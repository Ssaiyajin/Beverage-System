package jaxrs.dto;

import jaxrs.model.Crate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CrateDTO {
    private int id;
    private int bottleId;
    private int noOfBottles;
    private double price;
    private int inStock;
    private String href;

    public CrateDTO() {
    }

    public CrateDTO(Crate crate, URI baseUri) {
        this.id = crate.getId();
        this.bottleId = crate.getBottleId();
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();
        this.inStock = crate.getInStock();
        this.href = baseUri.toString() + "crates/" + crate.getId();
    }

    public static List<CrateDTO> marshall(List<Crate> crates, URI baseUri) {
        List<CrateDTO> dtos = new ArrayList<>();
        for (Crate crate : crates) {
            dtos.add(new CrateDTO(crate, baseUri));
        }
        return dtos;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
