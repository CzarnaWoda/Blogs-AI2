package me.blackwater.blogsai2.application.web.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PageRequest(
        @NotNull @Min(value = 0)
        int page,
        @Min(value = 1) @Max(value = 255)
        @NotNull
        int size
) {
}
