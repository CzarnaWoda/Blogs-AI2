package me.blackwater.blogsai2.application.web.request;

import jakarta.validation.constraints.NotBlank;

public record UserRoleRequest(
        @NotBlank
        String username,
        @NotBlank
        String role
) {
}
