package org.daos;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.persistence.HibernateConfig;
import org.persistence.model.FileData;
import org.persistence.model.Folder;
import org.persistence.model.PermissionMatrixSettings;
import org.persistence.model.Permissions;
import org.persistence.model.Role;
import org.persistence.model.SubRole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

public class FolderDAOTest {

    private static EntityManagerFactory emf;
    private static FolderDAO folderDAO;

    private SubRole subRole1;
    private SubRole subRole2;
    private SubRole subRole3;

    // private Permissions permissions1 = new Permissions();
    // private Permissions permissions2 = new Permissions();
    // private Permissions permissions3 = new Permissions();

    // private PermissionMatrixSettings permissionMatrixSettings1Folder1 = new PermissionMatrixSettings();
    // private PermissionMatrixSettings permissionMatrixSettings2Folder1 = new PermissionMatrixSettings();
    // private PermissionMatrixSettings permissionMatrixSettings1Folder2 = new PermissionMatrixSettings();
    // private PermissionMatrixSettings permissionMatrixSettings2Folder2 = new PermissionMatrixSettings();

    private Folder folder1;
    private Folder folder2;

    // private Role role1 = new Role();
    // private Role role2 = new Role();

    // private Set<PermissionMatrixSettings> permissionMatrixSettingsList1 = new HashSet<>();
    // private Set<PermissionMatrixSettings> permissionMatrixSettingsList2 = new HashSet<>();

    @BeforeAll
    public static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactory();
        folderDAO = FolderDAO.getInstance(emf);
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }

    @BeforeEach
    public void beforeEach() {
        resetDB();
        persistAll();
    }


    private void resetDB() {
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

    private void persistAll() {
        Role role1 = new Role("HR");
        Role role2 = new Role("Media Relations");

        subRole1 = new SubRole("HR_BASIC");
        subRole2 = new SubRole("HR_LEAD");
        subRole3 = new SubRole("MEDIA_RELATIONS_BASIC");

        folder1 = new Folder(new HashSet<FileData>(), new HashSet<Folder>(), null);
        folder2 = new Folder(new HashSet<FileData>(), new HashSet<Folder>(), folder1);

        Permissions permissions1 = new Permissions(true, true, true);
        Permissions permissions2 = new Permissions(true, false, false);
        Permissions permissions3 = new Permissions(true, false, true);
        
        PermissionMatrixSettings permissionMatrixSettings2Folder2 = new PermissionMatrixSettings(folder2, subRole3, permissions1);
        PermissionMatrixSettings permissionMatrixSettings1Folder1 = new PermissionMatrixSettings(folder1, subRole1, permissions1);
        PermissionMatrixSettings permissionMatrixSettings2Folder1 = new PermissionMatrixSettings(folder1, subRole2, permissions2);
        PermissionMatrixSettings permissionMatrixSettings1Folder2 = new PermissionMatrixSettings(folder2, subRole1, permissions3);

        role1.addSubFolder(folder1);
        role1.addSubFolder(folder2);
        role2.addSubFolder(folder1);
        role2.addSubFolder(folder2);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(role1);
            em.persist(role2);

            role1.addSubrole(subRole1);
            role1.addSubrole(subRole2);
            role2.addSubrole(subRole3);

            em.persist(subRole3);
            em.persist(subRole1);
            em.persist(subRole2);

            em.persist(permissions1);
            em.persist(permissions2);
            em.persist(permissions3);
            em.persist(permissionMatrixSettings1Folder1);
            em.persist(permissionMatrixSettings2Folder1);
            em.persist(permissionMatrixSettings1Folder2);
            em.persist(permissionMatrixSettings2Folder2);
            em.persist(folder1);
            em.persist(folder2);
            em.getTransaction().commit();
        }
    }

    @Test
    public void testGetPermissions() {
        Permissions expected = new Permissions(false, true, false);
        Permissions actual = folderDAO.getPermissions(folder1, subRole3);
        assertEquals(expected, actual);
    }
}