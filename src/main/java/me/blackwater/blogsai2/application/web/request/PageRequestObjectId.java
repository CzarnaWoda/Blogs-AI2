package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "Pagination request with an object identifier (e.g., article ID for comments)")
public record PageRequestObjectId(

        @Min(value = 0, message = "Page number cannot be negative")
        @Schema(
                description = "Page number (0-indexed). First page is 0.",
                example = "0",
                defaultValue = "0",
                minimum = "0"
        )
        int page,

        @Min(value = 1, message = "Page size must be at least 1")
        @Max(value = 100, message = "Page size cannot exceed 100")
        @Schema(
                description = "Number of items per page (1-100)",
                example = "5",
                defaultValue = "5",
                minimum = "1",
                maximum = "100"
        )
        int size,

        @NotNull(message = "Object ID cannot be null")
        @Positive(message = "Object ID must be positive")
        @Schema(
                description = "ID of the related object (e.g., article ID for fetching comments)",
                example = "5",
                minimum = "1"
        )
        Long objectId
) {
}