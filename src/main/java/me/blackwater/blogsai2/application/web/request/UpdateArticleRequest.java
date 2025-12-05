package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for updating an existing article")
public record UpdateArticleRequest(
        @Schema(
                description = "Unique identifier of the article to update",
                example = "1",
                required = true
        )
        @NotNull(message = "Article ID cannot be null")
        @Positive(message = "Article ID must be positive")
        long id,

        @Schema(
                description = "Updated title of the article",
                example = "Updated: Introduction to Spring Boot 3.0",
                required = true,
                minLength = 1,
                maxLength = 200
        )
        @NotNull(message = "Title cannot be null")
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
        String title,

        @Schema(
                description = "Updated content of the article",
                example = "Spring Boot 3.0 brings many new features including improved performance...",
                required = true,
                minLength = 1,
                maxLength = 50000
        )
        @NotNull(message = "Content cannot be null")
        @NotBlank(message = "Content cannot be blank")
        @Size(min = 1, max = 50000, message = "Content must be between 1 and 50000 characters")
        String content
) {
}