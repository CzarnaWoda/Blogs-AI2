package me.blackwater.blogsai2.application.web.request;

public record CreateArticleRequest(
        String title,
        long authorId,
        String content,
        long sectionId
) {
}
