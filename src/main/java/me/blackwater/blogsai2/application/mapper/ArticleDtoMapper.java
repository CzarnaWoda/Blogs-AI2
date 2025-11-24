package me.blackwater.blogsai2.application.mapper;

import me.blackwater.blogsai2.application.dto.ArticleDto;
import me.blackwater.blogsai2.domain.model.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleDtoMapper {

    public ArticleDto toDto(Article article) {
        return new ArticleDto(article.getId(),article.getTitle(),article.getContent(),article.getViews(),article.getLikes());
    }
}
