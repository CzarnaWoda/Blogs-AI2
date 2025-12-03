package me.blackwater.blogsai2.application.web.controller;

import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.UpdateUserRequest;
import me.blackwater.blogsai2.application.web.request.UserRoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserController {

    ResponseEntity<HttpResponse> addRole(UserRoleRequest addUserRoleRequest);

    ResponseEntity<HttpResponse> removeRole(UserRoleRequest removeUserRoleRequest);

    ResponseEntity<HttpResponse> update(UpdateUserRequest updateUserRequest, Authentication authentication);

    ResponseEntity<HttpResponse> users(int page, int size);

    ResponseEntity<HttpResponse> user(int id);

    ResponseEntity<HttpResponse> user(String userName);
}
