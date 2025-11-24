package me.blackwater.blogsai2.application.web.request;

public record CreateCommentRequest(
        long articleId,
        String authorUserName,
        String value
) {
}
