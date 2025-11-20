package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Password update request")
public record UpdateUserPasswordRequest(

        @Schema(
                description = "User ID (automatically filled from authentication)",
                example = "1",
                hidden = true
        )
        long id,

        @Schema(
                description = "Current password for verification",
                example = "OldPassword123!"
        )
        @NotBlank
        @NotNull
        String oldPassword,

        @Schema(
                description = "New password",
                example = "NewPassword456!"
        )
        @NotBlank
        @NotNull
        String newPassword
) {}