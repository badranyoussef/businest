package org.folder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class FolderService {

    private EntityManagerFactory emf;

    public FolderService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void assignRole(String folderId, String company, Role newRole) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Folder folder = em.find(Folder.class, folderId);

            if (folder == null) {
                throw new IllegalArgumentException("Folder not found.");
            }

            if (!folder.getCompany().equals(company)) {
                throw new SecurityException("Unauthorized access: Cannot modify folders from other companies.");
            }

            folder.setRole(newRole);
            em.merge(folder);

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e; // Let the controller handle the exception
        } finally {
            em.close();
        }
    }

    public List<Folder> getFoldersInCompany(String company) {
        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT f FROM Folder f WHERE f.company = :company";
            TypedQuery<Folder> query = em.createQuery(jpql, Folder.class);
            query.setParameter("company", company);
            return query.getResultList();

        } finally {
            em.close();
        }
    }
}
