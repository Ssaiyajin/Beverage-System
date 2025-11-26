package jaxrs.dto;

import jaxrs.model.Beverage;
import jaxrs.model.Bottle;
import jaxrs.model.Crate;
import jakarta.xml.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
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
        this.crateDTO = null;
    }

    public BeverageDTO(final CrateDTO crateDTO) {
        this.crateDTO = Objects.requireNonNull(crateDTO, "crateDTO must not be null");
        this.bottleDTO = null;
    }

    public static BeverageDTO of(final BottleDTO bottleDTO) {
        return new BeverageDTO(bottleDTO);
    }

    public static BeverageDTO of(final CrateDTO crateDTO) {
        return new BeverageDTO(crateDTO);
    }

    /**
     * Marshall a list of model beverages into DTOs using the provided baseUri.
     * Returns an immutable list (never null).
     */
    public static List<BeverageDTO> marshall(final List<Beverage> beverageList,
                                             final URI baseUri) {
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        final List<Beverage> list = Objects.requireNonNullElse(beverageList, Collections.emptyList());
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
     * Convert this DTO into a model Beverage.
     * Throws IllegalStateException when neither bottle nor crate data is present.
     */
    public Beverage unmarshall() {
        if (this.bottleDTO != null) {
            return new Bottle(
                    this.bottleDTO.getId(),
                    this.bottleDTO.getName(),
                    this.bottleDTO.getVolume(),
                    this.bottleDTO.isAlcoholic(),
                    this.bottleDTO.getVolumePercent(),
                    this.bottleDTO.getPrice(),
                    this.bottleDTO.getSupplier(),
                    this.bottleDTO.getInStock()
            );
        } else if (this.crateDTO != null) {
            final BottleDTO inner = this.crateDTO.getBottle();
            Bottle bottleModel = null;
            if (inner != null) {
                bottleModel = new Bottle(
                        inner.getId(),
                        inner.getName(),
                        inner.getVolume(),
                        inner.isAlcoholic(),
                        inner.getVolumePercent(),
                        inner.getPrice(),
                        inner.getSupplier(),
                        inner.getInStock()
                );
            }
            return new Crate(
                    this.crateDTO.getId(),
                    bottleModel,
                    this.crateDTO.getNoOfBottles(),
                    this.crateDTO.getPrice(),
                    this.crateDTO.getInStock()
            );
        }
        throw new IllegalStateException("BeverageDTO does not contain bottle or crate data");
    }

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
