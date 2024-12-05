package org.routes;


import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.folder.FolderDAO;
import org.folder.RouteFolderFile;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.folder.Folder;
import org.persistence.HibernateConfig;
import org.persistence.model.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FolderFileControllerTest {
    private static Javalin app;
    private static EntityManagerFactory emf;

    @BeforeAll
    public static void setUp() {
        // Start Javalin server
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        app = Javalin.create().start(7000);

        FolderDAO folderDAO = new FolderDAO(emf);
        RouteFolderFile routeFolderFile = new RouteFolderFile(emf);
        // Tilføj testdata
        Folder documentsFolder = folderDAO.CreateFolder(1,"/documents", "Documents");
        documentsFolder.addFile(new File("file1.txt", "txt", documentsFolder));
        documentsFolder.addFile(new File("file2.pdf", "pdf", documentsFolder));



        // Set up routes
        app.routes(routeFolderFile.configureRoutes());

        // Set up RestAssured
        RestAssured.port = 7000;
    }

    @AfterAll
    public static void tearDown() {
        app.stop();
        emf.close();
    }


    @Test
    public void testCreateFolder() {
        // Opretter en ny folder via API'et
        given()
                .contentType("application/json")
                .body("{\"id\":1, \"path\":\"/testFolder\", \"name\": \"Test Folder\"}") // Rettet JSON-body
                .when()
                .post("/folders/")
                .then()
                .statusCode(201) // Forventet statuskode for succesfuld oprettelse
                .body("path", containsString("/testFolder")) // Matcher korrekt sti
                .body("name", containsString("Test Folder")); // Matcher korrekt navn
    }

    @Test
    public void testGetAllFolders() {
        given()
                .when()
                .get("/folders")
                .then()
                .statusCode(200) // Forventet statuskode for succes
                .body("size()", greaterThan(0)); // Sørg for, at der er mindst én mappe
    }

    @Test
    public void testGetAllFilesByFolderId() {
        // Test GET API for at hente filer i en eksisterende mappe
        given()
                .pathParam("folder_id", 1) // Sørg for, at denne ID findes og matcher testdataene
                .when()
                .get("/folders/{folder_id}/files")
                .then()
                .statusCode(200) // Bekræft, at HTTP-statuskoden er korrekt
                .body("size()", greaterThan(0)) // Bekræft, at der er mindst én fil
                .body("[0].name", containsString("file1.txt")); // Bekræft, at filnavnet er korrekt
    }



    @Test
    public void testGetFolderById() {
        int validFolderId = 1; // Antag en eksisterende folder ID i testdatabasen
        int invalidFolderId = 9999; // Antag en ikke-eksisterende folder ID
        String invalidFormatId = "abc"; // Ugyldigt format

        // Test for gyldigt folder ID
        given()
                .pathParam("folder_id", validFolderId)
                .when()
                .get("/folders/{folder_id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(validFolderId));

        // Test for ugyldigt folder ID
        given()
                .pathParam("folder_id", invalidFolderId)
                .when()
                .get("/folders/{folder_id}")
                .then()
                .statusCode(404)
                .body("message", containsString("Folder not found"));

        // Test for ugyldigt format
        given()
                .pathParam("folder_id", invalidFormatId)
                .when()
                .get("/folders/{folder_id}")
                .then()
                .statusCode(400)
                .body("message", containsString("Invalid folder ID format"));
    }

  /*  @Test
    public void testRemoveFileFromFolder() {
        Folder folder = new Folder("/path", "Test Folder");
        File file = new File("file.txt", "txt", folder);

        folder.addFile(file);
        folder.removeFile(file);

        assertFalse(folder.getFiles().contains(file));
        assertNull(file.getFolder());
    }
*/


}