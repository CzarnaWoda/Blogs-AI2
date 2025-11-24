package me.blackwater.blogsai2.infrastructure.handler.article;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Handler(handlerType = HandlerType.GET, name = "article by section id",transactional = true)
@RequiredArgsConstructor
public class GetArticleBySectionIdHandler implements GetHandler<List<Article>, Long> {

    private final ArticleRepository articleRepository;

    @Override
    public List<Article> execute(Long sectionId) {
        final List<Article> articles = articleRepository.findBySectionId(sectionId);

        articles.forEach(article -> {
            article.addView();

            if(Duration.between(article.getUpdatedAt(), Instant.now()).compareTo(Duration.ofMinutes(5)) >= 0) {
                article.updateTime();

                articleRepository.save(article);
            }
        });

        return articles;
    }
}
