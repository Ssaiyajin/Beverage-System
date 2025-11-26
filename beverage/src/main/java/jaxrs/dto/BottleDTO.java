package jaxrs.dto;

import jaxrs.model.Bottle;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class BottleDTO {
    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;
    private String href;

    public BottleDTO() {
    }

    public BottleDTO(Bottle bottle, URI baseUri) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
        this.href = baseUri.toString() + "bottles/" + bottle.getId();
    }

    public static List<BottleDTO> marshall(List<Bottle> bottles, URI baseUri) {
        List<BottleDTO> dtos = new ArrayList<>();
        for (Bottle bottle : bottles) {
            dtos.add(new BottleDTO(bottle, baseUri));
        }
        return dtos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
