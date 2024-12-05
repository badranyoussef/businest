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
import org.entities.Company;
import org.entities.Folder;
import org.entities.Role;
import org.entities.SubRole;
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
    private RoleController roleController;
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
        RoleDAO roleDAO = new RoleDAO(emf);
        SubRoleDAO subRoleDAO = new SubRoleDAO(emf);
        CompanyDAO companyDAO = new CompanyDAO(emf);

        // Initialize Services
        folderService = new FolderService(folderDAO, companyDAO);
        companyService = new CompanyService(companyDAO);

        // Initialize Controllers
        roleController = new RoleController(roleDAO);
        subRoleController = new SubRoleController(subRoleDAO);
        folderController = new FolderController(folderService, companyService);
        companyController = new CompanyController(companyService, folderService, roleController, subRoleController);

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
        // Create company
        Company exampleCompany = createCompany("ExampleCompany");

        // Create roles
        Role userRole = createRole("USER", exampleCompany);
        Role guestRole = createRole("GUEST", exampleCompany);

        // Insert test folder
        insertTestFolder(123L, "Test Folder", exampleCompany, guestRole, null);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = userRole.getId().toString();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/123/role")
                    .then()
                    .statusCode(200)
                    .body(equalTo("Role updated successfully."));
        });

        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, 123L);
        assertNotNull(folder);
        assertEquals(userRole.getId(), folder.getRole().getId());
        em.close();
    }

    @Test
    void testAssignSubRole_Success() {
        // Create company
        Company exampleCompany = createCompany("ExampleCompany");

        // Create role and subroles
        Role userRole = createRole("USER", exampleCompany);
        SubRole mediumSubRole = createSubRole("MEDIUM", exampleCompany);
        SubRole highSubRole = createSubRole("HIGH", exampleCompany);

        // Insert test folder
        insertTestFolder(124L, "Test Folder SubRole", exampleCompany, userRole, mediumSubRole);

        JavalinTest.test(app, (server, client) -> {
            RestAssured.port = server.port();

            String requestBody = highSubRole.getId().toString();

            RestAssured.given()
                    .header("Authorization", "Bearer validToken")
                    .contentType("application/json")
                    .body(requestBody)
                    .when()
                    .post("/folders/124/subrole")
                    .then()
                    .statusCode(200)
                    .body(equalTo("SubRole updated successfully."));
        });

        EntityManager em = emf.createEntityManager();
        Folder folder = em.find(Folder.class, 124L);
        assertNotNull(folder);
        assertEquals(highSubRole.getId(), folder.getSubRole().getId());
        em.close();
    }

    @Test
    void testGetFoldersByCompany_Success() {
        // Create companies
        Company exampleCompany = createCompany("ExampleCompany");
        Company otherCompany = createCompany("OtherCompany");

        // Create roles and subroles
        Role userRole = createRole("USER", exampleCompany);
        Role managerRole = createRole("MANAGER", exampleCompany);
        SubRole mediumSubRole = createSubRole("MEDIUM", exampleCompany);
        SubRole highSubRole = createSubRole("HIGH", exampleCompany);

        // Insert test folders
        insertTestFolder(1L, "Folder One", exampleCompany, userRole, mediumSubRole);
        insertTestFolder(2L, "Folder Two", exampleCompany, managerRole, highSubRole);
        insertTestFolder(3L, "Folder Three", otherCompany, userRole, null);

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
    private Role createRole(String roleName, Company company) {
        EntityManager em = emf.createEntityManager();
        Role role = new Role();
        role.setName(roleName);
        role.setCompany(company); // Set the company
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
        em.close();
        return role;
    }

    private SubRole createSubRole(String subRoleName, Company company) {
        EntityManager em = emf.createEntityManager();
        SubRole subRole = new SubRole();
        subRole.setName(subRoleName);
        subRole.setCompany(company); // Set the company
        em.getTransaction().begin();
        em.persist(subRole);
        em.getTransaction().commit();
        em.close();
        return subRole;
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

    private void insertTestFolder(Long id, String name, Company company, Role role, SubRole subRole) {
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
        em.createQuery("DELETE FROM Company").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
