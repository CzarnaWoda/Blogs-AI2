package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.ToString;

@Schema(description = "User registration request data")
public record CreateUserRequest(

        @Schema(
                description = "Username (4-64 characters)",
                example = "johndoe",
                minLength = 4,
                maxLength = 64
        )
        @NotBlank
        @Size(min = 4, max = 64)
        String username,

        @Schema(
                description = "User password",
                example = "SecurePassword123!"
        )
        @NotBlank
        String password,

        @Schema(
                description = "Country calling code (e.g., +48 for Poland, +1 for USA)",
                example = "+48"
        )
        @NotBlank
        String countryCode,

        @Schema(
                description = "Phone number without country code",
                example = "123456789"
        )
        @NotBlank
        String phone,

        @Schema(
                description = "Email address",
                example = "user@example.com"
        )
        @NotBlank
        @Email
        String email
) {}