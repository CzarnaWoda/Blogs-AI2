package me.blackwater.blogsai2.domain.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank @Size(min = 6, max = 64) String username,
        @NotBlank
        String password,
        @NotBlank
        String countryCode,
        @NotBlank
        String phone,
        @NotBlank @Email
        String email
) {
}
