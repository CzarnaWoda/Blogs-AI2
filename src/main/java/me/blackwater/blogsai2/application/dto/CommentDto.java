package me.blackwater.blogsai2.application.dto;

public record CommentDto(
        long id,
        String value,
        String createdAt,
        String userName,
        int likes
) {
}
