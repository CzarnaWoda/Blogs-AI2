package me.blackwater.blogsai2.application.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateSectionRequest(
        @NotNull
        long id,
        @NotNull @NotBlank
        String title,
        @NotNull @NotBlank
        String description,
        @NotNull @NotBlank
        String type
) {
}
