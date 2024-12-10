package org.daos;

import jakarta.persistence.TypedQuery;
import org.persistence.model.Folder;
import org.persistence.model.PermissionMatrixSettings;
import org.persistence.model.Permissions;

import jakarta.persistence.EntityManagerFactory;

public class FolderDAO extends AbstractDAO<Folder> {
    private static EntityManagerFactory emf;

    public FolderDAO(EntityManagerFactory _emf) {
        super(_emf, Folder.class);
        emf = super.emf;

    }

    public Permissions getPermissions(int folderID, int subRoleID) {
        try (var em = emf.createEntityManager()) {
            TypedQuery<PermissionMatrixSettings> query = em.createQuery("SELECT p FROM PermissionMatrixSettings p WHERE p.folder.id = :folder AND p.subRole.id = :subRole", PermissionMatrixSettings.class);
            query.setParameter("folder", folderID);
            query.setParameter("subRole", subRoleID);
            return query.getSingleResult().getPermissions();
        }
    }

}
