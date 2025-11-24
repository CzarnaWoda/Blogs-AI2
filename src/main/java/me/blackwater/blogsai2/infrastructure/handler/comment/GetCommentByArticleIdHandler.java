package me.blackwater.blogsai2.infrastructure.handler.comment;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.PageRequestWithObjectId;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.repository.CommentRepository;
import org.springframework.data.domain.Page;

import java.time.Duration;

@Handler(handlerType = HandlerType.GET, name = "Comment by article")
@RequiredArgsConstructor
public class GetCommentByArticleIdHandler implements GetHandler<Page<Comment>, PageRequestWithObjectId> {

    private final CommentRepository commentRepository;


    @Override
    public Page<Comment> execute(PageRequestWithObjectId request) {
        return commentRepository.findByArticleIdWithAuthor(request.page(),request.size(),request.objectId());
    }
}
