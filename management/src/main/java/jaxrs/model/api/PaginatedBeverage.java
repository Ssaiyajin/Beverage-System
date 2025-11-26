package jaxrs.model.api;

import jaxrs.dto.BeverageDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"pagination", "dtos", "href"})
public class PaginatedBeverage {
    private Pagination pagination;
    private List<BeverageDTO> dtos;
    private URI href;

    public PaginatedBeverage() {
        // JAXB
    }

    public PaginatedBeverage(final Pagination pagination, final List<BeverageDTO> dtos, final URI href) {
        this.pagination = Objects.requireNonNull(pagination, "pagination must not be null");
        this.dtos = Objects.requireNonNullElse(dtos, List.of());
        this.href = href;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<BeverageDTO> getDtos() {
        return dtos == null ? List.of() : List.copyOf(dtos);
    }

    public void setDtos(List<BeverageDTO> dtos) {
        this.dtos = Objects.requireNonNullElse(dtos, List.of());
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
