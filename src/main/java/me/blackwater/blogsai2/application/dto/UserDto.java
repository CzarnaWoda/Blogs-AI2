package me.blackwater.blogsai2.application.dto;

import me.blackwater.blogsai2.domain.model.Email;
import me.blackwater.blogsai2.domain.model.Phone;
import me.blackwater.blogsai2.domain.model.UserRole;

import java.util.Set;

public record UserDto(
        String userName,
        String phone,
        String email,
        Set<String> userRoles
) {
}
