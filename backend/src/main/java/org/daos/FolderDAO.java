package org.daos;

import org.persistence.model.Folder;

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

}
