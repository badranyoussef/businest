package org.controllers;

import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.daos.LogDAO;
import org.persistence.model.UserChangesLogEntry;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationController {

    private LogDAO logDAO;

    public NotificationController(EntityManagerFactory _emf) {

        this.logDAO = new LogDAO(_emf);
    }

    public Handler getAllTemplateEntityOnes() {
        return ctx -> {

            try{
                List<UserChangesLogEntry> entityONES = logDAO.getAll();

                List<UserChangesLogEntryDTO> allTemplateEntityOnes = entityONES.stream()
                        .map(t -> new DtoONE(t)).collect(Collectors.toList());

                ctx.status(200).json(allTemplateEntityOnes);

            }catch (PersistenceException pe){
                throw new ApiException(500, "Something went wrong when retrieving all templateEntityOne. " +
                        "Exception message: " + pe.getMessage());
            }

        };
    }
}
