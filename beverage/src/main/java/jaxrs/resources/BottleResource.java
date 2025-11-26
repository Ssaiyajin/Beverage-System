package jaxrs.resources;

import jaxrs.controller.BottleService;
import jaxrs.dto.BottleDTO;
import jaxrs.dto.ErrorMessage;
import jaxrs.dto.ErrorType;
import jaxrs.model.Bottle;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("bottles")
public class BottleResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottles(@Context UriInfo uriInfo) {
        try {
            List<Bottle> models = BottleService.instance.getBottles();
            return Response.ok(new GenericEntity<List<BottleDTO>>(BottleDTO.marshall(models, uriInfo.getBaseUri())){}).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage(ErrorType.SERVER_ERROR, "Failed to fetch bottles"))
                    .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBottle(@PathParam("id") int id, @Context UriInfo uriInfo) {
        Bottle bottle = BottleService.instance.getBottle(id);
        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Bottle not found"))
                    .build();
        }
        return Response.ok(new BottleDTO(bottle, uriInfo.getBaseUri())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBottle(BottleDTO bottleDto, @Context UriInfo uriInfo) {
        Bottle created = BottleService.instance.createBottle(bottleDto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(new BottleDTO(created, uriInfo.getBaseUri())).build();
    }
}
