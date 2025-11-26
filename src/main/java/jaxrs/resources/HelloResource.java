package jaxrs.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.logging.Logger;

@Path("")
public class HelloResource {

    private static final Logger logger = Logger.getLogger(HelloResource.class.getName());

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello() {
        logger.fine("HelloResource#sayHello invoked");
        return Response.ok("Hello folks :)").build();
    }

}
