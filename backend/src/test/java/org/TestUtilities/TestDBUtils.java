package org.TestUtilities;

import org.persistence.HibernateConfig;

import io.cucumber.java.af.En;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class TestDBUtils {

    private static EntityManagerFactory emf;

    public TestDBUtils(EntityManagerFactory _emf) {
        emf = _emf;

    }

    public void resetDB() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM PermissionMatrixSettings").executeUpdate();
            em.createQuery("DELETE FROM Folder").executeUpdate();
            em.createQuery("DELETE FROM SubRole").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.createQuery("DELETE FROM Permissions").executeUpdate();
            em.createQuery("DELETE FROM FileData").executeUpdate();
            em.createQuery("DELETE FROM UserChangesLogEntry").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE folder_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE filerequirements_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE subrole_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE permissions_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE filedata_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE role_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE userchangeslogentry_id_seq RESTART WITH 1").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE permission_matrix_settings_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
    }
}
