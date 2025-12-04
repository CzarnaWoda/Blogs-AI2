package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for adding or removing roles from a user")
public record UserRoleRequest(

        @NotBlank(message = "Username cannot be blank")
        @Schema(
                description = "Username of the user to modify roles for",
                example = "johndoe",
                required = true
        )
        String username,

        @NotBlank(message = "Role cannot be blank")
        @Schema(
                description = "Role name to add or remove (e.g., ADMIN, USER, MODERATOR)",
                example = "ADMIN",
                required = true
        )
        String role
) {
}