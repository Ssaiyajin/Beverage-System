package jaxrs.resources;

import jaxrs.controller.BeverageService;
import jaxrs.controller.OrderService;
import jaxrs.dto.BeverageDTO;
import jaxrs.dto.BottleDTO;
import jaxrs.dto.CrateDTO;
import jaxrs.dto.OrderDTO;
import jaxrs.error.ErrorMessage;
import jaxrs.error.ErrorType;
import jaxrs.model.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
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
        if (beverageDTOList == null || beverageDTOList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        List<Beverage> beverages = BeverageService.instance.createNewBeverages(beverageDTOList);

        return Response.ok(new GenericEntity<List<BeverageDTO>>(BeverageDTO.marshall(
                beverages,
                uriInfo.getBaseUri())) {
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
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Beverage beverage = BeverageService.instance.updateBeverage(beverageDTO);
        if (beverage instanceof Bottle)
            return Response.ok(new BottleDTO((Bottle) beverage, uriInfo.getBaseUri())).build();
        return Response.ok(new CrateDTO((Crate) beverage, uriInfo.getBaseUri())).build();

    }

    @GET
    @Path("changeOrderStatus/{orderId}/{orderStatus}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateOrderOrderStatus(@Context final UriInfo info,
                                           @PathParam("orderId") final int orderId,
                                           @PathParam("orderStatus") final String status) {
        final Order order = OrderService.instance.getOrder(orderId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        final OrderStatus orderStatus;
        try {
            orderStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Invalid orderStatus value")).build();
        }

        Response validationError = validateStatusChange(order, orderStatus);
        if (validationError != null) {
            return validationError;
        }

        OrderService.instance.changeOrderStatus(order, orderStatus);
        return Response.ok(new OrderDTO(order, info.getBaseUri())).build();
    }

    @GET
    @Path("getAllSubmittedOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getAllSubmittedOrder(@Context final UriInfo info) {
        logger.info("get all order");
        return Response.ok(new GenericEntity<List<OrderDTO>>(OrderDTO.marshall(
                OrderService.instance.getAllOrderByStatus(OrderStatus.SUBMITTED),
                info.getBaseUri())) {
        }).build();
    }

    private Response validateStatusChange(final Order order, final OrderStatus targetStatus) {
        // Prevent changing a processed order
        if (order.getStatus() == OrderStatus.PROCESSED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER,
                            "Order is already processed! You can't " +
                                    (targetStatus == OrderStatus.CANCELLED ? "cancel" : "process") + " this order.")).build();
        }

        // Prevent re-cancelling an already cancelled order
        if (order.getStatus() == OrderStatus.CANCELLED && targetStatus == OrderStatus.CANCELLED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER,
                            "Order is already cancelled! You can't cancel this order.")).build();
        }

        // Prevent no-op status change (already in requested state)
        if (order.getStatus() == targetStatus) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER,
                            "Order is already " + targetStatus.name().toLowerCase() + "!")).build();
        }

        return null;
    }
}
