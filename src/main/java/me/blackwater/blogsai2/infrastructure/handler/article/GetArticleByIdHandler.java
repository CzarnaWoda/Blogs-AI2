package me.blackwater.blogsai2.infrastructure.handler.article;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.exception.ArticleNotFoundException;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;

import java.time.Duration;
import java.time.Instant;

@Handler(name = "article by id", handlerType = HandlerType.GET,transactional = true)
@RequiredArgsConstructor
public class GetArticleByIdHandler implements GetHandler<Article, Long> {

    private final ArticleRepository articleRepository;

    @Override
    public Article execute(Long id) {
        final Article article = articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException("Article with that id does not exist"));

        article.addView();

        if(Duration.between(article.getUpdatedAt(), Instant.now()).compareTo(Duration.ofMinutes(5)) >= 0) {
            article.updateTime();

            articleRepository.save(article);
        }

        return article;

    }
}
