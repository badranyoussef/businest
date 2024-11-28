import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.controllers.CompanyController;
import org.controllers.FolderController;
import org.controllers.RoleController;
import org.controllers.SubRoleController;
import org.daos.CompanyDAO;
import org.daos.FolderDAO;
import org.daos.RoleDAO;
import org.daos.SubRoleDAO;
import org.entities.Folder;
import org.folder.*;

import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FolderControllerIntegrationTest {

    private static EntityManagerFactory emf;
    private Javalin app;
    private FolderController folderController;
    private ISecurityController securityController;
    private FolderService folderService;
    private CompanyController companyController;
    private RoleController roleController;
    private SubRoleController subRoleController;

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
        // Initialize DAOs
        FolderDAO folderDAO = new FolderDAO(emf);
        RoleDAO roleDAO = new RoleDAO(emf);
        SubRoleDAO subRoleDAO = new SubRoleDAO(emf);
        CompanyDAO companyDAO = new CompanyDAO(emf);

        // Initialize Services (if any)
        folderService = new FolderService(folderDAO);
        CompanyService companyService = new CompanyService(companyDAO);

        // Initialize Controllers
        folderController = new FolderController(folderService);
        roleController = new RoleController(roleDAO);
        subRoleController = new SubRoleController(subRoleDAO);
        companyController = new CompanyController(companyService, folderService);

        // Initialize Security Controller
        securityController = new TestSecurityController();

        // Initialize the application
        app = Javalin.create(config -> {});

        // Register endpoints
        Endpoints endpoints = new Endpoints(securityController, folderController, companyController, roleController, subRoleController);
        endpoints.registerRoutes(app);

        // Clean the database before each test
        cleanDatabase();
    }

    @AfterEach
    void tearDown() {
        app.stop();
    }

    @Test
    void testAssignRole_Success() {
        Role userRole = createRole("USER");
        Role guestRole = createRole("GUEST");

        insertTestFolder("folder123", "Test Folder", "ExampleCompany", guestRole, null);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = userRole.getId().toString();

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

        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, "folder123");
        assertNotNull(folder);
        assertEquals(userRole.getId(), folder.getRole().getId());
        em.close();
    }

    @Test
    void testAssignSubRole_Success() {
        Role userRole = createRole("USER");
        SubRole mediumSubRole = createSubRole("MEDIUM");
        SubRole highSubRole = createSubRole("HIGH");

        insertTestFolder("folder124", "Test Folder SubRole", "ExampleCompany", userRole, mediumSubRole);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = highSubRole.getId().toString();

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

        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, "folder124");
        assertNotNull(folder);
        assertEquals(highSubRole.getId(), folder.getSubRole().getId());
        em.close();
    }

    @Test
    void testGetFoldersByCompany_Success() {
        Role userRole = createRole("USER");
        Role managerRole = createRole("MANAGER");
        SubRole mediumSubRole = createSubRole("MEDIUM");
        SubRole highSubRole = createSubRole("HIGH");

        insertTestFolder("folder1", "Folder One", "ExampleCompany", userRole, mediumSubRole);
        insertTestFolder("folder2", "Folder Two", "ExampleCompany", managerRole, highSubRole);
        insertTestFolder("folder3", "Folder Three", "OtherCompany", userRole, null);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .when()
                    .get("/folders/ExampleCompany")
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(2));
        });
    }

    private Role createRole(String roleName) {
        EntityManager em = emf.createEntityManager();
        Role role = new Role();
        role.setName(roleName);
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        em.close();
        return role;
    }

    private SubRole createSubRole(String subRoleName) {
        EntityManager em = emf.createEntityManager();
        SubRole subRole = new SubRole();
        subRole.setName(subRoleName);
        em.getTransaction().begin();
        em.persist(subRole);
        em.getTransaction().commit();
        em.close();
        return subRole;
    }

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

    private void cleanDatabase() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Folder").executeUpdate();
        em.createQuery("DELETE FROM Role").executeUpdate();
        em.createQuery("DELETE FROM SubRole").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
