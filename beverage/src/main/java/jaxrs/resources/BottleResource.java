package jaxrs.resources;

import jaxrs.error.ErrorMessage;
import jaxrs.error.ErrorType;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Singleton
@Path("beverage/bottles")
public class BottleResource {

    private static final Logger logger = Logger.getLogger(BottleResource.class.getName());
    private static final String DEFAULT_SERVICE_URL = "http://localhost:9999/v1/bottle/all";
    private Client client;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
    }

    @PreDestroy
    public void shutdown() {
        if (client != null) {
            client.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottles(@DefaultValue("0") @QueryParam("page") int page,
                               @DefaultValue("1000") @QueryParam("pageSize") int pageSize,
                               @DefaultValue("0") @QueryParam("minPrice") double minPrice,
                               @DefaultValue("1000") @QueryParam("maxPrice") double maxPrice,
                               @QueryParam("query") String query) {

        Response validation = validateParams(page, pageSize, minPrice, maxPrice);
        if (validation != null) {
            return validation;
        }

        try {
            String serviceUrl = System.getProperty("bottle.service.url", DEFAULT_SERVICE_URL);
            String entityResponse = fetchAllBottles(serviceUrl);

            return Response.status(Response.Status.OK)
                    .entity(entityResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            logger.severe("Error fetching bottles: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage(ErrorType.SERVER_ERROR, "Failed to fetch bottles"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    private Response validateParams(int page, int pageSize, double minPrice, double maxPrice) {
        if (page < 0 || pageSize < 0 || minPrice < 0 || maxPrice < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Parameter values must not be negative"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return null;
    }

    private String fetchAllBottles(String serviceUrl) {
        WebTarget webTarget = client.target(serviceUrl);
        Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

        logger.info("Status Code - Get Status   " + response.getStatus());
        String entityResponse = response.readEntity(String.class);
        logger.info("List of All bottles: " + entityResponse);

        return entityResponse;
    }
}
