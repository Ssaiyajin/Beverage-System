package jaxrs.model;

import java.util.Objects;

public final class Crate implements Beverage {
    private int id;
    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public Crate() {
        // for frameworks / DTOs
    }

    public Crate(int id, Bottle bottle, int noOfBottles, double price, int inStock) {
        setId(id);
        setBottle(bottle);
        setNoOfBottles(noOfBottles);
        setPrice(price);
        setInStock(inStock);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        validateNonNegative(id, "id");
        this.id = id;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = Objects.requireNonNull(bottle, "bottle must not be null");
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        if (noOfBottles < 1) throw new IllegalArgumentException("noOfBottles must be >= 1");
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        validateNonNegative(price, "price");
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        validateNonNegative(inStock, "inStock");
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Crate{" +
                "id=" + id +
                ", bottle=" + bottle +
                ", noOfBottles=" + noOfBottles +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crate)) return false;
        Crate crate = (Crate) o;
        return id == crate.id &&
                noOfBottles == crate.noOfBottles &&
                Double.compare(crate.price, price) == 0 &&
                inStock == crate.inStock &&
                Objects.equals(bottle, crate.bottle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bottle, noOfBottles, price, inStock);
    }

    // validation helpers
    private static void validateNonNegative(int value, String name) {
        if (value < 0) throw new IllegalArgumentException(name + " must be >= 0");
    }

    private static void validateNonNegative(double value, String name) {
        if (value < 0.0) throw new IllegalArgumentException(name + " must be >= 0");
    }
}
