import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.controllers.CompanyController;
import org.controllers.FolderController;
import org.controllers.RoleFolderController;
import org.controllers.SubRoleController;
import org.daos.CompanyDAO;
import org.daos.FolderDAO;
import org.daos.RoleFolderDAO;
import org.daos.SubRoleFolderDAO;
import org.entities.Company;
import org.entities.Folder;
import org.entities.RoleFolder;
import org.entities.SubRoleFolder;
import org.folder.CompanyService;
import org.folder.Endpoints;
import org.folder.FolderService;
import org.folder.ISecurityController;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

public class FolderControllerIntegrationTest {

    private static EntityManagerFactory emf;
    private Javalin app;
    private FolderController folderController;
    private ISecurityController securityController;
    private FolderService folderService;
    private CompanyController companyController;
    private RoleFolderController roleFolderController;
    private SubRoleController subRoleController;
    private CompanyService companyService;

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
        RoleFolderDAO roleFolderDAO = new RoleFolderDAO(emf);
        SubRoleFolderDAO subRoleFolderDAO = new SubRoleFolderDAO(emf);
        CompanyDAO companyDAO = new CompanyDAO(emf);

        // Initialize Services
        folderService = new FolderService(folderDAO, companyDAO);
        companyService = new CompanyService(companyDAO);

        // Initialize Controllers
        roleFolderController = new RoleFolderController(roleFolderDAO);
        subRoleController = new SubRoleController(subRoleFolderDAO);
        folderController = new FolderController(folderService, companyService);
        companyController = new CompanyController(companyService, folderService, roleFolderController, subRoleController);

        // Initialize Security Controller
        securityController = new TestSecurityController();

        // Initialize the application
        app = Javalin.create(config -> {});

        // Register endpoints
        Endpoints endpoints = new Endpoints(securityController, folderController, companyController, roleFolderController, subRoleController);
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
        // Create company
        Company exampleCompany = createCompany("ExampleCompany");

        // Create roleFolders
        RoleFolder userRoleFolder = createRole("USER", exampleCompany);
        RoleFolder guestRoleFolder = createRole("GUEST", exampleCompany);

        // Insert test folder
        insertTestFolder(123L, "Test Folder", exampleCompany, guestRoleFolder, null);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = userRoleFolder.getId().toString();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/123/role")
                    .then()
                    .statusCode(200)
                    .body(equalTo("RoleFolder updated successfully."));
        });

        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, 123L);
        assertNotNull(folder);
        assertEquals(userRoleFolder.getId(), folder.getRoleFolder().getId());
        em.close();
    }

    @Test
    void testAssignSubRole_Success() {
        // Create company
        Company exampleCompany = createCompany("ExampleCompany");

        // Create roleFolder and subroles
        RoleFolder userRoleFolder = createRole("USER", exampleCompany);
        SubRoleFolder mediumSubRoleFolder = createSubRole("MEDIUM", exampleCompany);
        SubRoleFolder highSubRoleFolder = createSubRole("HIGH", exampleCompany);

        // Insert test folder
        insertTestFolder(124L, "Test Folder SubRoleFolder", exampleCompany, userRoleFolder, mediumSubRoleFolder);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = highSubRoleFolder.getId().toString();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/124/subrole")
                    .then()
                    .statusCode(200)
                    .body(equalTo("SubRoleFolder updated successfully."));
        });

        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, 124L);
        assertNotNull(folder);
        assertEquals(highSubRoleFolder.getId(), folder.getSubRoleFolder().getId());
        em.close();
    }

    @Test
    void testGetFoldersByCompany_Success() {
        // Create companies
        Company exampleCompany = createCompany("ExampleCompany");
        Company otherCompany = createCompany("OtherCompany");

        // Create roleFolders and subroles
        RoleFolder userRoleFolder = createRole("USER", exampleCompany);
        RoleFolder managerRoleFolder = createRole("MANAGER", exampleCompany);
        SubRoleFolder mediumSubRoleFolder = createSubRole("MEDIUM", exampleCompany);
        SubRoleFolder highSubRoleFolder = createSubRole("HIGH", exampleCompany);

        // Insert test folders
        insertTestFolder(1L, "Folder One", exampleCompany, userRoleFolder, mediumSubRoleFolder);
        insertTestFolder(2L, "Folder Two", exampleCompany, managerRoleFolder, highSubRoleFolder);
        insertTestFolder(3L, "Folder Three", otherCompany, userRoleFolder, null);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .when()
                    .get("/companies/name/ExampleCompany/folders")
                    .then()
                    .statusCode(200)
                    .body("$", hasSize(2));
        });
    }

    // Helper methods
    private RoleFolder createRole(String roleName, Company company) {
        EntityManager em = emf.createEntityManager();
        RoleFolder roleFolder = new RoleFolder();
        roleFolder.setName(roleName);
        roleFolder.setCompany(company); // Set the company
        em.getTransaction().begin();
        em.persist(roleFolder);
        em.getTransaction().commit();
        em.close();
        return roleFolder;
    }

    private SubRoleFolder createSubRole(String subRoleName, Company company) {
        EntityManager em = emf.createEntityManager();
        SubRoleFolder subRoleFolder = new SubRoleFolder();
        subRoleFolder.setName(subRoleName);
        subRoleFolder.setCompany(company); // Set the company
        em.getTransaction().begin();
        em.persist(subRoleFolder);
        em.getTransaction().commit();
        em.close();
        return subRoleFolder;
    }

    private Company createCompany(String companyName) {
        EntityManager em = emf.createEntityManager();
        Company company = new Company();
        company.setCompanyName(companyName);
        em.getTransaction().begin();
        em.persist(company);
        em.getTransaction().commit();
        em.close();
        return company;
    }

    private void insertTestFolder(Long id, String name, Company company, RoleFolder roleFolder, SubRoleFolder subRoleFolder) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Folder folder = new Folder();
            folder.setId(id);
            folder.setName(name);
            folder.setCompany(company);
            folder.setRoleFolder(roleFolder);
            folder.setSubRoleFolder(subRoleFolder);
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
        em.createQuery("DELETE FROM RoleFolder").executeUpdate();
        em.createQuery("DELETE FROM SubRoleFolder").executeUpdate();
        em.createQuery("DELETE FROM Company").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
