package me.blackwater.blogsai2.application.web.request;

public record PageRequestWithObjectId(
        int page,
        int size,
        int objectId
) {
}
