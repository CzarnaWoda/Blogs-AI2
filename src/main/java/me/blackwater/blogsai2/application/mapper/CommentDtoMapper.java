package me.blackwater.blogsai2.application.mapper;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.dto.CommentDto;
import me.blackwater.blogsai2.domain.model.Comment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDtoMapper {

    public CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(),comment.getValue(), TimeUtil.getTimeInStandardFormat(comment.getCreatedAt()), comment.getAuthor().getUserName(),comment.getLikes());
    }
}
