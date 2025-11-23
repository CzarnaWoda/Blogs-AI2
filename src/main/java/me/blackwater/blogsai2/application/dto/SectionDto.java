package me.blackwater.blogsai2.application.dto;

public record SectionDto(
        long id,
        String title,
        String description,
        int views,
        String createdAt,
        String type
) {
}
