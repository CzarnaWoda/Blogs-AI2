package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Pagination request with sorting and ordering options")
public record PageSortOrderRequest(
        @Schema(
                description = "Page number (0-indexed). First page is 0",
                example = "0",
                defaultValue = "0",
                minimum = "0"
        )
        @Min(value = 0, message = "Page number must be 0 or greater")
        int page,

        @Schema(
                description = "Number of items per page",
                example = "10",
                defaultValue = "10",
                minimum = "1",
                maximum = "255"
        )
        @Min(value = 1, message = "Page size must be at least 1")
        @Max(value = 255, message = "Page size cannot exceed 255")
        int size,

        @Schema(
                description = "Sort direction: ASC for ascending, DESC for descending",
                example = "ASC",
                allowableValues = {"ASC", "DESC"},
                defaultValue = "ASC"
        )
        @Pattern(regexp = "^(ASC|DESC)$", message = "Sort direction must be either ASC or DESC")
        String sortDirection,

        @Schema(
                description = "Field name to sort by (e.g., 'createdAt', 'title', 'id')",
                example = "createdAt",
                defaultValue = "createdAt"
        )
        @NotNull(message = "Order by field cannot be null")
        @NotBlank(message = "Order by field cannot be blank")
        String orderBy
) {
}