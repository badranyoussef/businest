package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.persistence.model.File;

import java.util.List;

public class FileDAO extends AbstractDAO<File> {

    private static FileDAO instance;
    private static EntityManagerFactory emf;

    private FileDAO(EntityManagerFactory _emf, Class<File> entityClass) {
        super(_emf, entityClass);
    }

    public static FileDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FileDAO(emf, File.class);
        }
        return instance;
    }

    public List<File> getAllByTypeInPath(String folderPath, String fileType) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<File> query = em.createQuery("SELECT f FROM File f WHERE f.folderPath =:folder_path AND f.fileType =:file_type", File.class);
            query.setParameter("folder_path", folderPath);
            query.setParameter("file_type", fileType);
            return query.getResultList();
        }
    }

    public List<File> getAllFilesInPath(String folderPath) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<File> query = em.createQuery("SELECT f FROM File f WHERE f.folderPath =:folder_path", File.class);
            query.setParameter("folder_path", folderPath);
            return query.getResultList();
        }
    }

    public int deleteFileFromPath(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            File fileFound = em.find(File.class, id);
            if (fileFound != null) {
                em.remove(fileFound);
                em.getTransaction().commit();
                return 1;
            } else {
                return 0;
            }
        }
    }
}

