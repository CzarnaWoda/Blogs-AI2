package me.blackwater.blogsai2.application.web.controller;

import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateUserRequest;
import me.blackwater.blogsai2.application.web.request.LoginRequest;
import me.blackwater.blogsai2.application.web.request.UpdateUserPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthController {

    ResponseEntity<HttpResponse> login(LoginRequest request);

    ResponseEntity<HttpResponse> register(CreateUserRequest request);

    ResponseEntity<HttpResponse> authorities(Authentication authentication);

    ResponseEntity<HttpResponse> changePassword(UpdateUserPasswordRequest request, Authentication authentication);
}
