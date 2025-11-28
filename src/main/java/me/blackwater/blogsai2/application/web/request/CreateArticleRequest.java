package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for creating a new article")
public record CreateArticleRequest(
        @Schema(
                description = "Title of the article",
                example = "Introduction to Spring Boot",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1,
                maxLength = 255
        )
        @NotBlank(message = "Title cannot be blank")
        @NotNull(message = "Title cannot be null")
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
        String title,

        @Schema(
                description = "Unique identifier of the article author",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minimum = "1"
        )
        @NotNull(message = "Author ID cannot be null")
        @Positive(message = "Author ID must be positive")
        long authorId,

        @Schema(
                description = "Full content of the article",
                example = "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications...",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minLength = 1
        )
        @NotBlank(message = "Content cannot be blank")
        @NotNull(message = "Content cannot be null")
        String content,

        @Schema(
                description = "Unique identifier of the article section/category",
                example = "2",
                requiredMode = Schema.RequiredMode.REQUIRED,
                minimum = "1"
        )
        @NotNull(message = "Section ID cannot be null")
        @Positive(message = "Section ID must be positive")
        long sectionId
) {
}