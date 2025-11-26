package jaxrs.controller;

import jaxrs.model.Bottle;
import jaxrs.dto.BottleDTO;

import java.util.ArrayList;
import java.util.List;

public class BottleService {
    public static final BottleService instance = new BottleService();

    private final List<Bottle> bottles = new ArrayList<>();
    private int nextId = 1;

    private BottleService() {}

    public List<Bottle> getBottles() {
        return new ArrayList<>(bottles);
    }

    public Bottle getBottle(int id) {
        for (Bottle b : bottles) if (b.getId() == id) return b;
        return null;
    }

    public Bottle createBottle(BottleDTO dto) {
        int id = nextId++;
        Bottle b = new Bottle(
                id,
                dto.getName(),
                dto.getVolume(),
                dto.isAlcoholic(),
                dto.getVolumePercent(),
                dto.getPrice(),
                dto.getSupplier(),
                dto.getInStock()
        );
        bottles.add(b);
        return b;
    }
}