package jaxrs.dto;

import jaxrs.model.Order;
import java.net.URI;
import java.util.List;
import java.util.ArrayList;

public class OrderDTO {
    private int id;
    private List<Object> items = new ArrayList<>();

    public OrderDTO() {}

    public OrderDTO(Order order, URI baseUri) {
        if (order != null) this.id = order.getId();
    }

    public static List<OrderDTO> marshall(List<Order> models, URI baseUri) {
        List<OrderDTO> result = new ArrayList<>();
        if (models != null) for (Order o : models) result.add(new OrderDTO(o, baseUri));
        return result;
    }

    public List<Object> getItems() { return items; }
    public void setItems(List<Object> items) { this.items = items; }
}