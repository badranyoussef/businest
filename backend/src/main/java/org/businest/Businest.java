package org.businest;

import org.routes.RouteAccount;
import jakarta.persistence.EntityManagerFactory;
import org.daos.LogDAO;
import org.persistence.HibernateConfig;
import org.persistence.model.UserChangesLogEntry;
import org.rest.ApplicationConfig;
import org.routes.Route;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

public class Businest {
    public static void main(String[] args) {

        //EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        RouteAccount route = new RouteAccount();

        ApplicationConfig app = ApplicationConfig.getInstance();

        app.initiateServer()
                .startServer(7007)
                .setExceptionHandlers()
                .setRoute(route.routesUser());


        //LocalDate today = LocalDate.now();

        // Popuplating UserChangesLogEntry table in DB
        /*UserChangesLogEntry u1 = new UserChangesLogEntry("user1", "Roles changed", "New role added1", today, "Team Leader 1");
        UserChangesLogEntry u2 = new UserChangesLogEntry("user2", "Roles changed", "New role added2", today, "Team Leader 2");
        UserChangesLogEntry u3 = new UserChangesLogEntry("user3", "Roles changed", "New role added3", today, "Team Leader 3");
        UserChangesLogEntry u4 = new UserChangesLogEntry("user4", "Roles changed", "New role added4", today, "Team Leader 4");

        LogDAO logDAO1 = new LogDAO(emf);

        logDAO1.create(u1);
        logDAO1.create(u2);
        logDAO1.create(u3);
        logDAO1.create(u4);*/
    }
}