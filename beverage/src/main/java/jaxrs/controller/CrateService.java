package jaxrs.controller;

import jaxrs.model.Crate;
import jaxrs.model.Bottle;
import jaxrs.dto.CrateDTO;
import jaxrs.dto.BottleDTO;

import java.util.ArrayList;
import java.util.List;

public class CrateService {
    public static final CrateService instance = new CrateService();

    private final List<Crate> crates = new ArrayList<>();
    private int nextId = 1;

    private CrateService() {}

    public List<Crate> getCrates() {
        return new ArrayList<>(crates);
    }

    public Crate getCrate(int id) {
        for (Crate c : crates) if (c.getId() == id) return c;
        return null;
    }

    public Crate createCrate(CrateDTO dto) {
        // convert nested BottleDTO to Bottle (crate keeps its own Bottle instance)
        BottleDTO bd = dto.getBottle();
        Bottle bottle = new Bottle(
                bd.getId() <= 0 ? 0 : bd.getId(),
                bd.getName(),
                bd.getVolume(),
                bd.isAlcoholic(),
                bd.getVolumePercent(),
                bd.getPrice(),
                bd.getSupplier(),
                bd.getInStock()
        );

        Crate c = new Crate(nextId++, bottle, dto.getNoOfBottles(), dto.getPrice(), dto.getInStock());
        crates.add(c);
        return c;
    }
}