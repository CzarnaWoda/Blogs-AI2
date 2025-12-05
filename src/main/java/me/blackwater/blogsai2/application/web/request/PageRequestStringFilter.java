package me.blackwater.blogsai2.application.web.request;

public record PageRequestStringFilter(
        int page,
        int size,
        String stringFilter
) {

    public record PageRequestStringFilters(
            int page,
            int size,
            String stringFilter,
            String secondStringFilter
    ){

    }
}
