package me.blackwater.blogsai2.infrastructure.handler.article;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.PageSortOrderRequest;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.Instant;

@Handler(handlerType = HandlerType.GET, name = "article page sorted ordered",transactional = true)
@RequiredArgsConstructor
public class GetArticlePageSortedOrderedHandler implements GetHandler<Page<Article>, PageSortOrderRequest> {

    private final ArticleRepository articleRepository;

    @Override
    public Page<Article> execute(PageSortOrderRequest request) {
        final Page<Article> articles = articleRepository.findPageableSortedOrdered(request.page(),request.size(),request.sortDirection(),request.orderBy());

        articles.getContent().forEach(article -> {
            article.addView();

            if(Duration.between(article.getUpdatedAt(), Instant.now()).compareTo(Duration.ofMinutes(5)) >= 0) {
                article.updateTime();

                articleRepository.save(article);
            }
        });

        return articles;
    }
}
