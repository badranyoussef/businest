package org.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class FolderDAO {

    private EntityManagerFactory emf;
    public FolderDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    public List<FileRequirement> GetCompanyFileRequirements(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {

}
