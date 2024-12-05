package org.daos;

import jakarta.persistence.*;
import java.util.List;
import org.entities.SubRole;

public class SubRoleDAO {

    private EntityManagerFactory emf;

    public SubRoleDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SubRole create(SubRole subRole) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(subRole);
            em.getTransaction().commit();
            return subRole;
        } finally {
            em.close();
        }
    }

    public List<SubRole> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRole sr";
            return em.createQuery(jpql, SubRole.class).getResultList();
        } finally {
            em.close();
        }
    }

    public SubRole findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(SubRole.class, id);
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SubRole subRole = em.find(SubRole.class, id);
            if (subRole != null) {
                em.remove(subRole);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<SubRole> findSubRolesByUserId(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRole sr JOIN sr.users u WHERE u.id = :userId";
            return em.createQuery(jpql, SubRole.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
    public List<SubRole> findSubRolesByCompanyId(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRole sr WHERE sr.company.id = :companyId";
            return em.createQuery(jpql, SubRole.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<SubRole> findSubRolesByCompanyName(String companyName) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT sr FROM SubRole sr WHERE sr.company.companyName = :companyName";
            return em.createQuery(jpql, SubRole.class)
                    .setParameter("companyName", companyName)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
