package jaxrs.model;

public class Crate {
    private int id;
    private int bottleId;
    private int noOfBottles;
    private double price;
    private int inStock;

    public Crate() {
    }

    public Crate(int id, int bottleId, int noOfBottles, double price, int inStock) {
        this.id = id;
        this.bottleId = bottleId;
        this.noOfBottles = noOfBottles;
        this.price = price;
        this.inStock = inStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBottleId() {
        return bottleId;
    }

    public void setBottleId(int bottleId) {
        this.bottleId = bottleId;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
}
