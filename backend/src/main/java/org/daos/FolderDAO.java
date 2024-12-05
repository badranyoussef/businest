package org.daos;

import jakarta.persistence.TypedQuery;
import org.persistence.model.Folder;
import org.persistence.model.PermissionMatrixSettings;
import org.persistence.model.Permissions;
import org.persistence.model.SubRole;

import jakarta.persistence.EntityManagerFactory;

public class FolderDAO extends AbstractDAO<Folder> {
    private static FolderDAO instance;
    private static EntityManagerFactory emf;

    private FolderDAO(EntityManagerFactory _emf, Class<Folder> entityClass) {
        super(_emf, entityClass);
    }

    public static FolderDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FolderDAO(emf, Folder.class);
        }
        return instance;
    }

    public Permissions getPermissions(Folder folder1, SubRole subRole1) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<PermissionMatrixSettings> query = em.createQuery("SELECT p FROM PermissionMatrixSettings p WHERE p.folder = :folder AND p.subRole = :subRole", PermissionMatrixSettings.class);
            query.setParameter("folder", folder1);
            query.setParameter("subRole", subRole1);
            return query.getSingleResult().getPermissions();
        }
    }

}
