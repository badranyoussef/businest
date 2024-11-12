package org.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.HttpStatus;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;
import org.exceptions.ApiException;
import org.persistence.HibernateConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationConfig {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String timestamp = dateFormat.format(new Date());

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
    //private ISecurityController securityController = new SecurityController(emf);
    private ObjectMapper om = new ObjectMapper();
    private static ApplicationConfig instance;
    private Javalin app;

    private ApplicationConfig() {
        ObjectMapper om = new ObjectMapper();
        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.routing.contextPath = "/api";
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                    //it.allowHost("https://cphbusinessprojekt.dk");
                });
            });
        });
    }

    public static ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    public ApplicationConfig initiateServer() {
        return instance;
    }

    public ApplicationConfig startServer(int portNumber) {
        app.start(portNumber);
        return instance;
    }

    public ApplicationConfig setRoute(EndpointGroup route) {
        app.routes(route);
        return instance;
    }

    public ApplicationConfig setExceptionHandlers() {
        app.exception(Exception.class, (e, ctx) -> {
            ObjectNode node = om.createObjectNode().put("errorMessage from application config", e.getMessage());
            ctx.status(500).json(node);
        });

        app.error(404, ctx -> {
            ctx.status(404).result("Not Found");
        });

        app.exception(IllegalStateException.class, (e, ctx) -> {
            ctx.status(400).result("Bad Request: " + e.getMessage());
        });
        return instance;
    }

    public ApplicationConfig checkSecurityRoles() {
        app.updateConfig(config -> {
            config.accessManager((handler, ctx, permittedRoles) -> {
                for (RouteRole s : permittedRoles) {
                    System.out.println(s);
                }
                Set<String> allowedRoles = permittedRoles.stream().map(role -> role.toString().toUpperCase()).collect(Collectors.toSet());

                for (String s : allowedRoles) {
                    System.out.println("roles to pass by "+s);
                }

                if (allowedRoles.contains("ANYONE") || ctx.method().toString().equals("OPTIONS")) {
                    handler.handle(ctx);
                    return;
                }

                /*UserDTO user = ctx.attribute("user");
                System.out.println("USER IN CHECK_SEC_ROLES: " + user);
                if (user == null) {
                    ctx.status(HttpStatus.FORBIDDEN)
                            .json(om.createObjectNode()
                                    .put("msg", "Not authorized. No username were added from the token"));
                    return;
                }

                if (securityController.authorize(user, allowedRoles)) {
                    handler.handle(ctx);
                } else {
                    throw new ApiException(HttpStatus.FORBIDDEN.getCode(), "Unauthorized with roles: " + allowedRoles, timestamp);
                }*/
            });
        });
        return instance;
    }

    public void stopServer() {
        app.stop();
    }
}
