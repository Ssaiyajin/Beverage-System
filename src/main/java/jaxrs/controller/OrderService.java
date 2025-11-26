package jaxrs.controller;

import jaxrs.db.DB;
import jaxrs.dto.BeverageDTO;
import jaxrs.dto.OrderDTO;
import jaxrs.dto.OrderItemDTO;
import jaxrs.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderService {
    public static final OrderService instance = new OrderService();
    private final DB db;

    public OrderService() {
        this.db = new DB();
    }

    public List<Order> getAllOrder() {
        return this.db.getAllOrder();
    }

    public List<Order> getAllOrderByStatus(OrderStatus orderStatus) {
        return this.db.getAllOrder().stream()
                .filter(e -> e.getStatus() == orderStatus)
                .collect(Collectors.toList());
    }

    /**
     * Submit an order built from a list of BeverageDTOs.
     * Each beverage in the list is treated as a single quantity item.
     */
    public Order submitOrder(List<BeverageDTO> beverageDTOList) {
        Objects.requireNonNull(beverageDTOList, "beverageDTOList must not be null");
        if (beverageDTOList.isEmpty()) {
            throw new IllegalArgumentException("beverageDTOList must not be empty");
        }

        Order newOrder = new Order();
        List<OrderItem> orderItems = new ArrayList<>();
        double totalPrice = 0.0;
        int count = 0;

        for (BeverageDTO beverageDTO : beverageDTOList) {
            Beverage beverage = BeverageService.instance.unmarshall(beverageDTO);
            if (beverage == null) continue;

            // treat each DTO as quantity = 1
            final int quantity = 1;
            totalPrice += beverage.getPrice() * quantity;

            // decrease stock by quantity: DB.updateXxx expects a model with id and inStock set
            if (beverage instanceof Bottle) {
                Bottle decrease = new Bottle();
                decrease.setId(((Bottle) beverage).getId());
                decrease.setInStock(quantity);
                BeverageService.instance.updateBottle(decrease, false);
            } else if (beverage instanceof Crate) {
                Crate decrease = new Crate();
                decrease.setId(((Crate) beverage).getId());
                decrease.setInStock(quantity);
                BeverageService.instance.updateCrate(decrease, false);
            }

            OrderItem newOrderItem = new OrderItem(++count, beverage, quantity);
            orderItems.add(newOrderItem);
        }

        newOrder.setPrice(totalPrice);
        newOrder.setOrderItems(orderItems);
        newOrder.setStatus(OrderStatus.SUBMITTED);
        return this.db.addOrder(newOrder);
    }

    public void changeOrderStatus(Order order, OrderStatus orderStatus) {
        this.db.changeOrderStatus(order, orderStatus);
    }

    public Order getOrder(int orderId) {
        return this.db.getOrder(orderId);
    }

    /**
     * Update an existing order from an OrderDTO.
     * Restores previous stock for the existing order, then applies the new items and adjusts stock accordingly.
     */
    public void updateOrder(OrderDTO orderDTO) {
        Objects.requireNonNull(orderDTO, "orderDTO must not be null");

        final Order existing = getOrder(orderDTO.getOrderId());
        if (existing == null) {
            return;
        }

        // restore stock from previous order items
        for (OrderItem orderItem : existing.getOrderItems()) {
            Beverage beverage = orderItem.getBeverage();
            int qty = orderItem.getQuantity();
            if (beverage instanceof Bottle) {
                Bottle restore = new Bottle();
                restore.setId(((Bottle) beverage).getId());
                restore.setInStock(qty);
                BeverageService.instance.updateBottle(restore, true);
            } else if (beverage instanceof Crate) {
                Crate restore = new Crate();
                restore.setId(((Crate) beverage).getId());
                restore.setInStock(qty);
                BeverageService.instance.updateCrate(restore, true);
            }
        }

        // build new order items and decrease stock for new items
        List<OrderItem> newItems = new ArrayList<>();
        double totalPrice = 0.0;
        int count = 0;

        final List<OrderItemDTO> itemDTOS = orderDTO.getOrderItemDTOS();
        if (itemDTOS != null) {
            for (OrderItemDTO itemDTO : itemDTOS) {
                BeverageDTO bevDTO = itemDTO.getBeverageDTO();
                Beverage beverage = BeverageService.instance.unmarshall(bevDTO);
                if (beverage == null) continue;

                int qty = Math.max(1, itemDTO.getQuantity());
                totalPrice += beverage.getPrice() * qty;

                if (beverage instanceof Bottle) {
                    Bottle decrease = new Bottle();
                    decrease.setId(((Bottle) beverage).getId());
                    decrease.setInStock(qty);
                    BeverageService.instance.updateBottle(decrease, false);
                } else if (beverage instanceof Crate) {
                    Crate decrease = new Crate();
                    decrease.setId(((Crate) beverage).getId());
                    decrease.setInStock(qty);
                    BeverageService.instance.updateCrate(decrease, false);
                }

                newItems.add(new OrderItem(++count, beverage, qty));
            }
        }

        existing.setOrderItems(newItems);
        existing.setStatus(OrderStatus.SUBMITTED);
        existing.setPrice(totalPrice);
    }
}
