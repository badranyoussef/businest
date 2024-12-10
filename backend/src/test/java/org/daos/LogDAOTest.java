package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.persistence.HibernateConfig;
import org.persistence.model.UserChangesLogEntry;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LogDAOTest {


    private static EntityManagerFactory emf;
    private static LogDAO logDAO;

    @BeforeAll
    public static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        logDAO = new LogDAO(emf);
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }

    @BeforeEach
    public void beforeEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE UserChangesLogEntry RESTART IDENTITY").executeUpdate();

            LocalDate today = LocalDate.now();

            em.persist(new UserChangesLogEntry("User1", "Title1", "Description1", today, "AccountEditor1"));
            em.persist(new UserChangesLogEntry("User2", "Title2", "Description2", today, "AccountEditor2"));
            em.persist(new UserChangesLogEntry("User3", "Title3", "Description3", today, "AccountEditor3"));

            em.getTransaction().commit();
        }
    }

    @AfterEach
    public void afterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM UserChangesLogEntry ").executeUpdate();
            em.getTransaction().commit();
        }
    }


    @Test
    public void getAllLogEntries(){
        //Given
        int expectedListSize = 3;
        int expectedIdForTheFirstIndex = 1;

        //When
        List<UserChangesLogEntry> userChangesLogEntryList = logDAO.getAll();

        //Then
        assertNotNull(userChangesLogEntryList);
        assertEquals(expectedListSize, userChangesLogEntryList.size());
        assertEquals(expectedIdForTheFirstIndex,userChangesLogEntryList.get(0).getId());
    }

}
