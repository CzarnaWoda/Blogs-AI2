package me.blackwater.blogsai2.application.dto;

public record ArticleDto(
        long id,
        String title,
        String content,
        int views,
        int likes
) {
}
