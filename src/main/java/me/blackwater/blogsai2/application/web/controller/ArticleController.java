package me.blackwater.blogsai2.application.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateArticleRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Article Management", description = "API endpoints for managing blog articles - CRUD operations, filtering and pagination")
public interface ArticleController {

    @Operation(
            summary = "Create new article",
            description = "Creates a new blog article with the provided title, content, author and section. Returns the created article with generated ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                            {
                                              "statusCode": 200,
                                              "httpStatus": "OK",
                                              "timeStamp": "2024-11-24 10:30:45",
                                              "message": "Article created",
                                              "reason": "Article create request",
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
                    description = "Invalid input data - validation error or business rule violation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ErrorResponse"),
                            examples = @ExampleObject(
                                    name = "Validation Error",
                                    value = """
                                            {
                                              "statusCode": 400,
                                              "httpStatus": "BAD_REQUEST",
                                              "timeStamp": "2024-11-24 10:30:45",
                                              "message": "Validation failed",
                                              "reason": "Title cannot be blank"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Author or Section not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ErrorResponse"),
                            examples = @ExampleObject(
                                    name = "Not Found Error",
                                    value = """
                                            {
                                              "statusCode": 400,
                                              "httpStatus": "BAD_REQUEST",
                                              "timeStamp": "2024-11-24 10:30:45",
                                              "message": "Article has not been found",
                                              "reason": "Author with id 999 not found"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<HttpResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Article creation data including title, content, author and section IDs",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateArticleRequest.class),
                            examples = @ExampleObject(
                                    name = "Create Article Example",
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
            CreateArticleRequest request
    );

    @Operation(
            summary = "Get article by title",
            description = "Retrieves a single article by its exact title. Title matching is case-sensitive."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article found and returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                            {
                                              "statusCode": 200,
                                              "httpStatus": "OK",
                                              "timeStamp": "2024-11-24 10:30:45",
                                              "message": "Article by title",
                                              "reason": "Article by title request",
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
                            schema = @Schema(ref = "#/components/schemas/ErrorResponse")
                    )
            )
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
            description = "Retrieves a single article by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Article found and returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Article not found with the given ID",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ErrorResponse")
                    )
            )
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
            description = "Retrieves all articles written by a specific author. Returns an empty list if no articles found."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles found (can be empty list)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    name = "Success Response",
                                    value = """
                                            {
                                              "statusCode": 200,
                                              "httpStatus": "OK",
                                              "timeStamp": "2024-11-24 10:30:45",
                                              "message": "Article by author id",
                                              "reason": "Article by author id request",
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
                            schema = @Schema(ref = "#/components/schemas/ErrorResponse")
                    )
            )
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
            description = "Retrieves all articles belonging to a specific section/category. Returns an empty list if no articles found."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles found (can be empty list)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Section not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(ref = "#/components/schemas/ErrorResponse")
                    )
            )
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
            description = "Retrieves a paginated list of all articles sorted by creation date in ascending order. Supports custom page size and page number."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Articles retrieved successfully with pagination metadata",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    name = "Paginated Response",
                                    value = """
                                            {
                                              "statusCode": 200,
                                              "httpStatus": "OK",
                                              "timeStamp": "2024-11-24 10:30:45",
                                              "message": "Articles data",
                                              "reason": "Articles request",
                                              "data": {
                                                "articles": [
                                                  {
                                                    "id": 1,
                                                    "title": "Article One",
                                                    "content": "Content...",
                                                    "views": 50,
                                                    "likes": 10
                                                  }
                                                ],
                                                "totalElements": 25,
                                                "totalPages": 5
                                              }
                                            }
                                            """
                            )
                    )
            )
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
}