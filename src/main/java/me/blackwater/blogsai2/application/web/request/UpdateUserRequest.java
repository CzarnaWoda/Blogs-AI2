package me.blackwater.blogsai2.application.web.request;

public record UpdateUserRequest(
        Long id,
        String userName,
        String countryCode,
        String phoneNumber
) {
}
