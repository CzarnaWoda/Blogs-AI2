package me.blackwater.blogsai2.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "User data transfer object")
public record UserDto(

        @Schema(
                description = "Username",
                example = "johndoe"
        )
        String userName,

        @Schema(
                description = "Phone number with country code",
                example = "+48123456789"
        )
        String phone,

        @Schema(
                description = "Email address",
                example = "user@example.com"
        )
        String email,

        @Schema(
                description = "Set of user roles",
                example = "[\"USER\", \"ADMIN\"]"
        )
        Set<String> userRoles,

        String createdAt
) {}