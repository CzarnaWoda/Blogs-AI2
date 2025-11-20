package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Login request with user credentials")
public record LoginRequest(

        @Schema(
                description = "User email address",
                example = "user@example.com"
        )
        @Email
        @NotBlank
        String email,

        @Schema(
                description = "User password",
                example = "SecurePassword123!"
        )
        @NotBlank
        String password,

        @Schema(
                description = "Remember me flag for extended session",
                example = "true"
        )
        @NotNull
        boolean remember
) {}