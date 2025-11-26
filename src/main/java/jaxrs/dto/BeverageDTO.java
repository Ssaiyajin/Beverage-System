package jaxrs.dto;

import jaxrs.model.Beverage;
import jaxrs.model.Bottle;
import jaxrs.model.Crate;
import jakarta.xml.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverage")
@XmlType(propOrder = {"bottleDTO", "crateDTO"})
public class BeverageDTO {

    @XmlElement
    private BottleDTO bottleDTO;
    @XmlElement
    private CrateDTO crateDTO;

    public BeverageDTO() {
        // JAXB
    }

    public BeverageDTO(final BottleDTO bottleDTO) {
        this.bottleDTO = Objects.requireNonNull(bottleDTO, "bottleDTO must not be null");
    }

    public BeverageDTO(final CrateDTO crateDTO) {
        this.crateDTO = Objects.requireNonNull(crateDTO, "crateDTO must not be null");
    }

    public static BeverageDTO of(final BottleDTO bottleDTO) {
        return new BeverageDTO(bottleDTO);
    }

    public static BeverageDTO of(final CrateDTO crateDTO) {
        return new BeverageDTO(crateDTO);
    }

    public static List<BeverageDTO> marshall(final List<Beverage> beverageList,
                                             final URI baseUri) {
        final List<Beverage> list = Objects.requireNonNullElse(beverageList, List.of());
        final ArrayList<BeverageDTO> beverageDTOS = new ArrayList<>(list.size());
        for (final Beverage beverage : list) {
            if (beverage instanceof Bottle) {
                BottleDTO bottleDto = new BottleDTO((Bottle) beverage, baseUri);
                beverageDTOS.add(BeverageDTO.of(bottleDto));
            } else if (beverage instanceof Crate) {
                beverageDTOS.add(BeverageDTO.of(new CrateDTO((Crate) beverage, baseUri)));
            }
        }
        return List.copyOf(beverageDTOS);
    }

    /**
     * Convert this DTO back to a model Beverage.
     * Throws IllegalStateException if neither bottleDTO nor crateDTO is present.
     */
    public Beverage unmarshall() {
        if (this.bottleDTO != null) {
            return new Bottle(this.bottleDTO.getId(),
                    this.bottleDTO.getName(),
                    this.bottleDTO.getVolume(),
                    bottleDTO.isAlcoholic(),
                    bottleDTO.getVolumePercent(),
                    this.bottleDTO.getPrice(),
                    bottleDTO.getSupplier(),
                    this.bottleDTO.getInStock());
        } else if (this.crateDTO != null) {
            return new Crate(this.crateDTO.getId(), this.crateDTO.getBottle(),
                    this.crateDTO.getNoOfBottles(), this.crateDTO.getPrice(),
                    this.crateDTO.getInStock()
            );
        }
        throw new IllegalStateException("BeverageDTO does not contain bottle or crate data");
    }

    // getters/setters for JAXB and other consumers

    public BottleDTO getBottleDTO() {
        return bottleDTO;
    }

    public void setBottleDTO(BottleDTO bottleDTO) {
        this.bottleDTO = bottleDTO;
        this.crateDTO = null;
    }

    public CrateDTO getCrateDTO() {
        return crateDTO;
    }

    public void setCrateDTO(CrateDTO crateDTO) {
        this.crateDTO = crateDTO;
        this.bottleDTO = null;
    }
}
