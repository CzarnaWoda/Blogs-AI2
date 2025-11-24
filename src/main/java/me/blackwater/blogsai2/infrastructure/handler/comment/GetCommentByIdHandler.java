package me.blackwater.blogsai2.infrastructure.handler.comment;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.exception.CommentNotFoundException;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.repository.CommentRepository;

@Handler(handlerType = HandlerType.GET, name = "comment by id")
@RequiredArgsConstructor
public class GetCommentByIdHandler implements GetHandler<Comment,Long> {
    private final CommentRepository commentRepository;
    @Override
    public Comment execute(Long aLong) {
        return commentRepository.findById(aLong).orElseThrow(() -> new CommentNotFoundException("Comment doesnt exist!"));
    }
}
