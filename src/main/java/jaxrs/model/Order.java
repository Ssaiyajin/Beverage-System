package jaxrs.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {

    private int orderId;
    private List<OrderItem> orderItems = Collections.emptyList();
    private double price;
    private OrderStatus status;

    public Order() {
        // keep defaults
    }

    public Order(int orderId, List<OrderItem> orderItems, double price, OrderStatus status) {
        this.orderId = orderId;
        this.orderItems = new ArrayList<>(Objects.requireNonNull(orderItems, "orderItems must not be null"));
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
        this.price = price;
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = new ArrayList<>(Objects.requireNonNullElse(orderItems, Collections.emptyList()));
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return orderId == that.orderId &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(orderItems, that.orderItems) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderItems, price, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderItems=" + orderItems +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
