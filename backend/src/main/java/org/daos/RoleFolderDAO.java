package org.daos;

import jakarta.persistence.*;
import org.entities.RoleFolder;

import java.util.List;

public class RoleFolderDAO {

    private EntityManagerFactory emf;

    public RoleFolderDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public RoleFolder create(RoleFolder roleFolder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(roleFolder);
            em.getTransaction().commit();
            return roleFolder;
        } finally {
            em.close();
        }
    }

    public List<RoleFolder> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM RoleFolder r";
            return em.createQuery(jpql, RoleFolder.class).getResultList();
        } finally {
            em.close();
        }
    }

    public RoleFolder findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(RoleFolder.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RoleFolder roleFolder = em.find(RoleFolder.class, id);
            if (roleFolder != null) {
                em.remove(roleFolder);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<RoleFolder> findRolesByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM RoleFolder r JOIN r.users u WHERE u.id = :userId";
            return em.createQuery(jpql, RoleFolder.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<RoleFolder> findRolesByCompanyId(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM RoleFolder r WHERE r.company.id = :companyId";
            return em.createQuery(jpql, RoleFolder.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public List<RoleFolder> findRolesByCompanyName(String companyName) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM RoleFolder r WHERE r.company.companyName = :companyName";
            return em.createQuery(jpql, RoleFolder.class)
                    .setParameter("companyName", companyName)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
