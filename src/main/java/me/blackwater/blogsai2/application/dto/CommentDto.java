package me.blackwater.blogsai2.application.dto;

public record CommentDto(
        String value,
        String createdAt,
        String userName,
        int views,
        int likes
) {
}
