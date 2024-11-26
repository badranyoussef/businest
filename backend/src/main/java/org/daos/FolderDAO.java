package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.entities.FileRequirements;

import java.util.List;

public class FolderDAO {

    private EntityManagerFactory emf;
    public FolderDAO(EntityManagerFactory _emf) {
        this.emf = _emf;
    }

    public List<FileRequirements> GetCompanyFileRequirements(Long companyId) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT fr FROM FileRequirements fr WHERE fr.company.id = :companyId", FileRequirements.class)
                    .setParameter("companyId", companyId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}

