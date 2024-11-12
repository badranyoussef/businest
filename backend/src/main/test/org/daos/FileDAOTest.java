package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.daos.FileDAO;
import org.junit.jupiter.api.*;
import org.persistence.HibernateConfig;
import org.persistence.model.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileDAOTest {

    private static EntityManagerFactory emf;
    private static FileDAO fileDAO;

    @BeforeAll
    public static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        fileDAO = FileDAO.getInstance(emf);
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }

    @BeforeEach
    public void beforeEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE File RESTART IDENTITY").executeUpdate();
            em.persist(new File("/folder", "profile-picture", ".jpg"));
            em.getTransaction().commit();
        }
    }

    @AfterEach
    public void afterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM File ").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Testing that entity manager factory is not null.")
    public void testingEntityManagerFactory() {
        // Given

        // When

        // Then
        assertNotNull(emf);
    }

    @Test
    @DisplayName("Testing that FileDAO is not null.")
    public void test() {
        // Given

        // When

        // Then
        assertNotNull(fileDAO);
    }
}
