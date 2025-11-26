package jaxrs.dto;

import jaxrs.model.Crate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CrateDTO {
    private int id;
    private BottleDTO bottle;
    private int noOfBottles;
    private double price;
    private int inStock;

    public CrateDTO() {}

    public CrateDTO(Crate c, URI baseUri) {
        Objects.requireNonNull(c, "crate must not be null");
        this.id = c.getId();
        this.bottle = new BottleDTO(c.getBottle(), baseUri);
        this.noOfBottles = c.getNoOfBottles();
        this.price = c.getPrice();
        this.inStock = c.getInStock();
    }

    public static List<CrateDTO> marshall(List<Crate> models, URI baseUri) {
        List<CrateDTO> result = new ArrayList<>();
        if (models != null) {
            for (Crate c : models) result.add(new CrateDTO(c, baseUri));
        }
        return result;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public BottleDTO getBottle() { return bottle; }
    public void setBottle(BottleDTO bottle) { this.bottle = bottle; }
    public int getNoOfBottles() { return noOfBottles; }
    public void setNoOfBottles(int noOfBottles) { this.noOfBottles = noOfBottles; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getInStock() { return inStock; }
    public void setInStock(int inStock) { this.inStock = inStock; }
}
