/* package org.routes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.dtos.FileDTO;
import org.junit.jupiter.api.*;
import org.persistence.HibernateConfig;
import org.persistence.model.FileData;
import org.rest.ApplicationConfig;

import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RouteFileDataTest {

    private static EntityManagerFactory emf;
    private static ApplicationConfig app;

    @BeforeAll
    static void setUpBeforeAll() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7007;
        RestAssured.basePath = "/api/files";
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        Route route = new Route(emf);

        app = ApplicationConfig.getInstance();
        app.initiateServer()
                .startServer(RestAssured.port)
                .setExceptionHandlers()
                .setRoute(route.addRoutes());
    }

    @AfterAll
    static void shutDownAfterAll() {
        emf.close();
        app.stopServer();
    }

    @BeforeEach
    public void beforeEach() {
        URL testfile = getClass().getResource("testfile.txt");
        java.io.File file = new java.io.File(testfile.getPath());
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createNativeQuery("TRUNCATE TABLE File RESTART IDENTITY").executeUpdate();
            em.persist(new FileData("Profile picture", "work", file));
            em.persist(new FileData("Profile picture", "work", file));
            em.persist(new FileData("Profile picture", "work", file));
            em.persist(new FileData("Profile picture", "work", file));
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
    @DisplayName("Testing that the application is not null.")
    public void testFileDAO() {
        // Given

        // When

        // Then
        assertNotNull(app);
    }

    @Test
    @DisplayName("Creating a file")
    void test1() {
        URL testfile = getClass().getResource("testfile.txt");
        java.io.File file = new java.io.File(testfile.getPath());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new FileData("Profile picture", "work", file))
                .when()
                .post("/")
                .then()
                .statusCode(200)
                .body("id", greaterThan(0))
                .body("folderPath", equalTo("folder"))
                .body("name", equalTo("profile-picture1"))
                .body("fileType", equalTo(".jpg"));
    }

    @Test
    @DisplayName("Reading a file by id")
    void test2() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .get("/file/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("folderPath", equalTo("folder"))
                .body("name", equalTo("profile-picture1"))
                .body("fileType", equalTo(".jpg"));
    }

    @Test
    @DisplayName("Updating a file")
    void test3() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(new FileDTO(1, "folder", "profile-picture4", ".png", "work", ".png"))
                .when()
                .put("/")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("folderPath", equalTo("folder"))
                .body("name", equalTo("profile-picture4"))
                .body("fileType", equalTo(".png"));
    }

    @Test
    @DisplayName("Deleting file by id")
    void test4() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .pathParam("id", 1)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("folderPath", equalTo("folder"))
                .body("name", equalTo("profile-picture1"))
                .body("fileType", equalTo(".jpg"));
    }

    @Test
    @DisplayName("Get all files in path")
    void test5() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .pathParam("folder_path", "folder")
                .when()
                .get("/{folder_path}")
                .then()
                .body("size()", equalTo(3))
                .body("[0].id", equalTo(1))
                .body("[0].folderPath", equalTo("folder"))
                .body("[0].name", equalTo("profile-picture1"))
                .body("[0].fileType", equalTo(".jpg"))
                .body("[1].id", equalTo(2))
                .body("[1].folderPath", equalTo("folder"))
                .body("[1].name", equalTo("profile-picture2"))
                .body("[1].fileType", equalTo(".jpg"));
    }

    @Test
    @DisplayName("Get all files in path by file type")
    void test6() {
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .pathParam("folder_path", "folder2")
                .pathParam("file_type", ".png")
                .when()
                .get("/{folder_path}/{file_type}")
                .then()
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(3))
                .body("[0].folderPath", equalTo("folder2"))
                .body("[0].name", equalTo("profile-picture3"))
                .body("[0].fileType", equalTo(".png"));
    }
}

 */