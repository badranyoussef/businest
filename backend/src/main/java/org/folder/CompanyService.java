package org.folder;


import jakarta.persistence.EntityManager;
import org.daos.CompanyDAO;
import org.daos.RoleFolderDAO;
import org.daos.SubRoleFolderDAO;
import org.entities.Company;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.exceptions.ApiException;

import java.time.Instant;
import java.util.List;

import static org.persistence.HibernateConfig.entityManagerFactory;

public class CompanyService {

    private CompanyDAO companyDAO;
    private RoleFolderDAO roleFolderDAO;
    private SubRoleFolderDAO subRoleFolderDAO;
    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<RoleFolder> getRolesByCompany(String companyName) throws Exception {
        return companyDAO.findRolesByCompanyName(companyName);
    }
    public Company getCompanyById(Long companyId) {
        Company company = companyDAO.findById(companyId);
        if (company == null) {
            throw new ApiException(404, "Company not found.", Instant.now().toString());
        }
        return company;
    }



    public Company findById(Long companyId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Company.class, companyId);
        } finally {
            entityManager.close();
        }
    }

    public List<RoleFolder> getRolesByCompanyName(String companyName) {
        Company company = companyDAO.findByName(companyName);
        if (company == null) {
            throw new ApiException(404, "Company not found.", Instant.now().toString());
        }
        return roleFolderDAO.findRolesByCompanyId(company.getId());
    }
    public List<SubRoleFolder> getSubRolesByCompanyName(String companyName) {
        Company company = companyDAO.findByName(companyName);
        if (company == null) {
            throw new ApiException(404, "Company not found.", Instant.now().toString());
        }
        return subRoleFolderDAO.findSubRolesByCompanyId(company.getId());
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
}
