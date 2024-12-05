package org.folder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.persistence.model.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class FolderDAO {

    private static final Logger logger = LoggerFactory.getLogger(FolderDAO.class);
    private final EntityManagerFactory emf;

    public FolderDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Retrieve all folders
    public List<Folder> getAllFolders() {
        EntityManager em = emf.createEntityManager();
        try {
            logger.info("Fetching all folders from the database.");
            return em.createQuery(
                            "SELECT DISTINCT f FROM Folder f LEFT JOIN FETCH f.files", Folder.class)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error fetching all folders: {}", e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
    }

    // Retrieve folder by ID
    public Folder getById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            logger.info("Fetching folder by ID: {}", id);
            return em.createQuery(
                            "SELECT f FROM Folder f LEFT JOIN FETCH f.files WHERE f.id = :id", Folder.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            logger.error("Error fetching folder by ID {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
    }

    // Create or update a folder by ID and name
    public Folder createOrUpdateFolder(Integer id, String name) {
        EntityManager em = emf.createEntityManager();
        try {
            logger.info("Creating or updating folder with ID: {}", id);
            em.getTransaction().begin();

            // Find folder by ID
            Folder folder = em.find(Folder.class, id);

            if (folder == null) {
                logger.info("Folder not found. Creating new folder with name: {}", name);
                folder = new Folder(id, name);
                em.merge(folder);
            } else {
                logger.info("Folder found. Updating folder name to: {}", name);
                folder.setName(name);
            }

            em.getTransaction().commit();
            return folder;
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error("Error creating or updating folder with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error creating or updating folder", e);
        } finally {
            em.close();
        }
    }

    // Delete folder by ID
    public boolean deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            logger.info("Deleting folder by ID: {}", id);
            em.getTransaction().begin();
            Folder folder = em.find(Folder.class, id);
            if (folder != null) {
                em.remove(folder);
                em.getTransaction().commit();
                logger.info("Folder deleted successfully with ID: {}", id);
                return true;
            } else {
                logger.warn("Folder not found with ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            logger.error("Error deleting folder by ID {}: {}", id, e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
    }

    // Fetch all files by folder ID
    public List<File> getAllFilesInFolder(int folderId) {
        EntityManager em = emf.createEntityManager();
        try {
            logger.info("Fetching all files for folder ID: {}", folderId);
            List<File> files = em.createQuery("SELECT f FROM File f WHERE f.folder.id = :folderId", File.class)
                    .setParameter("folderId", folderId)
                    .getResultList();
            logger.info("Found {} files for folder ID: {}", files.size(), folderId);
            return files;
        } catch (Exception e) {
            logger.error("Error fetching files for folder ID {}: {}", folderId, e.getMessage(), e);
            throw e;
        } finally {
            em.close();
        }
    }
}
