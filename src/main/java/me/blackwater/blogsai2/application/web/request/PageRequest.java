package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Standard pagination request object")
public record PageRequest(
        @Schema(
                description = "Page number (0-indexed). First page is 0",
                example = "0",
                required = true,
                defaultValue = "0",
                minimum = "0"
        )
        @NotNull(message = "Page number cannot be null")
        @Min(value = 0, message = "Page number must be 0 or greater")
        int page,

        @Schema(
                description = "Number of items per page",
                example = "10",
                required = true,
                defaultValue = "10",
                minimum = "1",
                maximum = "255"
        )
        @Min(value = 1, message = "Page size must be at least 1")
        @Max(value = 255, message = "Page size cannot exceed 255")
        @NotNull(message = "Page size cannot be null")
        int size
) {
}