package me.blackwater.blogsai2.application.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @Email @NotBlank
        String email,
        @NotBlank
        String password,
        @NotNull
        boolean remember
){
}
