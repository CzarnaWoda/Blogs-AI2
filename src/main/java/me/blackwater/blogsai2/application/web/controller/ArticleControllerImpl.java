package me.blackwater.blogsai2.application.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.mapper.ArticleDtoMapper;
import me.blackwater.blogsai2.application.web.request.CreateArticleRequest;
import me.blackwater.blogsai2.application.web.request.PageSortOrderRequest;
import me.blackwater.blogsai2.domain.model.Article;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.infrastructure.handler.article.*;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByEmailHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByUserNameHandler;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
class ArticleControllerImpl implements ArticleController{

    private final CreateArticleHandler createArticleHandler;
    private final GetArticleByIdHandler getArticleByIdHandler;
    private final GetArticleByTitleHandler getArticleByTitleHandler;
    private final GetArticleByAuthorIdHandler getArticleByAuthorIdHandler;
    private final GetArticleBySectionIdHandler getArticleBySectionIdHandler;
    private final GetArticlePageSortedOrderedHandler getArticlePageSortedOrderedHandler;
    private final GetUserByEmailHandler getUserByEmailHandler;
    private final ArticleDtoMapper articleDtoMapper;

    @Override
    @PostMapping
    public ResponseEntity<HttpResponse> create(@RequestBody @Valid CreateArticleRequest request, Authentication authentication) {
        final Article article = createArticleHandler.execute(request);


        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .statusCode(OK.value())
                        .httpStatus(OK)
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .message("Article created")
                        .reason("Article create request")
                        .data(Map.of("article", articleDtoMapper.toDto(article)))
                .build());
    }

    @Override
    @GetMapping("/title/{title}")
    public ResponseEntity<HttpResponse> article(@PathVariable String title) {
        final Article article = getArticleByTitleHandler.execute(title);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Article by title")
                .reason("Article by title request")
                .data(Map.of("article", articleDtoMapper.toDto(article)))
                .build());
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<HttpResponse> article(@PathVariable long id) {
        final Article article = getArticleByIdHandler.execute(id);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Article by id")
                .reason("Article by id request")
                .data(Map.of("article", articleDtoMapper.toDto(article)))
                .build());
    }

    @Override
    @GetMapping("/author/{authorId}")
    public ResponseEntity<HttpResponse> articleByAuthorId(@PathVariable long authorId) {
        final List<Article> articles = getArticleByAuthorIdHandler.execute(authorId);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Article by author id")
                .reason("Article by author id request")
                .data(Map.of("articles", articles.stream().map(articleDtoMapper::toDto).toList()))
                .build());
    }

    @Override
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<HttpResponse> articleBySectionId(@PathVariable long sectionId) {
        final List<Article> articles = getArticleBySectionIdHandler.execute(sectionId);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Article by section id")
                .reason("Article by section id request")
                .data(Map.of("articles", articles.stream().map(articleDtoMapper::toDto).toList()))
                .build());
    }

    @Override
    @GetMapping("/articles")
    public ResponseEntity<HttpResponse> articles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final Page<Article> articles = getArticlePageSortedOrderedHandler.execute(new PageSortOrderRequest(page,size,"ASC", "createdAt"));

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .statusCode(OK.value())
                .httpStatus(OK)
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .message("Articles data")
                .reason("Articles request")
                .data(Map.of("articles", articles.getContent().stream().map(articleDtoMapper::toDto).toList(), "totalElements", articles.getTotalElements(), "totalPages", articles.getTotalPages()))
                .build());
    }
}
