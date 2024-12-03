package org.folder;


import org.daos.CompanyDAO;
import org.entities.Company;

import java.util.List;

public class CompanyService {

    private CompanyDAO companyDAO;

    public CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public List<Role> getRolesByCompany(String companyName) throws Exception {
        return companyDAO.findRolesByCompanyName(companyName);
    }
    public Company getCompanyById(Long companyId) {
        return companyDAO.findById(companyId);
    }
}
