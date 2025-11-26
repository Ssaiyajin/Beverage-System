package jaxrs.model;

import java.net.URI;
import java.util.Objects;

public final class Bottle implements Beverage {

    private int id;
    private String name;
    private double volume;
    private boolean isAlcoholic;
    private double volumePercent;
    private double price;
    private String supplier;
    private int inStock;

    public Bottle() {
        // for frameworks / JAXB
    }

    public Bottle(int id, String name, double volume, boolean isAlcoholic,
                  double volumePercent, double price, String supplier, int inStock) {
        setId(id);
        setName(name);
        setVolume(volume);
        setAlcoholic(isAlcoholic);
        setVolumePercent(volumePercent);
        setPrice(price);
        setSupplier(supplier);
        setInStock(inStock);
    }

    /**
     * Copy constructor kept for compatibility with DTO usage.
     * baseUri is intentionally unused but preserved to avoid signature changes.
     */
    public Bottle(final Bottle bottle, final URI baseUri) {
        Objects.requireNonNull(bottle, "bottle must not be null");
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        validateNonNegative(id, "id");
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String n = Objects.requireNonNull(name, "name must not be null").trim();
        if (n.isEmpty()) throw new IllegalArgumentException("name must not be empty");
        this.name = n;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        validateNonNegative(volume, "volume");
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        this.isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        if (volumePercent < 0.0 || volumePercent > 100.0)
            throw new IllegalArgumentException("volumePercent must be between 0 and 100");
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        validateNonNegative(price, "price");
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        String s = Objects.requireNonNull(supplier, "supplier must not be null").trim();
        if (s.isEmpty()) throw new IllegalArgumentException("supplier must not be empty");
        this.supplier = s;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        validateNonNegative(inStock, "inStock");
        this.inStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bottle)) return false;
        Bottle bottle = (Bottle) o;
        return id == bottle.id &&
                Double.compare(bottle.volume, volume) == 0 &&
                isAlcoholic == bottle.isAlcoholic &&
                Double.compare(bottle.volumePercent, volumePercent) == 0 &&
                Double.compare(bottle.price, price) == 0 &&
                inStock == bottle.inStock &&
                Objects.equals(name, bottle.name) &&
                Objects.equals(supplier, bottle.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, volume, isAlcoholic, volumePercent, price, supplier, inStock);
    }

    @Override
    public String toString() {
        return "Bottle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", volume=" + volume +
                ", isAlcoholic=" + isAlcoholic +
                ", volumePercent=" + volumePercent +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                ", inStock=" + inStock +
                '}';
    }

    // --- validation helpers ---
    private static void validateNonNegative(int value, String name) {
        if (value < 0) throw new IllegalArgumentException(name + " must be >= 0");
    }

    private static void validateNonNegative(double value, String name) {
        if (value < 0.0) throw new IllegalArgumentException(name + " must be >= 0");
    }
}