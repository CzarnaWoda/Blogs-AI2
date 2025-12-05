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
import me.blackwater.blogsai2.application.web.request.CreateCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(
        name = "Comment Management",
        description = "API endpoints for managing article comments - create, retrieve, like, and moderate comments"
)
public interface CommentController {

    @Operation(
            summary = "Create new comment",
            description = "Creates a new comment on an article. Requires USER role. Returns the created comment with generated ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment successfully created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Comment create request",
                                              "message": "Comment created",
                                              "data": {
                                                "comment": {
                                                  "id": 1,
                                                  "value": "Great article! Very informative.",
                                                  "articleId": 5,
                                                  "authorUserName": "johndoe",
                                                  "likes": 0,
                                                  "createdAt": "2025-12-03T10:30:00"
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data or article not found",
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
                                                      "message": "Comment value cannot be blank"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Article Not Found",
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
                            }
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/ForbiddenResponse")
    })
    ResponseEntity<HttpResponse> comment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Comment creation data including article ID, author username, and comment value",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateCommentRequest.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "articleId": 5,
                                              "authorUserName": "johndoe",
                                              "value": "Great article! Very informative and well-written."
                                            }
                                            """
                            )
                    )
            )
            @Valid CreateCommentRequest request,

            @Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Get paginated comments by article",
            description = "Retrieves a paginated list of all comments for a specific article. Public endpoint - no authentication required. Returns empty list if no comments found."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comments retrieved successfully with pagination metadata",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Comments by article id request",
                                              "message": "Comments by article id",
                                              "data": {
                                                "totalElements": 15,
                                                "totalPages": 3,
                                                "comments": [
                                                  {
                                                    "id": 1,
                                                    "value": "Great article!",
                                                    "authorUserName": "johndoe",
                                                    "likes": 5,
                                                    "createdAt": "2025-12-03T09:00:00"
                                                  },
                                                  {
                                                    "id": 2,
                                                    "value": "Very informative, thanks!",
                                                    "authorUserName": "janesmith",
                                                    "likes": 3,
                                                    "createdAt": "2025-12-03T09:15:00"
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
                    description = "Article not found or invalid pagination parameters",
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
            )
    })
    ResponseEntity<HttpResponse> commentsByArticleId(
            @Parameter(
                    description = "Unique identifier of the article whose comments to retrieve",
                    required = true,
                    example = "5"
            )
            long objectId,

            @Parameter(
                    description = "Page number (zero-based index)",
                    required = true,
                    example = "0",
                    schema = @Schema(minimum = "0", defaultValue = "0")
            )
            int page,

            @Parameter(
                    description = "Number of comments per page",
                    required = true,
                    example = "10",
                    schema = @Schema(minimum = "1", maximum = "100", defaultValue = "10")
            )
            int size
    );

    @Operation(
            summary = "Get comment by ID",
            description = "Retrieves a single comment by its unique identifier. Requires authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment found and returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Comment by id request",
                                              "message": "Comment by id",
                                              "data": {
                                                "comment": {
                                                  "id": 1,
                                                  "value": "Great article! Very informative.",
                                                  "articleId": 5,
                                                  "authorUserName": "johndoe",
                                                  "likes": 5,
                                                  "createdAt": "2025-12-03T09:00:00"
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Comment not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Comment has not been found",
                                              "message": "Comment with id 999 not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> comment(
            @Parameter(
                    description = "Unique identifier of the comment",
                    required = true,
                    example = "1"
            )
            long id
    );

    @Operation(
            summary = "Like a comment",
            description = "Adds a like to the specified comment. Requires authentication.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment liked successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Like comment request",
                                              "message": "Comment liked",
                                              "data": {
                                                "comment": {
                                                  "id": 1,
                                                  "value": "Great article!",
                                                  "likes": 6
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Comment not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Comment has not been found",
                                              "message": "Comment with id 999 not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> likeComment(
            @Parameter(
                    description = "Unique identifier of the comment to like",
                    required = true,
                    example = "1"
            )
            long id
    );

    @Operation(
            summary = "Disable comment (Helper/Moderator only)",
            description = "Disables/removes a comment from public view. Requires HELPER role or higher.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment disabled successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Disable comment request",
                                              "message": "Comment has been disabled",
                                              "data": {
                                                "comment": {
                                                  "id": 1,
                                                  "disabled": true
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Comment not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "BAD_REQUEST",
                                              "statusCode": 400,
                                              "reason": "Comment has not been found",
                                              "message": "Comment with id 999 not found"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/ForbiddenResponse")
    })
    ResponseEntity<HttpResponse> disableById(
            @Parameter(
                    description = "Unique identifier of the comment to disable",
                    required = true,
                    example = "1"
            )
            long id
    );
}