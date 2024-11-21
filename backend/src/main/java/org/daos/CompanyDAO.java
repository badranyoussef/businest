package org.daos;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.folder.Role;
import org.persistence.HibernateConfig;

public class CompanyDAO {

    private EntityManagerFactory entityManagerFactory;

    public CompanyDAO() {
        this.entityManagerFactory = HibernateConfig.getEntityManagerFactoryConfig(false);
    }


    public List<Role> findRolesByCompanyName(String companyName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            String jpql = "SELECT r FROM Role r WHERE r.company.name = :companyName";
            return entityManager.createQuery(jpql, Role.class)
                    .setParameter("companyName", companyName)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}
