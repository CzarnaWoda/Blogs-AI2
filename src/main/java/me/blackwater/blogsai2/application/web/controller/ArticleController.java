package me.blackwater.blogsai2.application.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateArticleRequest;
import me.blackwater.blogsai2.application.web.request.UpdateArticleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(
        name = "Article Management",
        description = "API endpoints for managing blog articles - CRUD operations, filtering and pagination"
)
public interface ArticleController {

    @Operation(
            summary = "Create new article",
            description = "Creates a new blog article with the provided title, content, author and section. Returns the created article with generated ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Article create request",
                                              "message": "Article created",
                                              "data": {
                                                "article": {
                                                  "id": 1,
                                                  "title": "Introduction to Spring Boot",
                                                  "content": "Spring Boot makes it easy to create...",
                                                  "views": 0,
                                                  "likes": 0
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data - validation error or author/section not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Validation Error",
                                            value = """
                                                    {
                                                      "timeStamp": "2025-12-03T10:30:00",
                                                      "httpStatus": "BAD_REQUEST",
                                                      "statusCode": 400,
                                                      "reason": "Validation failed",
                                                      "message": "Title cannot be blank"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Author Not Found",
                                            value = """
                                                    {
                                                      "timeStamp": "2025-12-03T10:30:00",
                                                      "httpStatus": "BAD_REQUEST",
                                                      "statusCode": 400,
                                                      "reason": "Author not found",
                                                      "message": "Author with id 999 not found"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Section Not Found",
                                            value = """
                                                    {
                                                      "timeStamp": "2025-12-03T10:30:00",
                                                      "httpStatus": "BAD_REQUEST",
                                                      "statusCode": 400,
                                                      "reason": "Section not found",
                                                      "message": "Section with id 999 not found"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Article creation data including title, content, author and section IDs",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateArticleRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "title": "Introduction to Spring Boot",
                                              "authorId": 1,
                                              "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications...",
                                              "sectionId": 2
                                            }
                                            """
                            )
                    )
            )
            @Valid CreateArticleRequest request,

            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Get article by title",
            description = "Retrieves a single article by its exact title. Title matching is case-sensitive.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article found and returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Article by title request",
                                              "message": "Article by title",
                                              "data": {
                                                "article": {
                                                  "id": 1,
                                                  "title": "Introduction to Spring Boot",
                                                  "content": "Spring Boot makes it easy...",
                                                  "views": 150,
                                                  "likes": 42
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Article not found with the given title",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Article has not been found",
                                              "message": "Article with title 'Unknown Title' not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> article(
            @Parameter(
                    description = "Exact title of the article to retrieve",
                    required = true,
                    example = "Introduction to Spring Boot"
            )
            String title
    );

    @Operation(
            summary = "Get article by ID",
            description = "Retrieves a single article by its unique identifier",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article found and returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Article by id request",
                                              "message": "Article by id",
                                              "data": {
                                                "article": {
                                                  "id": 1,
                                                  "title": "Introduction to Spring Boot",
                                                  "content": "Spring Boot makes it easy...",
                                                  "views": 150,
                                                  "likes": 42
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Article not found with the given ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Article has not been found",
                                              "message": "Article with id 999 not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> article(
            @Parameter(
                    description = "Unique identifier of the article",
                    required = true,
                    example = "1"
            )
            long id
    );

    @Operation(
            summary = "Get articles by author",
            description = "Retrieves all articles written by a specific author. Returns an empty list if no articles found.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles found (can be empty list)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Article by author id request",
                                              "message": "Article by author id",
                                              "data": {
                                                "articles": [
                                                  {
                                                    "id": 1,
                                                    "title": "First Article",
                                                    "content": "Content here...",
                                                    "views": 100,
                                                    "likes": 20
                                                  },
                                                  {
                                                    "id": 2,
                                                    "title": "Second Article",
                                                    "content": "More content...",
                                                    "views": 200,
                                                    "likes": 35
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Author not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Author not found",
                                              "message": "Author with id 999 not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> articleByAuthorId(
            @Parameter(
                    description = "Unique identifier of the author",
                    required = true,
                    example = "1"
            )
            long authorId
    );

    @Operation(
            summary = "Get articles by section",
            description = "Retrieves all articles belonging to a specific section/category. Returns an empty list if no articles found.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles found (can be empty list)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Article by section id request",
                                              "message": "Article by section id",
                                              "data": {
                                                "articles": [
                                                  {
                                                    "id": 3,
                                                    "title": "Tech Article",
                                                    "content": "Technology content...",
                                                    "views": 300,
                                                    "likes": 50
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Section not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Section not found",
                                              "message": "Section with id 999 not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> articleBySectionId(
            @Parameter(
                    description = "Unique identifier of the section",
                    required = true,
                    example = "2"
            )
            long sectionId
    );

    @Operation(
            summary = "Get paginated articles",
            description = "Retrieves a paginated list of all articles sorted by creation date in ascending order. Supports custom page size and page number.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully with pagination metadata",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Articles request",
                                              "message": "Articles data",
                                              "data": {
                                                "totalElements": 25,
                                                "totalPages": 5,
                                                "articles": [
                                                  {
                                                    "id": 1,
                                                    "title": "Article One",
                                                    "content": "Content...",
                                                    "views": 50,
                                                    "likes": 10
                                                  },
                                                  {
                                                    "id": 2,
                                                    "title": "Article Two",
                                                    "content": "More content...",
                                                    "views": 75,
                                                    "likes": 15
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> articles(
            @Parameter(
                    description = "Page number (0-indexed). First page is 0.",
                    example = "0",
                    schema = @Schema(type = "integer", defaultValue = "0", minimum = "0")
            )
            int page,

            @Parameter(
                    description = "Number of articles per page",
                    example = "5",
                    schema = @Schema(type = "integer", defaultValue = "5", minimum = "1", maximum = "100")
            )
            int size
    );

    ResponseEntity<HttpResponse> countUserArticles(String authorName);

    ResponseEntity<HttpResponse> update(UpdateArticleRequest request);
}