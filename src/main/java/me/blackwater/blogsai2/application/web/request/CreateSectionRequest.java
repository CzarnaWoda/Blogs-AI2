package me.blackwater.blogsai2.application.web.request;

public record CreateSectionRequest(
        String creator,
        String title,
        String description,
        String type
) {
}
