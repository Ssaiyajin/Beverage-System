package jaxrs.db;

import jaxrs.model.Beverage;
import jaxrs.model.Bottle;
import jaxrs.model.Crate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DB {

    private final List<Bottle> bottles;
    private final List<Crate> crates;

    public DB() {
        this.bottles = initBottles();
        this.crates = initCrates();
    }

    private List<Bottle> initBottles() {
        return new ArrayList<>(Arrays.asList(
                new Bottle(1, "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34),
                new Bottle(2, "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17),
                new Bottle(3, "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11),
                new Bottle(4, "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5),
                new Bottle(5, "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3),
                new Bottle(6, "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66),
                new Bottle(7, "Premium Tafelwasser", 0.7, false, 0.0, 4.29, "Franken Brunnen", 12),
                new Bottle(8, "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57),
                new Bottle(9, "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42),
                new Bottle(10, "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12),
                new Bottle(11, "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34),
                new Bottle(12, "Cola", 1.5, false, 0.0, 1.79, "CCC", 69),
                new Bottle(13, "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12),
                new Bottle(14, "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25),
                new Bottle(15, "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55),
                new Bottle(16, "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8)
        ));
    }

    private List<Crate> initCrates() {
        return new ArrayList<>(Arrays.asList(
                new Crate(1, this.bottles.get(0), 20, 14.99, 3),
                new Crate(2, this.bottles.get(1), 20, 15.99, 5),
                new Crate(3, this.bottles.get(2), 6, 30.00, 7),
                new Crate(4, this.bottles.get(7), 12, 1.99, 11),
                new Crate(5, this.bottles.get(8), 20, 11.99, 13),
                new Crate(6, this.bottles.get(11), 6, 10.99, 4),
                new Crate(7, this.bottles.get(12), 6, 11.99, 5),
                new Crate(8, this.bottles.get(13), 20, 35.00, 7),
                new Crate(9, this.bottles.get(14), 12, 20.00, 9)
        ));
    }

    public List<Bottle> getAllBottles() {
        return List.copyOf(this.bottles);
    }

    public List<Crate> getAllCrates() {
        return List.copyOf(this.crates);
    }

    public Bottle getBottle(final int id) {
        return this.bottles.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Crate getCrate(final int id) {
        return this.crates.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Beverage> getBeverages() {
        return Stream.concat(
                        this.bottles.stream().map(b -> (Beverage) b),
                        this.crates.stream().map(c -> (Beverage) c))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Beverage> getBeverages(double min, double max) {
        return Stream.concat(
                        this.bottles.stream().filter(b -> b.getPrice() >= min && b.getPrice() <= max).map(b -> (Beverage) b),
                        this.crates.stream().filter(c -> c.getPrice() >= min && c.getPrice() <= max).map(c -> (Beverage) c))
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateBottle(Bottle bottle, boolean isStockIncrease) {
        if (bottle == null) return;
        this.bottles.stream()
                .filter(e -> e.getId() == bottle.getId())
                .findFirst()
                .ifPresent(b -> {
                    final int newStock = isStockIncrease ? b.getInStock() + bottle.getInStock()
                            : b.getInStock() - bottle.getInStock();
                    b.setInStock(Math.max(0, newStock));
                });
    }

    public void updateCrate(Crate crate, boolean isStockIncrease) {
        if (crate == null) return;
        this.crates.stream()
                .filter(e -> e.getId() == crate.getId())
                .findFirst()
                .ifPresent(c -> {
                    final int newStock = isStockIncrease ? c.getInStock() + crate.getInStock()
                            : c.getInStock() - crate.getInStock();
                    c.setInStock(Math.max(0, newStock));
                });
    }

    public int getBottleMaxId() {
        return this.bottles.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0);
    }

    public int getCrateMaxId() {
        return this.crates.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0);
    }

    public Bottle createOrUpdateBottle(Bottle bottle, Bottle oldBottle) {
        Objects.requireNonNull(bottle, "bottle must not be null");
        if (oldBottle == null) oldBottle = new Bottle();
        if (bottle.getId() <= -1) {
            oldBottle.setId(getBottleMaxId() + 1);
        }
        oldBottle.setName(bottle.getName());
        oldBottle.setVolume(bottle.getVolume());
        oldBottle.setAlcoholic(bottle.isAlcoholic());
        oldBottle.setVolumePercent(bottle.getVolumePercent());
        oldBottle.setPrice(bottle.getPrice());
        oldBottle.setSupplier(bottle.getSupplier());
        oldBottle.setInStock(bottle.getInStock());
        return oldBottle;
    }

    public Crate createOrUpdateCrate(Crate crate, Crate oldcrate) {
        Objects.requireNonNull(crate, "crate must not be null");
        if (oldcrate == null) oldcrate = new Crate();
        if (crate.getId() <= -1) {
            oldcrate.setId(getCrateMaxId() + 1);
        }
        oldcrate.setBottle(crate.getBottle());
        oldcrate.setPrice(crate.getPrice());
        oldcrate.setNoOfBottles(crate.getNoOfBottles());
        oldcrate.setInStock(crate.getInStock());
        return oldcrate;
    }

    public Beverage createOrUpdateBeverage(Beverage beverage) {
        Objects.requireNonNull(beverage, "beverage must not be null");
        if (beverage instanceof Bottle) {
            return updateBottleDb((Bottle) beverage);
        } else if (beverage instanceof Crate) {
            return updateCrateDb((Crate) beverage);
        }
        return null;
    }

    private Bottle updateBottleDb(Bottle bottle) {
        final Optional<Bottle> bottleToUpdate = this.bottles.stream()
                .filter(b -> b.getId() == bottle.getId())
                .findFirst();

        if (bottleToUpdate.isPresent()) {
            return createOrUpdateBottle(bottle, bottleToUpdate.get());
        } else {
            Bottle created = createOrUpdateBottle(bottle, null);
            this.bottles.add(created);
            return created;
        }
    }

    private Crate updateCrateDb(Crate crate) {
        final Optional<Crate> crateToUpdate = this.crates.stream()
                .filter(c -> c.getId() == crate.getId())
                .findFirst();

        if (crateToUpdate.isPresent()) {
            return createOrUpdateCrate(crate, crateToUpdate.get());
        } else {
            Crate created = createOrUpdateCrate(crate, null);
            this.crates.add(created);
            return created;
        }
    }
}
