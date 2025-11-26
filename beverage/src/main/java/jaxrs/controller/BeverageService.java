package jaxrs.controller;

import jaxrs.db.DB;
import jaxrs.dto.BeverageDTO;
import jaxrs.model.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeverageService {
    public static final BeverageService instance = new BeverageService();
    private final DB db;

    public BeverageService() {
        this.db = new DB();
    }

    public List<Bottle> getAllBottles() {
        return db.getAllBottles();
    }

    public List<Crate> getAllCrates() {
        return db.getAllCrates();
    }

    public Bottle getBottle(final int id) {
        return db.getBottle(id);
    }

    public Crate getCrate(final int id) {
        return db.getCrate(id);
    }

    public List<Beverage> getBeverages() {
        return db.getBeverages();
    }

    public List<Beverage> getBeverages(double min, double max) {
        return db.getBeverages(min, max);
    }

    public Beverage unmarshall(final BeverageDTO beverageDTO) {
        Objects.requireNonNull(beverageDTO, "beverageDTO must not be null");
        return beverageDTO.unmarshall();
    }

    public void updateBottle(final Bottle bottle, final boolean isStockIncrease) {
        if (bottle == null) return;
        db.updateBottle(bottle, isStockIncrease);
    }

    public void updateCrate(final Crate crate, final boolean isStockIncrease) {
        if (crate == null) return;
        db.updateCrate(crate, isStockIncrease);
    }

    public List<Beverage> createNewBeverages(final List<BeverageDTO> beverageDTOList) {
        final List<BeverageDTO> list = Objects.requireNonNullElse(beverageDTOList, List.of());
        return list.stream()
                .filter(Objects::nonNull)
                .map(this::unmarshall)
                .filter(Objects::nonNull)
                .map(this::createNewBeverage)
                .collect(Collectors.toUnmodifiableList());
    }

    public Beverage updateBeverage(final BeverageDTO beverageDTO) {
        Objects.requireNonNull(beverageDTO, "beverageDTO must not be null");
        final Beverage beverage = unmarshall(beverageDTO);
        return createNewBeverage(beverage);
    }

    public Beverage createNewBeverage(final Beverage beverage) {
        Objects.requireNonNull(beverage, "beverage must not be null");
        return db.createOrUpdateBeverage(beverage);
    }
}
