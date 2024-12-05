package org.daos;

import jakarta.persistence.*;
import org.entities.Company;
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

    public Folder findById(Long folderId) { // Changed parameter type to Long
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Folder.class, folderId);
        } finally {
            em.close();
        }
    }

    public List<Folder> findByCompany(Company company) {
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

    public Folder findByNameAndCompanyName(String folderName, String companyName) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT f FROM Folder f WHERE f.name = :folderName AND f.company.companyName = :companyName";
            return em.createQuery(jpql, Folder.class)
                    .setParameter("folderName", folderName)
                    .setParameter("companyName", companyName)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public void delete(Long folderId) { // Changed parameter type to Long
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

    public List<Folder> findByCompanyId(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT f FROM Folder f WHERE f.company.id = :companyId";
            return em.createQuery(jpql, Folder.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
