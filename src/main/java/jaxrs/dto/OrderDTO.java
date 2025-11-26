package jaxrs.dto;

import jaxrs.model.Order;
import jaxrs.model.OrderItem;
import jaxrs.model.OrderStatus;
import jaxrs.resources.EmployeeResource;
import jaxrs.resources.OrderResource;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = {"orderId", "orderItemDTOS", "price", "status","href","cancelOrderHref","processOrderHref"})
public class OrderDTO {

    private int orderId;
    @XmlElement(required = true)
    private List<OrderItemDTO> orderItemDTOS;
    @XmlElement(required = true)
    private double price;

    @XmlElement(required = true)
    private OrderStatus status;
    private URI href;

    private URI cancelOrderHref;
    private URI processOrderHref;


    public OrderDTO() {
        // JAXB
    }

    public OrderDTO(final Order order, final URI baseUri) {
        Objects.requireNonNull(order, "order must not be null");
        Objects.requireNonNull(baseUri, "baseUri must not be null");

        this.orderId = order.getOrderId();
        this.orderItemDTOS = order.getOrderItems().stream()
                .map(oi -> new OrderItemDTO(oi, baseUri))
                .collect(Collectors.toUnmodifiableList());
        this.price = order.getPrice();
        this.status = order.getStatus();

        this.href = UriBuilder.fromUri(baseUri)
                .path(OrderResource.class)
                .path(OrderResource.class, "getOrder")
                .build(this.orderId);

        this.cancelOrderHref = UriBuilder.fromUri(baseUri)
                .path(OrderResource.class)
                .path(OrderResource.class, "updateOrderOrderStatus")
                .build(this.orderId, "CANCELLED");

        this.processOrderHref = UriBuilder.fromUri(baseUri)
                .path(EmployeeResource.class)
                .path(EmployeeResource.class, "updateOrderOrderStatus")
                .build(this.orderId, "PROCESSED");
    }

    public static List<OrderDTO> marshall(final List<Order> orders, final URI baseUri) {
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        final List<Order> list = Objects.requireNonNullElse(orders, List.of());
        return list.stream()
                .map(o -> new OrderDTO(o, baseUri))
                .collect(Collectors.toUnmodifiableList());
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDTO> getOrderItemDTOS() {
        return orderItemDTOS == null ? List.of() : List.copyOf(orderItemDTOS);
    }

    public void setOrderItemDTOS(List<OrderItemDTO> orderItemDTOS) {
        this.orderItemDTOS = orderItemDTOS == null ? List.of() : new ArrayList<>(orderItemDTOS);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    public URI getCancelOrderHref() {
        return cancelOrderHref;
    }

    public void setCancelOrderHref(URI cancelOrderHref) {
        this.cancelOrderHref = cancelOrderHref;
    }

    public URI getProcessOrderHref() {
        return processOrderHref;
    }

    public void setProcessOrderHref(URI processOrderHref) {
        this.processOrderHref = processOrderHref;
    }
}
