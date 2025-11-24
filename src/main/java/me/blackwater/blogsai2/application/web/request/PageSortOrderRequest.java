package me.blackwater.blogsai2.application.web.request;

public record PageSortOrderRequest(
        int page,
        int size,
        String sortDirection,
        String orderBy
) {
}
