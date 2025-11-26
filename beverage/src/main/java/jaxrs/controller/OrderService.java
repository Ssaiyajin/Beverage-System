package jaxrs.controller;

import jaxrs.model.Order;
import jaxrs.dto.OrderDTO;
import java.util.List;
import java.util.ArrayList;

public class OrderService {
    public static final OrderService instance = new OrderService();
    private final List<Order> orders = new ArrayList<>();
    private int nextId = 1;

    private OrderService() {}

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public Order getOrder(int id) {
        for (Order o : orders) if (o.getId() == id) return o;
        return null;
    }

    public Order createOrder(OrderDTO dto) {
        Order o = new Order(nextId++);
        orders.add(o);
        return o;
    }
}