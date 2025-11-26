package jaxrs.controller;

import jaxrs.dto.CrateDTO;
import jaxrs.model.Crate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrateService {
    public static final CrateService instance = new CrateService();

    private final Map<Integer, Crate> crates = new HashMap<>();
    private int nextId = 1;

    private CrateService() {
    }

    public List<Crate> getCrates() {
        return new ArrayList<>(crates.values());
    }

    public Crate getCrate(int id) {
        return crates.get(id);
    }

    public Crate createCrate(CrateDTO crateDto) {
        Crate crate = new Crate();
        crate.setId(nextId++);
        crate.setBottleId(crateDto.getBottleId());
        crate.setNoOfBottles(crateDto.getNoOfBottles());
        crate.setPrice(crateDto.getPrice());
        crate.setInStock(crateDto.getInStock());
        crates.put(crate.getId(), crate);
        return crate;
    }

    public Crate updateCrate(int id, Crate crate) {
        if (crates.containsKey(id)) {
            crate.setId(id);
            crates.put(id, crate);
            return crate;
        }
        return null;
    }

    public boolean deleteCrate(int id) {
        return crates.remove(id) != null;
    }
}
