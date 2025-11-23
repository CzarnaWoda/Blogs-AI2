package me.blackwater.blogsai2.application.web.request;

public record GetSectionByTypeRequest(
        int page,
        int size,
        String type
) {
}
