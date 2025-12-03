package me.blackwater.blogsai2.application.web.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.mapper.UserDtoMapper;
import me.blackwater.blogsai2.application.web.request.CreateUserRequest;
import me.blackwater.blogsai2.application.web.request.LoginRequest;
import me.blackwater.blogsai2.application.web.request.UpdateUserPasswordRequest;
import me.blackwater.blogsai2.domain.exception.IllegalAccountAccessException;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.infrastructure.handler.user.CreateUserHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByEmailHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.UpdateUserPasswordByIdHandler;
import me.blackwater.blogsai2.infrastructure.security.provider.AccountAuthenticationProvider;
import me.blackwater.blogsai2.infrastructure.security.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthControllerImpl implements AuthController{

    private final GetUserByEmailHandler getUserByEmailHandler;
    private final CreateUserHandler createUserHandler;
    private final TokenService tokenService;
    private final AccountAuthenticationProvider authenticationProvider;
    private final UserDtoMapper userDtoMapper;
    private final UpdateUserPasswordByIdHandler updateUserPasswordByIdHandler;

    @Override
    @PostMapping("/login")
    public ResponseEntity<HttpResponse> login(@RequestBody @Valid LoginRequest request) {
        try {
            final Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            final User user = getUserByEmailHandler.execute(request.email());

            return ResponseEntity.status(OK)
                    .body(HttpResponse.builder()
                            .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                            .statusCode(OK.value())
                            .httpStatus(OK)
                            .message("Successfully logged in")
                            .reason("Login request sent by User")
                            .data(Map.of("token", tokenService.generateToken(authentication), "user", userDtoMapper.toDto(user)))
                            .build());
        }catch (AuthenticationException e){
            throw new IllegalAccountAccessException("Illegal access to account!");
        }
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody @Valid CreateUserRequest request) {
        final User user = createUserHandler.execute(request);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .statusCode(OK.value())
                .httpStatus(OK)
                .message("Successfully registered")
                .reason("Register request sent by User")
                .data(Map.of("user", userDtoMapper.toDto(user)))
                .build());
    }

    @Override
    @GetMapping("/authorities")
    public ResponseEntity<HttpResponse> authorities(Authentication authentication) {
        final User user = getUserByEmailHandler.execute(authentication.getName());

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .statusCode(OK.value())
                        .httpStatus(OK)
                        .message("User authorities")
                        .reason("Authorities request sent by User")
                        .data(Map.of("authorities", user.getRoles()))
                        .build());

    }

    @Override
    @PostMapping("/changePassword")
    public ResponseEntity<HttpResponse> changePassword(@RequestBody @Valid UpdateUserPasswordRequest request, Authentication authentication) {
        final User user = getUserByEmailHandler.execute(authentication.getName());

        updateUserPasswordByIdHandler.execute(new UpdateUserPasswordRequest(user.getId(),request.oldPassword(),request.newPassword()));

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .statusCode(OK.value())
                        .httpStatus(OK)
                        .message("Successfully changed password")
                        .reason("Change password request sent by User")
                        .build());

    }

    @Override
    @GetMapping("/me")
    public ResponseEntity<HttpResponse> me(Authentication authentication) {
        final User user = getUserByEmailHandler.execute(authentication.getName());


        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .statusCode(OK.value())
                        .httpStatus(OK)
                        .message("User information")
                        .reason("User information sent by User")
                        .data(Map.of("user", userDtoMapper.toDto(user)))
                        .build());
    }
}
