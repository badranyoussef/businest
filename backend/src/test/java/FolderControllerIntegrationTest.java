

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jakarta.persistence.EntityManagerFactory;
import org.controllers.CompanyController;
import org.folder.*;
import org.junit.jupiter.api.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;


import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class FolderControllerIntegrationTest {

    private static EntityManagerFactory emf;
    private Javalin app;
    private FolderController folderController;
    private ISecurityController securityController;
    private FolderService folderService;
    private CompanyController companyController;
    private User user;

    @BeforeAll
    static void setUpClass() {
        try {
            emf = Persistence.createEntityManagerFactory("test-persistence-unit");
        } catch (Exception e) {
            System.err.println("Error initializing EntityManagerFactory: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        folderService = new FolderService(emf);
        folderController = new FolderController(folderService);
        securityController = new SecurityController();

        app = Javalin.create(config -> {

        });

        Endpoints endpoints = new Endpoints(securityController, folderController,companyController);
        endpoints.registerRoutes(app);

        // Clean the database before each test
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Folder").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }

    @AfterEach
    void tearDown() {
        app.stop();
    }

    @Test
    void testAssignRole_Success() {
        // Insert test data
        insertTestFolder("folder123", "Test Folder", "ExampleCompany", Role.GUEST);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"folder123\", \"newRole\": \"USER\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/folder123/role")
                    .then()
                    .statusCode(200)
                    .body(equalTo("Role updated successfully."));
        });

        // Verify the folder's role was updated
        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, "folder123");
        assertNotNull(folder);
        assertEquals(Role.USER, folder.getRole());
        em.close();
    }

    @Test
    void testAssignRole_UnauthorizedAccess() {
        // Insert a folder belonging to a different company
        insertTestFolder("folder123", "Test Folder", "OtherCompany", Role.GUEST);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"folder123\", \"newRole\": \"USER\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/folder123/role")
                    .then()
                    .statusCode(403)
                    .body(equalTo("Forbidden: Unauthorized access: Cannot modify folders from other companies."));
        });
    }

    @Test
    void testAssignRole_FolderNotFound() {
        // No folder inserted

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"nonExistentFolder\", \"newRole\": \"USER\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/nonExistentFolder/role")
                    .then()
                    .statusCode(404)
                    .body(equalTo("Not Found: Folder not found."));
        });
    }

    @Test
    void testGetFoldersByCompany_Success() {
        // Insert folders for the company
        insertTestFolder("folder1", "Folder One", "ExampleCompany", Role.USER);
        insertTestFolder("folder2", "Folder Two", "ExampleCompany", Role.MANAGER);
        // Insert a folder for a different company
        insertTestFolder("folder3", "Folder Three", "OtherCompany", Role.USER);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .when()
                    .get("/folders/ExampleCompany")
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(2))
                    .body("[0].id", anyOf(equalTo("folder1"), equalTo("folder2")))
                    .body("[1].id", anyOf(equalTo("folder1"), equalTo("folder2")));
        });
    }

    // Helper methods
    private void insertTestFolder(String id, String name, String company, Role role) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Folder folder = new Folder(id, name, company, role);
        em.persist(folder);
        em.getTransaction().commit();
        em.close();
    }
}
