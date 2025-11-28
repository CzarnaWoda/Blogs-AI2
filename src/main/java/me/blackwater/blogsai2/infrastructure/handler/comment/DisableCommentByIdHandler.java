package me.blackwater.blogsai2.infrastructure.handler.comment;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.repository.CommentRepository;

@Handler(handlerType = HandlerType.UPDATE, name = "disable comment by id")
@RequiredArgsConstructor
public class DisableCommentByIdHandler implements UpdateHandler<Comment,Long> {

    private final GetCommentByIdHandler getCommentByIdHandler;
    private final CommentRepository commentRepository;
    @Override
    public Comment execute(Long dto) {
        final Comment comment = getCommentByIdHandler.execute(dto);

        if(comment.isDisabled()){
            return comment;
        }

        comment.disable();

        return commentRepository.save(comment);
    }
}
