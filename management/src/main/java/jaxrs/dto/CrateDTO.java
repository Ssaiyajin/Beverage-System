package jaxrs.dto;

import jaxrs.model.Crate;
import jaxrs.resources.BeverageResource;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"id", "bottle", "noOfBottles", "price", "inStock", "href"})
public final class CrateDTO {

    private int id;

    @XmlElement(required = true)
    private BottleDTO bottle;

    @XmlElement(required = true)
    private int noOfBottles;

    @XmlElement(required = true)
    private double price;

    @XmlElement(required = true)
    private int inStock;

    private URI href;

    public CrateDTO() {
        // JAXB
    }

    public CrateDTO(final Crate crate, final URI baseUri) {
        Objects.requireNonNull(crate, "crate must not be null");
        Objects.requireNonNull(baseUri, "baseUri must not be null");

        this.id = crate.getId();
        this.bottle = new BottleDTO(crate.getBottle(), baseUri);
        this.noOfBottles = crate.getNoOfBottles();
        this.price = crate.getPrice();
        this.inStock = crate.getInStock();
        this.href = UriBuilder.fromUri(baseUri)
                .path(BeverageResource.class)
                .path(BeverageResource.class, "getCrate")
                .build(this.id);
    }

    public static List<CrateDTO> marshall(final List<Crate> crates, final URI baseUri) {
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        final List<Crate> list = Objects.requireNonNullElse(crates, List.of());
        return list.stream()
                .map(c -> new CrateDTO(c, baseUri))
                .collect(Collectors.toUnmodifiableList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BottleDTO getBottle() {
        return bottle;
    }

    public void setBottle(BottleDTO bottle) {
        this.bottle = bottle;
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

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
