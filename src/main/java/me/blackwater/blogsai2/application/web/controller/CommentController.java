package me.blackwater.blogsai2.application.web.controller;

import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateCommentRequest;
import me.blackwater.blogsai2.application.web.request.PageRequestWithObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface CommentController {

    ResponseEntity<HttpResponse> comment(CreateCommentRequest request);

    ResponseEntity<HttpResponse> commentsByArticleId(PageRequestWithObjectId request);

    ResponseEntity<HttpResponse> comment(long id);

    ResponseEntity<HttpResponse> likeComment(long id);
}
