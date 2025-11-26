package jaxrs.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private double price;
    private List<Integer> positions;
    private String status;

    public Order() {
        this.positions = new ArrayList<>();
    }

    public Order(int id, double price, List<Integer> positions, String status) {
        this.id = id;
        this.price = price;
        this.positions = positions != null ? positions : new ArrayList<>();
        this.status = status;
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
        this.positions = positions != null ? positions : new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
