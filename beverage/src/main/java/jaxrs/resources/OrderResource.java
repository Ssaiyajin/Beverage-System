package jaxrs.resources;

import jaxrs.controller.OrderService;
import jaxrs.dto.OrderDTO;
import jaxrs.error.ErrorMessage;
import jaxrs.error.ErrorType;
import jaxrs.model.Order;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Path("order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger logger = Logger.getLogger(OrderResource.class.getName());

    @GET
    public Response listOrders(@Context UriInfo uriInfo) {
        Objects.requireNonNull(uriInfo, "UriInfo must not be null");
        List<Order> models = OrderService.instance.getOrders();
        return Response.ok(new GenericEntity<List<OrderDTO>>(OrderDTO.marshall(models, uriInfo.getBaseUri())){}).build();
    }

    @GET
    @Path("{id}")
    public Response getOrder(@PathParam("id") int id, @Context UriInfo uriInfo) {
        Order order = OrderService.instance.getOrder(id);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorMessage(ErrorType.NOT_FOUND, "Order not found"))
                    .build();
        }
        return Response.ok(new OrderDTO(order, uriInfo.getBaseUri())).build();
    }

    @POST
    public Response createOrder(OrderDTO orderDto, @Context UriInfo uriInfo) {
        if (orderDto == null || orderDto.getItems() == null || orderDto.getItems().isEmpty()) {
            return badRequest("Order must contain at least one item");
        }

        Order created = OrderService.instance.createOrder(orderDto);
        URI location = uriInfo.getAbsolutePathBuilder().path(Integer.toString(created.getId())).build();
        logger.info("Created order " + created.getId());
        return Response.created(location).entity(new OrderDTO(created, uriInfo.getBaseUri())).build();
    }

    // basic validation helper
    private Response badRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, message))
                .build();
    }
}