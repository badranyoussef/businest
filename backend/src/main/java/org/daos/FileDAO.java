package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

import org.persistence.model.FileData;

public class FileDAO extends AbstractDAO<FileData> {

    private static EntityManagerFactory emf;

    public FileDAO(EntityManagerFactory _emf) {
        super(_emf, FileData.class);
        emf = super.emf;
    }

    public List<FileData> getAllByTypeInPath(String folderPath, String fileType) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<FileData> query = em.createQuery("SELECT f FROM FileData f WHERE f.folderPath =:folder_path AND f.fileType =:file_type", FileData.class);
            query.setParameter("folder_path", folderPath);
            query.setParameter("file_type", fileType);
            return query.getResultList();
        }
    }

    public List<FileData> getAllFilesInPath(String folderPath) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<FileData> query = em.createQuery("SELECT f FROM FileData f WHERE f.folderPath =:folder_path", FileData.class);
            query.setParameter("folder_path", folderPath);
            return query.getResultList();
        }
    }
}

