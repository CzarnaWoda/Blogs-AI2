package me.blackwater.blogsai2.infrastructure.handler.article;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.UpdateArticleRequest;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;

@RequiredArgsConstructor
@Handler(name = "Update article by id", handlerType = HandlerType.UPDATE)
public class UpdateArticleByIdHandler implements UpdateHandler<Article, UpdateArticleRequest> {
    private final GetArticleByIdHandler getArticleByIdHandler;
    private final ArticleRepository articleRepository;
    @Override
    public Article execute(UpdateArticleRequest dto) {
        final Article article = getArticleByIdHandler.execute(dto.id());

        article.update(dto.title(),dto.content());

        return articleRepository.save(article);
    }
}
