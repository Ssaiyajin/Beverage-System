package jaxrs.model.api;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.net.URI;
import java.util.Objects;

@XmlRootElement
@XmlType(propOrder = {"page", "noOfPages", "first", "previous", "next", "last"})
public class Pagination {

    private int page;
    private int noOfPages;

    private URI first;
    private URI previous;
    private URI next;
    private URI last;

    public Pagination() { }

    /**
     * Build pagination metadata using the supplied base path (URI without query).
     *
     * @param path          base path (typically UriInfo.getAbsolutePath())
     * @param currentPage   requested page number (1-based)
     * @param pageLimit     items per page (must be >= 1)
     * @param collectionSize total number of items in the collection
     */
    public Pagination(final URI path, final int currentPage, final int pageLimit, final int collectionSize) {
        Objects.requireNonNull(path, "path must not be null");

        final int limit = Math.max(1, pageLimit);
        final int pages = (int) Math.ceil((double) Math.max(0, collectionSize) / (double) limit);

        this.noOfPages = Math.max(1, pages);
        this.page = Math.min(Math.max(1, currentPage), this.noOfPages);

        this.first = UriBuilder.fromUri(path)
                .queryParam("pageNo", 1)
                .queryParam("pageLimit", limit)
                .build();

        this.last = UriBuilder.fromUri(path)
                .queryParam("pageNo", this.noOfPages)
                .queryParam("pageLimit", limit)
                .build();

        this.previous = UriBuilder.fromUri(path)
                .queryParam("pageNo", Math.max(this.page - 1, 1))
                .queryParam("pageLimit", limit)
                .build();

        this.next = UriBuilder.fromUri(path)
                .queryParam("pageNo", Math.min(this.page + 1, this.noOfPages))
                .queryParam("pageLimit", limit)
                .build();
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getNoOfPages() {
        return this.noOfPages;
    }

    public void setNoOfPages(final int noOfPages) {
        this.noOfPages = noOfPages;
    }

    public URI getFirst() {
        return this.first;
    }

    public void setFirst(final URI first) {
        this.first = first;
    }

    public URI getPrevious() {
        return this.previous;
    }

    public void setPrevious(final URI previous) {
        this.previous = previous;
    }

    public URI getNext() {
        return this.next;
    }

    public void setNext(final URI next) {
        this.next = next;
    }

    public URI getLast() {
        return this.last;
    }

    public void setLast(final URI last) {
        this.last = last;
    }
}
