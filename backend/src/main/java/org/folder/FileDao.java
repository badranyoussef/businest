package org.folder;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.daos.AbstractDAO;
import org.persistence.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class FileDao  {
    private static final Logger logger = LoggerFactory.getLogger(FileDao.class);
    private static FileDao instance;
    private static EntityManagerFactory emf;



    public FileDao(EntityManagerFactory emf) {
        FileDao.emf = emf;
    }


    public List<File> getAllFiles() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM File f", File.class).getResultList();
        } finally {
            em.close();
        }
    }
    public File addFileToFolder(int folderId, File file) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Find folderen
            Folder folder = em.find(Folder.class, folderId);
            if (folder == null) {
                throw new IllegalArgumentException("Folder not found with ID: " + folderId);
            }

            // Sæt folder på filen og tilføj den til folderen
            file.setFolder(folder);
            folder.addFile(file);

            // Persist filen
            em.persist(file);

            em.getTransaction().commit();
            logger.info("File {} added to folder {}", file.getName(), folder.getName());
            return file;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error("Error adding file to folder: {}", e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
    }









    // Fetch all distinct folders (via Folder entity)



}
