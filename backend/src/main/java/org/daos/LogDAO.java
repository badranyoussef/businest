package org.daos;

import jakarta.persistence.EntityManagerFactory;
import org.persistence.model.entities.*;

import java.util.List;

public class LogDAO {

    public LogDAO(EntityManagerFactory _emf) {
        emf = _emf;
    }

    public List<UserChangesLogEntry> getAll() {

        try (var em = emf.createEntityManager()) {

            return em.createQuery("FROM UserChangeLogEntry t", UserChangesLogEntry.class).getResultList();
        }
    }

}
