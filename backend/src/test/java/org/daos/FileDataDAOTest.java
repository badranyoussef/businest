package org.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import org.persistence.HibernateConfig;
import org.persistence.model.FileData;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileDataDAOTest {

    private static EntityManagerFactory emf;
    private static FileDAO fileDAO;

    @BeforeAll
    public static void beforeAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        fileDAO = FileDAO.getInstance(emf);
    }

    @AfterAll
    public static void afterAll() {
        emf.close();
    }

    @BeforeEach
    public void beforeEach() {
        URL testfile = getClass().getResource("/12B.txt");
        java.io.File file = new java.io.File(testfile.getPath());
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE File RESTART IDENTITY").executeUpdate();
            em.persist(new FileData("Profile picture.", "Social Media",file));
            em.persist(new FileData("Profile picture.", "Social Media",file));
            em.persist(new FileData("Profile picture.", "Social Media",file));
            em.persist(new FileData("Profile picture.", "Social Media",file));
            em.getTransaction().commit();
        }
    }

    @AfterEach
    public void afterEach() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM FileData ").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Testing that entity manager factory is not null.")
    public void testingEntityManagerFactory() {
        // Given

        // When

        // Then
        assertNotNull(emf);
    }

    @Test
    @DisplayName("Testing that FileDAO is not null.")
    public void testFileDAO() {
        // Given

        // When

        // Then
        assertNotNull(fileDAO);
    }

    @DisplayName("Upload file not null.")
    @Test
    public void test00(){
        // Given
        URL testfile = getClass().getResource("/12B.txt");

        // When
        java.io.File file = new java.io.File(testfile.getPath());

        // Then
        assertNotNull(testfile);
        assertNotNull(file);
    }

    @Test
    @DisplayName("Testing creating a file.")
    public void createFile() {
        //Given
        URL testfile = getClass().getResource("/12B.txt");
        java.io.File file = new java.io.File(testfile.getPath());
        FileData expectedFileData = new FileData("Profile picture.", "Social Media", file);
        int expectedId = 5;

        //When
        FileData createdFileData = fileDAO.create(expectedFileData);

        //Then
        assertNotNull(createdFileData);
        assertEquals(expectedFileData.getName(), createdFileData.getName());
        //assertEquals(expectedFileData.getFolderPath(), createdFileData.getFolderPath());
        assertEquals(expectedFileData.getFileType(), createdFileData.getFileType());
        assertEquals(expectedFileData.getDescription(), createdFileData.getDescription());
        assertEquals(expectedFileData.getTopic(), createdFileData.getTopic());
        assertEquals(expectedId, createdFileData.getId());
    }

    @Test
    @DisplayName("Successfully deleting a file")
    public void deleteFile1() {
        //Given
        int expectedResult = 1;

        //When
        int result = fileDAO.delete(1);

        //Then
        assertEquals(expectedResult, result);
        assertTrue(result > 0);
    }

    @Test
    @DisplayName("Deleting a file unsuccessful")
    public void deleteFile2() {
        //Given
        int expectedResult = 0;

        //When
        int result = fileDAO.delete(5);

        //Then
        assertEquals(expectedResult, result);
        assertTrue(result == 0);
        assertFalse(result > 0);
    }

    @Test
    @DisplayName("Getting file by id.")
    public void readFile() {
        //Given
        int expetedRead = 3;
        String expectedName = "12B.txt";

        //When
        FileData foundFileData = fileDAO.getById(3);

        //Then
        assertNotNull(foundFileData);
        assertEquals(expetedRead, foundFileData.getId());
        assertEquals(foundFileData.getName(), expectedName);
    }

    @Test
    @DisplayName("Update file ")
    public void updateFile() {
        //Given
        int getFile = 1;
        FileData fileDataExpected;
        try (EntityManager em = emf.createEntityManager()) {
            fileDataExpected = em.find(FileData.class, getFile);
        }
        //fileDataExpected.setFolderPath("halluu");
        fileDataExpected.setName("hallu-picture");
        fileDataExpected.setFileType(".png");

        //When
        FileData fileDataUpdated = fileDAO.update(fileDataExpected);

        //Then
        assertNotNull(fileDataUpdated);
        assertEquals(fileDataExpected.getName(), fileDataUpdated.getName());
        //assertEquals(fileDataExpected.getFolderPath(), fileDataUpdated.getFolderPath());
        assertEquals(fileDataExpected.getFileType(), fileDataUpdated.getFileType());
        assertEquals(fileDataExpected.getId(), fileDataUpdated.getId());
    }

    @Test
    @DisplayName("Getting all files by type in path")
    public void getAllByTypeInPath() {
        //Given
        int expectedListSize = 2;
        int expectedIdForTheFirstIndex = 1;

        //When
        List<FileData> allFileData = fileDAO.getAllByTypeInPath("/folder", "txt");

        //Then
        assertNotNull(allFileData);
        assertEquals(expectedListSize, allFileData.size());
        assertEquals(expectedIdForTheFirstIndex, allFileData.get(0).getId());
    }

    @Test
    @DisplayName("Getting all files by path")
    public void getAllFilesInPath() {
        //Given
        int expectedListSize = 2;
        int expectedIdForTheFirstIndex = 3;

        //When
        List<FileData> allFileData = fileDAO.getAllFilesInPath("/folder2");

        //Then
        assertNotNull(allFileData);
        assertEquals(expectedListSize, allFileData.size());
        assertEquals(expectedIdForTheFirstIndex, allFileData.get(0).getId());
    }
}