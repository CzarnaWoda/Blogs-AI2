package me.blackwater.blogsai2.application.web.controller;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.mapper.CommentDtoMapper;
import me.blackwater.blogsai2.application.web.request.CreateCommentRequest;
import me.blackwater.blogsai2.application.web.request.PageRequestWithObjectId;
import me.blackwater.blogsai2.domain.model.Comment;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.infrastructure.handler.comment.*;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByEmailHandler;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController{

    private final CreateCommentByArticleIdHandler createCommentByArticleIdHandler;
    private final CommentDtoMapper commentDtoMapper;
    private final GetUserByEmailHandler getUserByEmailHandler;
    private final GetCommentByArticleIdHandler getCommentByArticleIdHandler;
    private final GetCommentByIdHandler getCommentByIdHandler;
    private final LikeCommentHandler likeCommentHandler;
    private final DisableCommentByIdHandler disableCommentByIdHandler;

    @Override
    @PostMapping
    public ResponseEntity<HttpResponse> comment(@RequestBody CreateCommentRequest request, Authentication authentication) {
        final User user = getUserByEmailHandler.execute(authentication.getName());

        final Comment comment = createCommentByArticleIdHandler.execute(new CreateCommentRequest(request.articleId(),user.getUserName(),request.value()));

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("Comment created")
                        .reason("Comment create request sent")
                        .data(Map.of("comment", commentDtoMapper.toDto(comment)))
                .build());
    }

    @Override
    @GetMapping("/comments")
    public ResponseEntity<HttpResponse> commentsByArticleId(@RequestBody PageRequestWithObjectId request) {
        final Page<Comment> comments = getCommentByArticleIdHandler.execute(request);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("Comments found")
                        .reason("Comments page request sent")
                        .data(Map.of("comments", comments.getContent().stream().map(commentDtoMapper::toDto).toList(), "totalElements", comments.getTotalElements(), "totalPages", comments.getTotalPages()))
                        .build());
    }

    @Override
    @GetMapping
    public ResponseEntity<HttpResponse> comment(long id) {
        final Comment comment = getCommentByIdHandler.execute(id);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("Comment found")
                        .reason("Comment by id request sent")
                        .data(Map.of("comment", commentDtoMapper.toDto(comment)))
                        .build());
    }

    @Override
    @PatchMapping("/like")
    public ResponseEntity<HttpResponse> likeComment(long id) {
        final Comment comment = likeCommentHandler.execute(id);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("Comment liked")
                        .reason("Comment like request sent")
                        .data(Map.of("comment", commentDtoMapper.toDto(comment)))
                        .build());
    }

    @Override
    @PatchMapping("/disable/{id}")
    public ResponseEntity<HttpResponse> disableById(@PathVariable long id) {
        final Comment comment = disableCommentByIdHandler.execute(id);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("Comment disabled")
                        .reason("Comment disable request sent")
                        .data(Map.of("comment", commentDtoMapper.toDto(comment)))
                        .build());
    }
}
