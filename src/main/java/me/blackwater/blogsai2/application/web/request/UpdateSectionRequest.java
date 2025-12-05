package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for updating an existing section")
public record UpdateSectionRequest(
        @Schema(
                description = "Unique identifier of the section to update",
                example = "1",
                required = true
        )
        @NotNull(message = "Section ID cannot be null")
        @Positive(message = "Section ID must be positive")
        long id,

        @Schema(
                description = "Updated title of the section",
                example = "Advanced Technology",
                required = true,
                minLength = 1,
                maxLength = 100
        )
        @NotNull(message = "Title cannot be null")
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
        String title,

        @Schema(
                description = "Updated description of the section",
                example = "In-depth articles about advanced technology topics including AI, cloud computing, and more",
                required = true,
                minLength = 1,
                maxLength = 500
        )
        @NotNull(message = "Description cannot be null")
        @NotBlank(message = "Description cannot be blank")
        @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
        String description,

        @Schema(
                description = "Updated type/category of the section",
                example = "ADVANCED_TECH",
                required = true,
                maxLength = 50
        )
        @NotNull(message = "Type cannot be null")
        @NotBlank(message = "Type cannot be blank")
        @Size(min = 1, max = 50, message = "Type must be between 1 and 50 characters")
        String type
) {
}