package org.daos;

import jakarta.persistence.*;
import org.entities.Folder;
import java.util.List;

public class FolderDAO {

    private EntityManagerFactory emf;

    public FolderDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void create(Folder folder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(folder);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void update(Folder folder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(folder);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Folder findById(String folderId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Folder.class, folderId);
        } finally {
            em.close();
        }
    }

    public List<Folder> findByCompany(String company) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT f FROM Folder f WHERE f.company = :company";
            return em.createQuery(jpql, Folder.class)
                    .setParameter("company", company)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    // Find a folder by its name and company
    public Folder findByName(String folderName, String company) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT f FROM Folder f WHERE f.name = :folderName AND f.company = :company";
            return em.createQuery(jpql, Folder.class)
                    .setParameter("folderName", folderName)
                    .setParameter("company", company)
                    .getResultStream()
                    .findFirst()
                    .orElse(null); // Return null if no result
        } finally {
            em.close();
        }
    }

    public void delete(String folderId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Folder folder = em.find(Folder.class, folderId);
            if (folder != null) {
                em.remove(folder);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
