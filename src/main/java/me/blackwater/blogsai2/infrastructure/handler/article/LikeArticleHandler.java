package me.blackwater.blogsai2.infrastructure.handler.article;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.exception.ArticleNotFoundException;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;

@Handler(handlerType = HandlerType.UPDATE, name = "Article update like")
@RequiredArgsConstructor
public class LikeArticleHandler implements UpdateHandler<Article, Long> {

    private final ArticleRepository articleRepository;

    @Override
    public Article execute(Long id) {
        final Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Article does not exist"));

        article.addLike();

        return articleRepository.save(article);
    }
}
