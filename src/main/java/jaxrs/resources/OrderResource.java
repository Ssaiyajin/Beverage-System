package jaxrs.resources;

import jaxrs.controller.OrderService;
import jaxrs.dto.BeverageDTO;
import jaxrs.dto.OrderDTO;
import jaxrs.error.ErrorMessage;
import jaxrs.error.ErrorType;
import jaxrs.model.Order;
import jaxrs.model.OrderStatus;
import jaxrs.model.api.PaginatedOrder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.logging.Logger;

@Path("order")
public class OrderResource {
    private static final Logger logger = Logger.getLogger("OrderResource");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response paginatedOrder(@Context UriInfo uriInfo,
                                   @QueryParam("pageLimit") @DefaultValue("10") int pageLimit,
                                   @QueryParam("pageNo") @DefaultValue("1") int pageNo) {

        if (pageLimit < 1 || pageNo < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(
                    ErrorType.INVALID_PARAMETER,
                    "PageLimit or page is less than 1. Read the documentation for a proper handling!");

            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }

        PaginationHelper<Order> orderPaginationHelper =
                new PaginationHelper<>(OrderService.instance.getAllOrder());


        final PaginatedOrder response = new PaginatedOrder(
                orderPaginationHelper.getPagination(uriInfo, pageNo, pageLimit),
                OrderDTO.marshall(orderPaginationHelper.getPaginatedList(pageNo, pageLimit), uriInfo.getBaseUri()),
                uriInfo.getRequestUri()
        );
        return Response.ok(response).build();
    }

    @POST
    @Consumes("application/json")
    public Response submitOrder(final List<BeverageDTO> beverageDTOList,
                                @Context final UriInfo uriInfo) {
        logger.info("submit order ");
        if (beverageDTOList == null || beverageDTOList.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Order order = OrderService.instance.submitOrder(beverageDTOList);

        return Response.ok(UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderResource.class).path(
                OrderResource.class, "getOrder").build(order.getOrderId())).build();
    }

    @GET
    @Path("{orderId}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response getOrder(@Context final UriInfo info, @PathParam("orderId") final int orderId) {
        final Order order = OrderService.instance.getOrder(orderId);

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new OrderDTO(order, info.getBaseUri())).build();
    }

    @PUT
    @Path("updateOrder")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateOrder(final OrderDTO orderDTO, @Context final UriInfo info) {
        final Order order = OrderService.instance.getOrder(orderDTO.getOrderId());

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (order.getStatus() == OrderStatus.PROCESSED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER, "Order is already processed! You can't update it.")).build();
        }
        OrderService.instance.updateOrder(orderDTO);
        return Response.ok(new OrderDTO(order, info.getBaseUri())).build();
    }

    @GET
    @Path("changeOrderStatus/{orderId}/{orderStatus}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response updateOrderOrderStatus(@Context final UriInfo info,
                                           @PathParam("orderId") final int orderId,
                                           @PathParam("orderStatus") final String status) {
        final Order order = OrderService.instance.getOrder(orderId);
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (order.getStatus() == OrderStatus.PROCESSED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER,
                            "Order is already processed! You can't " +
                                    (orderStatus == OrderStatus.CANCELLED ? "cancel" : "process") + " this order.")).build();
        }
        if (order.getStatus() == OrderStatus.CANCELLED && orderStatus == OrderStatus.CANCELLED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER,
                            "Order is already cancelled! You can't " +
                                    "cancel" + " this order.")).build();
        }
        if (order.getStatus() == OrderStatus.PROCESSED && orderStatus == OrderStatus.PROCESSED) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorMessage(ErrorType.INVALID_PARAMETER,
                            "Order is already processed! You can't " +
                                    "process" + " this order.")).build();
        }
        OrderService.instance.changeOrderStatus(order, orderStatus);
        return Response.ok(new OrderDTO(order, info.getBaseUri())).build();
    }
}
