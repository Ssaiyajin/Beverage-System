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
     * Validates inputs and normalizes page/pageLimit to at least 1.
     */
    public Pagination getPagination(final UriInfo info, int page, final int pageLimit) {
        Objects.requireNonNull(info, "UriInfo must not be null");
        PageInfo infoObj = computePageInfo(page, pageLimit);
        int totalSize = items.size();

        return new Pagination(info.getAbsolutePath(), infoObj.page, infoObj.limit, totalSize);
    }

    /**
     * Return an unmodifiable sublist for the requested page. Returns empty list when page is beyond range.
     */
    public List<T> getPaginatedList(int page, final int pageLimit) {
        PageInfo info = computePageInfo(page, pageLimit);
        int start = info.startIndex;
        int end = info.endIndex;

        if (start >= items.size()) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(items.subList(start, end));
    }

    /**
     * Helper structure for computed page indices.
     */
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

    /**
     * Normalize page and pageLimit and compute start/end indices (end exclusive).
     */
    private PageInfo computePageInfo(int page, int pageLimit) {
        int normalizedLimit = Math.max(1, pageLimit);
        int normalizedPage = Math.max(1, page);

        final int size = this.items.size();
        int startIndex = (normalizedPage - 1) * normalizedLimit;

        // If there are no items, keep indices at 0..0 (empty)
        if (size == 0) {
            return new PageInfo(normalizedPage, normalizedLimit, 0, 0);
        }

        // If requested page is beyond available items, return empty slice (start >= size)
        int endIndex = Math.min(size, startIndex + normalizedLimit);
        return new PageInfo(normalizedPage, normalizedLimit, startIndex, endIndex);
    }
}
