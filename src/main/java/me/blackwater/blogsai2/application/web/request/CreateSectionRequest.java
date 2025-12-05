package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request object for creating a new section/category")
public record CreateSectionRequest(
        @Schema(
                description = "Username of the section creator",
                example = "admin",
                required = true,
                maxLength = 50
        )
        @NotNull(message = "Creator cannot be null")
        @NotBlank(message = "Creator cannot be blank")
        String creator,

        @Schema(
                description = "Title of the section",
                example = "Technology",
                required = true,
                minLength = 1,
                maxLength = 100
        )
        @NotNull(message = "Title cannot be null")
        @NotBlank(message = "Title cannot be blank")
        String title,

        @Schema(
                description = "Description of the section explaining its purpose and content",
                example = "Articles about technology, programming, and software development",
                required = true,
                minLength = 1,
                maxLength = 500
        )
        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be blank")
        String description,

        @Schema(
                description = "Type/category of the section",
                example = "TECH",
                required = true,
                maxLength = 50
        )
        @NotNull(message = "Type cannot be null")
        @NotBlank(message = "Type cannot be blank")
        String type
) {
}