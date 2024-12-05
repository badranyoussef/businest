package org.businest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.folder.Folder;
import org.folder.FolderDAO;
import org.persistence.HibernateConfig;
import org.persistence.model.File;
import org.folder.FileDao;
import java.util.List;


public class FolderMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
        EntityManager em = emf.createEntityManager();
        FolderDAO folderDAO = new FolderDAO(emf);

        em.getTransaction().begin();

        // Create folders and files
        Folder documentsFolder = folderDAO.createOrUpdateFolder(12, "Documents");
        documentsFolder.addFile(new File("file1.txt", "txt", documentsFolder));
        documentsFolder.addFile(new File("file2.pdf", "pdf", documentsFolder));

        Folder projectsFolder = folderDAO.createOrUpdateFolder(15, "Projects");
        projectsFolder.addFile(new File("project1.docx", "docx", projectsFolder));
        projectsFolder.addFile(new File("project2.pptx", "pptx", projectsFolder));

        Folder reportsFolder = folderDAO.createOrUpdateFolder(17, "Reports");
        reportsFolder.addFile(new File("report1.xlsx", "xlsx", reportsFolder));
        reportsFolder.addFile(new File("report2.csv", "csv", reportsFolder));

        em.merge(documentsFolder);
        em.merge(projectsFolder);
        em.merge(reportsFolder);

        em.getTransaction().commit();

        System.out.println("Folders and files have been successfully inserted into the database!");
        em.close();

        // Retrieve all folders
        List<Folder> folders = folderDAO.getAllFolders();
        folders.forEach(folder -> System.out.println("Folder: " + folder));
        // Create a new folder

        Folder folder2 = folderDAO.createOrUpdateFolder(2, "mmFosslder");
        System.out.println("Created folder: " + folder2);
        // Retrieve folder by ID
        Folder folderById = folderDAO.getById(12)    ;
        System.out.println("Folder by ID: " + folderById);

        // Fetch all files by folder ID
        FolderDAO folderdao = new FolderDAO(emf);
        List<File> filesInFolder = folderDAO.getAllFilesInFolder(15);
        filesInFolder.forEach(file -> System.out.println("File in folder: " + file));




    }
}
