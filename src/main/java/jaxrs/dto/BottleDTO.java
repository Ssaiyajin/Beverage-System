package jaxrs.dto;

import jaxrs.model.Bottle;
import jaxrs.resources.BeverageResource;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = {"id", "name", "price", "volume", "inStock",
        "isAlcoholic", "volumePercent", "supplier", "href"})
public final class BottleDTO {

    private int id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private double volume;

    @XmlElement(required = true)
    private double price;
    @XmlElement(required = true)
    private int inStock;
    @XmlElement(required = true)
    private boolean isAlcoholic;
    @XmlElement(required = true)
    private double volumePercent;

    @XmlElement(required = true)
    private String supplier;

    private URI href;

    public BottleDTO() {
        // JAXB
    }

    public BottleDTO(final Bottle bottle, final URI baseUri) {
        Objects.requireNonNull(bottle, "bottle must not be null");
        Objects.requireNonNull(baseUri, "baseUri must not be null");

        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.price = bottle.getPrice();
        this.inStock = bottle.getInStock();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.supplier = bottle.getSupplier();
        this.href = UriBuilder.fromUri(baseUri)
                .path(BeverageResource.class)
                .path(BeverageResource.class, "getBottle")
                .build(this.id);
    }

    public static List<BottleDTO> marshall(final List<Bottle> bottleList, final URI baseUri) {
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        final List<Bottle> list = Objects.requireNonNullElse(bottleList, List.of());
        return list.stream()
                .map(b -> new BottleDTO(b, baseUri))
                .collect(Collectors.toUnmodifiableList());
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
