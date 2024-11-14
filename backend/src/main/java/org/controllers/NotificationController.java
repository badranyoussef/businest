package org.controllers;

import io.javalin.http.Handler;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import org.daos.LogDAO;
import org.dtos.UserChangesLogEntryDTO;
import org.persistence.model.UserChangesLogEntry;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationController {


    public static Handler getAllLogEntries(LogDAO logDAO) {
        return ctx -> {
            try{
                System.out.println("Get all log entries - START");
                List<UserChangesLogEntry> allLogEntries = logDAO.getAll();

                List<UserChangesLogEntryDTO> allLogEntriesAsDTOs = allLogEntries.stream()
                        .map(t -> new UserChangesLogEntryDTO(t)).collect(Collectors.toList());

                ctx.status(200).json(allLogEntriesAsDTOs);

            }catch (Exception e){
                System.out.println("Some error happened when trying to fetch all log entries");
            }
        };
    }
}
