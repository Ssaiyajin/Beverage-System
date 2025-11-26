package jaxrs.controller;

import jaxrs.dto.BottleDTO;
import jaxrs.model.Bottle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottleService {
    public static final BottleService instance = new BottleService();

    private final Map<Integer, Bottle> bottles = new HashMap<>();
    private int nextId = 1;

    private BottleService() {
    }

    public List<Bottle> getBottles() {
        return new ArrayList<>(bottles.values());
    }

    public Bottle getBottle(int id) {
        return bottles.get(id);
    }

    public Bottle createBottle(BottleDTO bottleDto) {
        Bottle bottle = new Bottle();
        bottle.setId(nextId++);
        bottle.setName(bottleDto.getName());
        bottle.setVolume(bottleDto.getVolume());
        bottle.setAlcoholic(bottleDto.isAlcoholic());
        bottle.setVolumePercent(bottleDto.getVolumePercent());
        bottle.setPrice(bottleDto.getPrice());
        bottle.setSupplier(bottleDto.getSupplier());
        bottle.setInStock(bottleDto.getInStock());
        bottles.put(bottle.getId(), bottle);
        return bottle;
    }

    public Bottle updateBottle(int id, Bottle bottle) {
        if (bottles.containsKey(id)) {
            bottle.setId(id);
            bottles.put(id, bottle);
            return bottle;
        }
        return null;
    }

    public boolean deleteBottle(int id) {
        return bottles.remove(id) != null;
    }
}
