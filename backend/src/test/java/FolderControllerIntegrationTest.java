

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.controllers.CompanyController;
import org.daos.CompanyDAO;
import org.daos.FolderDAO;
import org.folder.*;
import org.junit.jupiter.api.*;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FolderControllerIntegrationTest {

    private static EntityManagerFactory emf;
    private Javalin app;
    private FolderController folderController;
    private ISecurityController securityController;
    private FolderService folderService;
    private CompanyController companyController;

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
        FolderDAO folderDAO = new FolderDAO(emf);
        folderService = new FolderService(folderDAO);
        folderController = new FolderController(folderService);
        securityController = new org.folder.TestSecurityController();

        // Initialize CompanyController with a mock or real implementation
        companyController = new CompanyController(new CompanyService(new CompanyDAO(emf)));

        app = Javalin.create(config -> {

        });

        Endpoints endpoints = new Endpoints(securityController, folderController, companyController);
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
        insertTestFolder("folder123", "Test Folder", "ExampleCompany", Role.GUEST, SubRole.LOW);

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
    void testAssignSubRole_Success() {
        // Insert test data
        insertTestFolder("folder124", "Test Folder SubRole", "ExampleCompany", Role.USER, SubRole.MEDIUM);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"folder124\", \"newSubRole\": \"HIGH\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/folder124/subrole")
                    .then()
                    .statusCode(200)
                    .body(equalTo("SubRole updated successfully."));
        });

        // Verify the folder's subRole was updated
        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, "folder124");
        assertNotNull(folder);
        assertEquals(SubRole.HIGH, folder.getSubRole());
        em.close();
    }

    @Test
    void testAssignRole_UnauthorizedAccess() {
        // Insert a folder belonging to a different company
        insertTestFolder("folder125", "Test Folder Unauthorized", "OtherCompany", Role.GUEST, SubRole.LOW);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"folder125\", \"newRole\": \"USER\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/folder125/role")
                    .then()
                    .statusCode(403)
                    .body(equalTo("Forbidden: Unauthorized access: Cannot modify folders from other companies."));
        });
    }

    @Test
    void testAssignSubRole_UnauthorizedAccess() {
        // Insert a folder belonging to a different company
        insertTestFolder("folder126", "Test Folder SubRole Unauthorized", "OtherCompany", Role.GUEST, SubRole.LOW);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"folder126\", \"newSubRole\": \"HIGH\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/folder126/subrole")
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
    void testAssignSubRole_FolderNotFound() {
        // No folder inserted

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = "{ \"folderId\": \"nonExistentFolder\", \"newSubRole\": \"HIGH\" }";

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/nonExistentFolder/subrole")
                    .then()
                    .statusCode(404)
                    .body(equalTo("Not Found: Folder not found."));
        });
    }

    @Test
    void testGetFoldersByCompany_Success() {
        // Insert folders for the company
        insertTestFolder("folder1", "Folder One", "ExampleCompany", Role.USER, SubRole.MEDIUM);
        insertTestFolder("folder2", "Folder Two", "ExampleCompany", Role.MANAGER, SubRole.HIGH);
        // Insert a folder for a different company
        insertTestFolder("folder3", "Folder Three", "OtherCompany", Role.USER, SubRole.LOW);

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
                    .body("[1].id", anyOf(equalTo("folder1"), equalTo("folder2")))
                    .body("[0].subRole", anyOf(equalTo("MEDIUM"), equalTo("HIGH")))
                    .body("[1].subRole", anyOf(equalTo("MEDIUM"), equalTo("HIGH")));
        });
    }

    // Helper methods
    private void insertTestFolder(String id, String name, String company, Role role, SubRole subRole) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Folder folder = new Folder();
            folder.setId(id);
            folder.setName(name);
            folder.setCompany(company);
            folder.setRole(role);
            folder.setSubRole(subRole);
            em.persist(folder);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
