package jaxrs.resources;

import jaxrs.controller.OrderService;
import jaxrs.dto.OrderDTO;
import jaxrs.dto.ErrorMessage;
import jaxrs.dto.ErrorType;
import jaxrs.model.Order;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("orders")
public class OrderResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders(@Context UriInfo uriInfo) {
        List<Order> models = OrderService.instance.getOrders();
        return Response.ok(new GenericEntity<List<OrderDTO>>(OrderDTO.marshall(models, uriInfo.getBaseUri())){}).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderDTO orderDto, @Context UriInfo uriInfo) {
        Order created = OrderService.instance.createOrder(orderDto);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(created.getId())).build();
        return Response.created(location).entity(new OrderDTO(created, uriInfo.getBaseUri())).build();
    }
}
