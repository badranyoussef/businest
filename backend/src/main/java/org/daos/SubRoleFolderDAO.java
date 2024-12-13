package org.daos;

import jakarta.persistence.*;
import java.util.List;
import org.entities.SubRoleFolder;

public class SubRoleFolderDAO {

    private EntityManagerFactory emf;

    public SubRoleFolderDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SubRoleFolder create(SubRoleFolder subRoleFolder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(subRoleFolder);
            em.getTransaction().commit();
            return subRoleFolder;
        } finally {
            em.close();
        }
    }

    public List<SubRoleFolder> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRoleFolder sr";
            return em.createQuery(jpql, SubRoleFolder.class).getResultList();
        } finally {
            em.close();
        }
    }

    public SubRoleFolder findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SubRoleFolder.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SubRoleFolder subRoleFolder = em.find(SubRoleFolder.class, id);
            if (subRoleFolder != null) {
                em.remove(subRoleFolder);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<SubRoleFolder> findSubRolesByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRoleFolder sr JOIN sr.users u WHERE u.id = :userId";
            return em.createQuery(jpql, SubRoleFolder.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public List<SubRoleFolder> findSubRolesByCompanyId(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRoleFolder sr WHERE sr.company.id = :companyId";
            return em.createQuery(jpql, SubRoleFolder.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<SubRoleFolder> findSubRolesByCompanyName(String companyName) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRoleFolder sr WHERE sr.company.companyName = :companyName";
            return em.createQuery(jpql, SubRoleFolder.class)
                    .setParameter("companyName", companyName)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
