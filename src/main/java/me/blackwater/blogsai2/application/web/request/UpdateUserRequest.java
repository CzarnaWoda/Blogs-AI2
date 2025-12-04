package me.blackwater.blogsai2.application.web.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request object for updating user profile information")
public record UpdateUserRequest(

        @Schema(
                description = "User ID. If null, the authenticated user's profile will be updated",
                example = "1"
        )
        Long id,

        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username can only contain letters, numbers, underscores and hyphens")
        @Schema(
                description = "Username (3-50 characters, alphanumeric with underscores and hyphens)",
                example = "johndoe",
                minLength = 3,
                maxLength = 50
        )
        String userName,

        @Pattern(regexp = "^\\+\\d{1,4}$", message = "Country code must start with + followed by 1-4 digits")
        @Schema(
                description = "Country calling code (e.g., +48 for Poland, +1 for USA)",
                example = "+48",
                pattern = "^\\+\\d{1,4}$"
        )
        String countryCode,

        @Pattern(regexp = "^\\d{9,15}$", message = "Phone number must contain 9-15 digits")
        @Schema(
                description = "Phone number without country code (9-15 digits)",
                example = "123456789",
                pattern = "^\\d{9,15}$",
                minLength = 9,
                maxLength = 15
        )
        String phoneNumber
) {
}