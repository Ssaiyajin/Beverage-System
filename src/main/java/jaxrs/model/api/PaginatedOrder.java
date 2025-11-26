package jaxrs.model.api;

import jaxrs.dto.OrderDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"pagination", "dtos", "href"})
public class PaginatedOrder {
    private Pagination pagination;
    private List<OrderDTO> dtos;
    private URI href;

    public PaginatedOrder() { }

    public PaginatedOrder(final Pagination pagination, final List<OrderDTO> dtos, final URI href) {
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

    public List<OrderDTO> getDtos() {
        return dtos;
    }

    public void setDtos(List<OrderDTO> dtos) {
        this.dtos = dtos;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
