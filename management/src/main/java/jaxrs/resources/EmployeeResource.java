package jaxrs.resources;

import jaxrs.controller.BeverageService;
import jaxrs.dto.BeverageDTO;
import jaxrs.dto.BottleDTO;
import jaxrs.dto.CrateDTO;
import jaxrs.error.ErrorMessage;
import jaxrs.error.ErrorType;
import jaxrs.model.Beverage;
import jaxrs.model.Bottle;
import jaxrs.model.Crate;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Path("employee")
public class EmployeeResource {
    private static final Logger logger = Logger.getLogger(EmployeeResource.class.getName());

    @POST
    @Path("createNewBeverages")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response createNewBeverages(final List<BeverageDTO> beverageDTOList,
                                       @Context final UriInfo uriInfo) {
        logger.info("create new beverages");
        final List<BeverageDTO> dtoList = Objects.requireNonNullElse(beverageDTOList, List.of());
        if (dtoList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final List<Beverage> beverages = BeverageService.instance.createNewBeverages(dtoList);
        final List<BeverageDTO> result = BeverageDTO.marshall(beverages, uriInfo == null ? URI.create("/") : uriInfo.getBaseUri());

        return Response.ok(new GenericEntity<List<BeverageDTO>>(List.copyOf(result)) {
        }).build();
    }

    @PUT
    @Path("updateBeverage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateBeverage(final BeverageDTO beverageDTO,
                                   @Context final UriInfo uriInfo) {
        logger.info("update beverage");
        if (beverageDTO == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty"))
                    .build();
        }

        final Beverage updated = BeverageService.instance.updateBeverage(beverageDTO);
        if (updated == null) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Failed to update beverage"))
                    .build();
        }

        final URI base = uriInfo == null ? URI.create("/") : uriInfo.getBaseUri();
        if (updated instanceof Bottle) {
            return Response.ok(new BottleDTO((Bottle) updated, base)).build();
        }
        return Response.ok(new CrateDTO((Crate) updated, base)).build();
    }
}
