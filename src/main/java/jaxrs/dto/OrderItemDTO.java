package jaxrs.dto;

import jaxrs.model.Bottle;
import jaxrs.model.Crate;
import jaxrs.model.OrderItem;
import jakarta.xml.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
@XmlType(propOrder = {"number", "beverageDTO", "quantity"})
public class OrderItemDTO {

    @XmlElement(required = true)
    private int number;
    @XmlElement(required = true)
    private BeverageDTO beverageDTO;
    @XmlElement(required = true)
    private int quantity;

    public OrderItemDTO() {
        // JAXB
    }

    public OrderItemDTO(final OrderItem orderItem, final URI baseUri) {
        Objects.requireNonNull(orderItem, "orderItem must not be null");
        Objects.requireNonNull(baseUri, "baseUri must not be null");

        this.number = orderItem.getNumber();
        this.quantity = orderItem.getQuantity();

        if (orderItem.getBeverage() instanceof Bottle) {
            this.beverageDTO = BeverageDTO.of(new BottleDTO((Bottle) orderItem.getBeverage(), baseUri));
        } else {
            this.beverageDTO = BeverageDTO.of(new CrateDTO((Crate) orderItem.getBeverage(), baseUri));
        }
    }

    public static List<OrderItemDTO> marshall(final List<OrderItem> orderItems,
                                              final URI baseUri) {
        Objects.requireNonNull(baseUri, "baseUri must not be null");
        final List<OrderItem> list = Objects.requireNonNullElse(orderItems, List.of());
        return list.stream()
                .map(oi -> new OrderItemDTO(oi, baseUri))
                .collect(Collectors.toUnmodifiableList());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BeverageDTO getBeverageDTO() {
        return beverageDTO;
    }

    public void setBeverageDTO(BeverageDTO beverageDTO) {
        this.beverageDTO = beverageDTO;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
