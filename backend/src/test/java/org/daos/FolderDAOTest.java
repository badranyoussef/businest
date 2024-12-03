package org.daos;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.entities.Folder;
import org.entities.PermissionMatrixSettings;
import org.entities.Permissions;
import org.entities.Role;
import org.entities.SubRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.persistence.HibernateConfig;
import org.persistence.model.File;

import groovy.transform.builder.InitializerStrategy.SET;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class FolderDAOTest {

    private static EntityManagerFactory emf;
    private static FolderDAO folderDAO;

    private SubRole subRole1;
    private SubRole subRole2;
    private SubRole subRole3;

    private Permissions permissions1;
    private Permissions permissions2;
    private Permissions permissions3;
    
    private PermissionMatrixSettings permissionMatrixSettings1Folder1;
    private PermissionMatrixSettings permissionMatrixSettings2Folder1;
    private PermissionMatrixSettings permissionMatrixSettings1Folder2;
    private PermissionMatrixSettings permissionMatrixSettings2Folder2;

    private Folder folder1;
    private Folder folder2;

    private Set<SubRole> subRoles1;
    private Set<SubRole> subRoles2;

    private Role role1;
    private Role role2;

    private Set<PermissionMatrixSettings> permissionMatrixSettingsList1;
    private Set<PermissionMatrixSettings> permissionMatrixSettingsList2;

    @BeforeAll
    public static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        folderDAO = FolderDAO.getInstance(emf);
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }

    @BeforeEach
    public void beforeEach() {


        permissionMatrixSettingsList1 = List.of(permissionMatrixSettings1Folder1, permissionMatrixSettings2Folder1);
        permissionMatrixSettingsList2 = List.of(permissionMatrixSettings1Folder2, permissionMatrixSettings2Folder2);



        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE File RESTART IDENTITY").executeUpdate();
            em.persist(em.persist(new Folder ("folder1", new ArrayList<File>(), new ArrayList<Folder>(), new ArrayList<SubRole>(), null, new ArrayList<PermissionMatrixSettings>()));
        }
    }

    private void setupInstances() {
        subRoles1 = Set.of(subRole1, subRole2);
        subRoles2 = Set.of(subRole3);

        role1 = new Role("HR", Set.of(folder1, folder2), subRoles1);
        role2 = new Role("Media Relations", Set.of(folder1, folder2), subRoles2);

        subRole1 = new SubRole("HR_BASIC", role1);
        subRole2 = new SubRole("HR_LEAD", role1);
        subRole3 = new SubRole("MEDIA_RELATIONS_BASIC", role2);

        permissions1 = new Permissions(true, true, true);
        permissions2 = new Permissions(true, false, false);
        permissions3 = new Permissions(true, false, true);

        permissionMatrixSettings1Folder1 = new PermissionMatrixSettings(folder1, subRole1, permissions1);
        permissionMatrixSettings2Folder1 = new PermissionMatrixSettings(folder1, subRole2, permissions2);
        permissionMatrixSettings1Folder2 = new PermissionMatrixSettings(folder2, subRole1, permissions3);
        permissionMatrixSettings2Folder2 = new PermissionMatrixSettings(folder2, subRole3, permissions1);

        folder1 = new Folder(new HashSet<File>(), new HashSet<Folder>(), new HashSet<SubRole>(), null, permissionMatrixSettingsList1);
    }

    private void persistSubrole() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new SubRole("subrole1", new ArrayList<PermissionMatrixSettings>()));
            em.persist(new SubRole("subrole2", new ArrayList<PermissionMatrixSettings>()));
            em.persist(new SubRole("subrole3", new ArrayList<PermissionMatrixSettings>()));
            em.getTransaction().commit();
        }
    }

    private void persistPermissions() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new Permission("permission1", new ArrayList<SubRole>()));
            em.persist(new Permission("permission2", new ArrayList<SubRole>()));
            em.persist(new Permission("permission3", new ArrayList<SubRole>()));
            em.getTransaction().commit();
        }
    }

    private void persistPermissionMatrixSettings() {
        try (EntityManager em = emf.createEntityManager()) {

        }
    }

    private void persistFolder() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(new Folder("folder1", new ArrayList<File>(), new ArrayList<Folder>(), new ArrayList<SubRole>(), null, new ArrayList<PermissionMatrixSettings>()));
            em.persist(new Folder("folder2", new ArrayList<File>(), new ArrayList<Folder>(), new ArrayList<SubRole>(), null, new ArrayList<PermissionMatrixSettings>()));
            em.persist(new Folder("folder3", new ArrayList<File>(), new ArrayList<Folder>(), new ArrayList<SubRole>(), null, new ArrayList<PermissionMatrixSettings>()));
            em.getTransaction().commit();
        }
    }

}
