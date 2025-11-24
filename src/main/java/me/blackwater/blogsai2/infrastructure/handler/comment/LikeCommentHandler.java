package me.blackwater.blogsai2.infrastructure.handler.comment;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.repository.CommentRepository;

@Handler(handlerType = HandlerType.UPDATE, name = "Like comment")
@RequiredArgsConstructor
public class LikeCommentHandler implements UpdateHandler<Comment, Long> {

    private final GetCommentByIdHandler getCommentByIdHandler;
    private final CommentRepository commentRepository;
    @Override
    public Comment execute(Long commentId) {
        final Comment comment = getCommentByIdHandler.execute(commentId);

        comment.addLike();

        return commentRepository.save(comment);
    }
}
