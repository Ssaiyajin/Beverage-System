package jaxrs.controller;

import jaxrs.dto.OrderDTO;
import jaxrs.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    public static final OrderService instance = new OrderService();

    private final Map<Integer, Order> orders = new HashMap<>();
    private int nextId = 1;

    private OrderService() {
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders.values());
    }

    public Order getOrder(int id) {
        return orders.get(id);
    }

    public Order createOrder(OrderDTO orderDto) {
        Order order = new Order();
        order.setId(nextId++);
        order.setPrice(orderDto.getPrice());
        order.setPositions(orderDto.getPositions());
        order.setStatus(orderDto.getStatus());
        orders.put(order.getId(), order);
        return order;
    }

    public Order updateOrder(int id, Order order) {
        if (orders.containsKey(id)) {
            order.setId(id);
            orders.put(id, order);
            return order;
        }
        return null;
    }

    public boolean deleteOrder(int id) {
        return orders.remove(id) != null;
    }
}
