package me.blackwater.blogsai2.infrastructure.handler.article;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.ArticleRepository;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByUserNameHandler;

@Handler(name = "get article count by author id", handlerType = HandlerType.GET)
@RequiredArgsConstructor
public class GetArticleCountByAuthorIdHandler implements GetHandler<Integer,String> {

    private final ArticleRepository articleRepository;
    private final GetUserByUserNameHandler getUserByUserNameHandler;
    @Override
    public Integer execute(String authorName) {
        final User user = getUserByUserNameHandler.execute(authorName);

        return articleRepository.countByAuthorId(user.getId());
    }
}
