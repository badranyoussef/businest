package org.daos;

import jakarta.persistence.EntityManagerFactory;
import org.persistence.model.UserChangesLogEntry;

import java.util.List;

public class LogDAO {

    private EntityManagerFactory emf;

    public LogDAO(EntityManagerFactory _emf) {
        emf = _emf;
    }

    public List<UserChangesLogEntry> getAll() {

        try (var em = emf.createEntityManager()) {

            return em.createQuery("FROM UserChangesLogEntry t", UserChangesLogEntry.class).getResultList();
        }
    }


}
