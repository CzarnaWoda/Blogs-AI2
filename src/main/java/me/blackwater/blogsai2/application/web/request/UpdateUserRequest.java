package me.blackwater.blogsai2.application.web.request;

public record UpdateUserRequest(
        long id,
        String userName,
        String countryCode,
        String phoneNumber
) {
}
