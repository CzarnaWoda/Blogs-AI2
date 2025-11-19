package me.blackwater.blogsai2.application.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserPasswordRequest(

        @NotBlank @NotNull
        long id,
        @NotBlank @NotNull
        String oldPassword,
        @NotBlank @NotNull
        String newPassword
) {


}
