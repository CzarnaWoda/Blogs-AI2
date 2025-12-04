package me.blackwater.blogsai2.application.web.request;

public record UpdateArticleRequest(
        long id,
        String title,
        String content
) {
}
