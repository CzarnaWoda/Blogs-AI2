package me.blackwater.blogsai2.application.web.controller;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateCommentRequest;
import me.blackwater.blogsai2.application.web.request.PageRequestWithObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController{

    @Override
    @PostMapping
    public ResponseEntity<HttpResponse> comment(CreateCommentRequest request) {
        return null;
    }

    @Override
    @GetMapping("/comments")
    public ResponseEntity<HttpResponse> commentsByArticleId(PageRequestWithObjectId request) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<HttpResponse> comment(long id) {
        return null;
    }

    @Override
    @PatchMapping("/like")
    public ResponseEntity<HttpResponse> likeComment(long id) {
        return null;
    }
}
