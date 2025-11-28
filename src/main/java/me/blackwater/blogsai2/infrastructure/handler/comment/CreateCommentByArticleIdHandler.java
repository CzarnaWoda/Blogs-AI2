package me.blackwater.blogsai2.infrastructure.handler.comment;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.CreateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.CreateCommentRequest;
import me.blackwater.blogsai2.domain.exception.IllegalActionException;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;
import me.blackwater.blogsai2.infrastructure.handler.article.GetArticleByIdHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByUserNameHandler;

@Handler(handlerType = HandlerType.CREATE, name = "Create comment by articleId")
@RequiredArgsConstructor
public class CreateCommentByArticleIdHandler implements CreateHandler<Comment, CreateCommentRequest> {

    private final ArticleRepository articleRepository;
    private final GetArticleByIdHandler getArticleByIdHandler;
    private final GetUserByUserNameHandler getUserByUserNameHandler;

    @Override
    public Comment execute(CreateCommentRequest dto) {
        final Article article = getArticleByIdHandler.execute(dto.articleId());
        if(article.isBlocked()){
            throw new IllegalActionException("Article is blocked!");
        }
        final User author = getUserByUserNameHandler.execute(dto.authorUserName());

        final Comment comment = article.addComment(new Comment(author,dto.value()));

        articleRepository.save(article);

        return comment;
    }
}
