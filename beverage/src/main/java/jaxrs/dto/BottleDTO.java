package jaxrs.dto;

import jaxrs.model.Bottle;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BottleDTO {
    private int id;
    private String name;
    private double volume;
    private boolean alcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;

    public BottleDTO() {}

    public BottleDTO(Bottle b, URI baseUri) {
        Objects.requireNonNull(b, "bottle must not be null");
        // baseUri kept for compatibility with existing call sites
        this.id = b.getId();
        this.name = b.getName();
        this.volume = b.getVolume();
        this.alcoholic = b.isAlcoholic();
        this.volumePercent = b.getVolumePercent();
        this.price = b.getPrice();
        this.supplier = b.getSupplier();
        this.inStock = b.getInStock();
    }

    public static List<BottleDTO> marshall(List<Bottle> models, URI baseUri) {
        List<BottleDTO> result = new ArrayList<>();
        if (models != null) {
            for (Bottle b : models) result.add(new BottleDTO(b, baseUri));
        }
        return result;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }
    public boolean isAlcoholic() { return alcoholic; }
    public void setAlcoholic(boolean alcoholic) { this.alcoholic = alcoholic; }
    public double getVolumePercent() { return volumePercent; }
    public void setVolumePercent(double volumePercent) { this.volumePercent = volumePercent; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }
    public int getInStock() { return inStock; }
    public void setInStock(int inStock) { this.inStock = inStock; }
}
