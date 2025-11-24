package me.blackwater.blogsai2.infrastructure.handler.article;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.CreateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.CreateArticleRequest;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.model.Section;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;
import me.blackwater.blogsai2.infrastructure.handler.section.GetSectionByIdHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByIdHandler;

@Handler(handlerType = HandlerType.CREATE, name = "create article")
@RequiredArgsConstructor
public class CreateArticleHandler implements CreateHandler<Article, CreateArticleRequest> {
    private final ArticleRepository articleRepository;
    private final GetUserByIdHandler getUserByIdHandler;
    private final GetSectionByIdHandler getSectionByIdHandler;

    @Override
    public Article execute(CreateArticleRequest dto) {
        final User user = getUserByIdHandler.execute(dto.authorId());
        final Section section = getSectionByIdHandler.execute(dto.sectionId());

        return articleRepository.save(new Article(dto.title(),user,dto.content(),section));
    }
}
