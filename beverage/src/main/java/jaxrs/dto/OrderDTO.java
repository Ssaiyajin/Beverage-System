package jaxrs.dto;

import jaxrs.model.Order;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private int id;
    private double price;
    private List<Integer> positions;
    private String status;
    private String href;

    public OrderDTO() {
    }

    public OrderDTO(Order order, URI baseUri) {
        this.id = order.getId();
        this.price = order.getPrice();
        this.positions = order.getPositions();
        this.status = order.getStatus();
        this.href = baseUri.toString() + "orders/" + order.getId();
    }

    public static List<OrderDTO> marshall(List<Order> orders, URI baseUri) {
        List<OrderDTO> dtos = new ArrayList<>();
        for (Order order : orders) {
            dtos.add(new OrderDTO(order, baseUri));
        }
        return dtos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
