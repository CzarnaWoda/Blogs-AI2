package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Request object for creating a new comment on an article")
public record CreateCommentRequest(
        @Schema(
                description = "Unique identifier of the article to comment on",
                example = "5",
                required = true
        )
        @NotNull(message = "Article ID cannot be null")
        @Positive(message = "Article ID must be positive")
        long articleId,

        @Schema(
                description = "Username of the comment author",
                example = "johndoe",
                required = true,
                maxLength = 50
        )
        String authorUserName,

        @Schema(
                description = "Content of the comment",
                example = "Great article! Very informative and well-written.",
                required = true,
                minLength = 1,
                maxLength = 1000
        )
        @NotNull(message = "Comment value cannot be null")
        @NotBlank(message = "Comment value cannot be blank")
        String value
) {
}