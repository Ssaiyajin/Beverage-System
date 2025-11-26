package jaxrs.resources;

import jaxrs.controller.CrateService;
import jaxrs.dto.CrateDTO;
import jaxrs.dto.ErrorMessage;
import jaxrs.dto.ErrorType;
import jaxrs.model.Crate;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("crates")
public class CrateResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrates(@Context UriInfo uriInfo) {
        try {
            List<Crate> models = CrateService.instance.getCrates();
            return Response.ok(new GenericEntity<List<CrateDTO>>(CrateDTO.marshall(models, uriInfo.getBaseUri())){}).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage(ErrorType.SERVER_ERROR, "Failed to fetch crates"))
                    .build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrate(@PathParam("id") int id, @Context UriInfo uriInfo) {
        Crate crate = CrateService.instance.getCrate(id);
        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Crate not found"))
                    .build();
        }
        return Response.ok(new CrateDTO(crate, uriInfo.getBaseUri())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCrate(CrateDTO crateDto, @Context UriInfo uriInfo) {
        Crate created = CrateService.instance.createCrate(crateDto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(new CrateDTO(created, uriInfo.getBaseUri())).build();
    }
}
