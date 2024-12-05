package org.daos;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.entities.Company;
import org.entities.Role;
import org.persistence.HibernateConfig;

public class CompanyDAO {

    private EntityManagerFactory entityManagerFactory;

    public CompanyDAO() {
        this.entityManagerFactory = HibernateConfig.getEntityManagerFactoryConfig(false);
    }

    public CompanyDAO(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }


    public List<Role> findRolesByCompanyName(String companyName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT DISTINCT f.role FROM Folder f WHERE f.company.companyName = :companyName";
            return entityManager.createQuery(jpql, Role.class)
                    .setParameter("companyName", companyName)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }


    public Company findById(Long companyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Company.class, companyId);
        } finally {
            entityManager.close();
        }
    }


    public Company findByName(String companyName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT c FROM Company c WHERE c.companyName = :companyName";
            return entityManager.createQuery(jpql, Company.class)
                    .setParameter("companyName", companyName)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            entityManager.close();
        }
    }

    public boolean existsById(Long companyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Company c WHERE c.id = :companyId";
            Long count = entityManager.createQuery(jpql, Long.class)
                    .setParameter("companyId", companyId)
                    .getSingleResult();
            return count > 0;
        } finally {
            entityManager.close();
        }
    }


}

