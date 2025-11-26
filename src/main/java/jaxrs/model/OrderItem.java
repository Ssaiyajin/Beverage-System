package jaxrs.model;

import java.util.Objects;

public final class OrderItem {

    private final int number;
    private final Beverage beverage;
    private final int quantity;

    public OrderItem(int number, Beverage beverage, int quantity) {
        if (number < 0) throw new IllegalArgumentException("number must be >= 0");
        this.number = number;
        this.beverage = Objects.requireNonNull(beverage, "beverage must not be null");
        if (quantity < 1) throw new IllegalArgumentException("quantity must be >= 1");
        this.quantity = quantity;
    }

    public int getNumber() {
        return number;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return number == that.number &&
                quantity == that.quantity &&
                Objects.equals(beverage, that.beverage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, beverage, quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "number=" + number +
                ", beverage=" + beverage +
                ", quantity=" + quantity +
                '}';
    }
}
