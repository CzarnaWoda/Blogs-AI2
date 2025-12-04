package me.blackwater.blogsai2.application.dto;

public record SectionDto(
        long id,
        String creatorName,
        String creatorEmail,
        String title,
        String description,
        int views,
        String createdAt,
        String type
) {
}
