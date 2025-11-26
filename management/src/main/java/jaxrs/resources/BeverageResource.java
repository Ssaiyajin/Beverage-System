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

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Path("beverage")
public class BeverageResource {

    private static final Logger logger = Logger.getLogger(BeverageResource.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response paginatedBeverage(@Context UriInfo uriInfo,
                                      @QueryParam("pageLimit") @DefaultValue("10") int pageLimit,
                                      @QueryParam("pageNo") @DefaultValue("1") int pageNo) {

        if (pageLimit < 1 || pageNo < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(
                    ErrorType.INVALID_PARAMETER,
                    "pageLimit and pageNo must be >= 1");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        Objects.requireNonNull(uriInfo, "UriInfo must not be null");

        PaginationHelper<Beverage> beveragePaginationHelper =
                new PaginationHelper<>(BeverageService.instance.getBeverages());

        final PaginatedBeverage response = new PaginatedBeverage(
                beveragePaginationHelper.getPagination(uriInfo, pageNo, pageLimit),
                BeverageDTO.marshall(beveragePaginationHelper.getPaginatedList(pageNo, pageLimit), uriInfo.getBaseUri()),
                uriInfo.getRequestUri()
        );
        return Response.ok(response).build();
    }

    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBeverage(@Context final UriInfo info,
                                   @QueryParam("min") @DefaultValue("1") double min,
                                   @QueryParam("max") @DefaultValue("2") double max) {
        logger.info("SEARCH: beverages");
        return Response.ok(new GenericEntity<List<BeverageDTO>>(BeverageDTO.marshall(
                BeverageService.instance.getBeverages(min, max),
                info.getBaseUri())) {
        }).build();
    }

    @GET
    @Path("bottle/{bottleId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getBottle(@Context final UriInfo info, @PathParam("bottleId") final int bottleId) {
        logger.info("Get bottle with id " + bottleId);
        final Bottle bottle = BeverageService.instance.getBottle(bottleId);

        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
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
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new CrateDTO(crate, info.getBaseUri())).build();
    }
}
