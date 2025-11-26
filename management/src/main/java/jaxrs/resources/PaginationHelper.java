package jaxrs.resources;

import jaxrs.model.api.Pagination;
import jakarta.ws.rs.core.UriInfo;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class PaginationHelper<T> {

    private final List<T> items;

    public PaginationHelper(final List<T> items) {
        this.items = Objects.requireNonNullElse(items, Collections.emptyList());
    }

    /**
     * Compute pagination metadata for the provided UriInfo and page parameters.
     * Normalizes page/pageLimit to sensible defaults and clamps values.
     */
    public Pagination getPagination(final UriInfo info, int page, final int pageLimit) {
        Objects.requireNonNull(info, "UriInfo must not be null");
        PageInfo pi = computePageInfo(page, pageLimit);
        return new Pagination(info.getAbsolutePath(), pi.page, pi.limit, items.size());
    }

    /**
     * Return an unmodifiable sublist for the requested page. Returns empty list when page is beyond range.
     */
    public List<T> getPaginatedList(int page, final int pageLimit) {
        PageInfo pi = computePageInfo(page, pageLimit);
        if (pi.startIndex >= items.size() || items.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(items.subList(pi.startIndex, pi.endIndex));
    }

    private static final class PageInfo {
        final int page;
        final int limit;
        final int startIndex;
        final int endIndex;

        PageInfo(int page, int limit, int startIndex, int endIndex) {
            this.page = page;
            this.limit = limit;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }

    private PageInfo computePageInfo(int page, int pageLimit) {
        final int limit = Math.max(1, pageLimit);
        int normalizedPage = Math.max(1, page);

        final int size = this.items.size();
        int start = (normalizedPage - 1) * limit;

        // if requested page is beyond available items and there are items, fall back to first page
        if (start >= size && size > 0) {
            normalizedPage = 1;
            start = 0;
        }

        int end = Math.min(size, start + limit);
        return new PageInfo(normalizedPage, limit, start, end);
    }
}
