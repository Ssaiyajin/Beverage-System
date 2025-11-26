package jaxrs.model;

import java.util.Objects;

public class Crate implements Beverage {
    private int id;
    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public Crate() {
    }

    public Crate(int id, Bottle bottle, int noOfBottles, double price, int inStock) {
        if (id < 0) throw new IllegalArgumentException("id must be >= 0");
        this.id = id;
        this.bottle = Objects.requireNonNull(bottle, "bottle must not be null");
        if (noOfBottles < 1) throw new IllegalArgumentException("noOfBottles must be >= 1");
        this.noOfBottles = noOfBottles;
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
        this.price = price;
        if (inStock < 0) throw new IllegalArgumentException("inStock must be >= 0");
        this.inStock = inStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("id must be >= 0");
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
        if (price < 0) throw new IllegalArgumentException("price must be >= 0");
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        if (inStock < 0) throw new IllegalArgumentException("inStock must be >= 0");
        this.inStock = inStock;
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
}
