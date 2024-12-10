package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.persistence.model.FileData;

import java.util.List;

public class FileDAO extends AbstractDAO<FileData> {

    private static FileDAO instance;
    private static EntityManagerFactory emf;

    private FileDAO(EntityManagerFactory _emf, Class<FileData> entityClass) {
        super(_emf, entityClass);
    }

    public static FileDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FileDAO(emf, FileData.class);
        }
        return instance;
    }

    public List<FileData> getAllByTypeInPath(String folderPath, String fileType) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<FileData> query = em.createQuery("SELECT f FROM FileData f WHERE f.filePath =:folder_path AND f.fileType =:file_type", FileData.class);
            query.setParameter("folder_path", folderPath);
            query.setParameter("file_type", fileType);
            return query.getResultList();
        }
    }

    public List<FileData> getAllFilesInPath(String folderPath) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<FileData> query = em.createQuery("SELECT f FROM FileData f WHERE f.filePath =:folder_path", FileData.class);
            query.setParameter("folder_path", folderPath);
            return query.getResultList();
        }
    }
}

