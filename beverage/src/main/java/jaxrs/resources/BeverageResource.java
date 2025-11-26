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
import jaxrs.model.api.PaginatedBeverage;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Path("beverage")
@Produces(MediaType.APPLICATION_JSON)
public class BeverageResource {

    private static final Logger logger = Logger.getLogger(BeverageResource.class.getName());

    @GET
    public Response paginatedBeverage(@Context UriInfo uriInfo,
                                      @QueryParam("pageLimit") @DefaultValue("10") int pageLimit,
                                      @QueryParam("pageNo") @DefaultValue("1") int pageNo) {

        if (pageLimit < 1 || pageNo < 1) {
            return badRequest("pageLimit and pageNo must be >= 1");
        }

        Objects.requireNonNull(uriInfo, "UriInfo must not be null");

        PaginationHelper<Beverage> beveragePaginationHelper =
                new PaginationHelper<>(BeverageService.instance.getBeverages());

        final PaginatedBeverage response = new PaginatedBeverage(
                beveragePaginationHelper.getPagination(uriInfo, pageNo, pageLimit),
                BeverageDTO.marshall(beveragePaginationHelper.getPaginatedList(pageNo, pageLimit), uriInfo.getBaseUri()),
                uriInfo.getRequestUri());

        return Response.ok(response).build();
    }

    @GET
    @Path("search")
    public Response searchBeverage(@Context final UriInfo info,
                                   @QueryParam("min") @DefaultValue("1") double min,
                                   @QueryParam("max") @DefaultValue("2") double max) {
        Objects.requireNonNull(info, "UriInfo must not be null");
        logger.info("SEARCH: beverages");

        List<Beverage> models = BeverageService.instance.getBeverages(min, max);
        return Response.ok(dtoEntity(models, info.getBaseUri())).build();
    }

    @GET
    @Path("bottle/{bottleId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(@Context final UriInfo info, @PathParam("bottleId") final int bottleId) {
        logger.info("Get bottle with id " + bottleId);
        final Bottle bottle = BeverageService.instance.getBottle(bottleId);

        if (bottle == null) {
            return notFound();
        }
        return Response.ok(new BottleDTO(bottle, info.getBaseUri())).build();
    }

    @GET
    @Path("crate/{crateId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getCrate(@Context final UriInfo info, @PathParam("crateId") final int crateId) {
        logger.info("Get crate with id " + crateId);
        final Crate crate = BeverageService.instance.getCrate(crateId);

        if (crate == null) {
            return notFound();
        }
        return Response.ok(new CrateDTO(crate, info.getBaseUri())).build();
    }

    // small helpers extracted to keep endpoints concise
    private Response badRequest(String message) {
        final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, message);
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }

    private Response notFound() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private GenericEntity<List<BeverageDTO>> dtoEntity(List<Beverage> models, URI baseUri) {
        List<BeverageDTO> dtoList = BeverageDTO.marshall(models, baseUri);
        return new GenericEntity<List<BeverageDTO>>(dtoList) {};
    }
}
