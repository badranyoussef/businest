package org.daos;

import jakarta.persistence.*;
import org.entities.Role;

import java.util.List;

public class RoleDAO {

    private EntityManagerFactory emf;

    public RoleDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Role create(Role role) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
            return role;
        } finally {
            em.close();
        }
    }

    public List<Role> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM Role r";
            return em.createQuery(jpql, Role.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Role findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Role.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Role role = em.find(Role.class, id);
            if (role != null) {
                em.remove(role);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Role> findRolesByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM Role r JOIN r.users u WHERE u.id = :userId";
            return em.createQuery(jpql, Role.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Role> findRolesByCompanyId(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM Role r WHERE r.company.id = :companyId";
            return em.createQuery(jpql, Role.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public List<Role> findRolesByCompanyName(String companyName) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT r FROM Role r WHERE r.company.companyName = :companyName";
            return em.createQuery(jpql, Role.class)
                    .setParameter("companyName", companyName)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
