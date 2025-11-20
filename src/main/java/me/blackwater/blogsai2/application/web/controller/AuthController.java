package me.blackwater.blogsai2.application.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.CreateUserRequest;
import me.blackwater.blogsai2.application.web.request.LoginRequest;
import me.blackwater.blogsai2.application.web.request.UpdateUserPasswordRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(
        name = "Authentication",
        description = "Endpoints for user authentication and account management"
)
public interface AuthController {

    @Operation(
            summary = "User login",
            description = "Authenticates a user with email and password. Returns JWT token and user data on success."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    name = "Successful Login",
                                    value = """
                        {
                          "timeStamp": "2025-11-20 10:30:00",
                          "httpStatus": "OK",
                          "statusCode": 200,
                          "reason": "Login request sent by User",
                          "message": "Successfully logged in",
                          "data": {
                            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjM3NDEyMDAwfQ...",
                            "user": {
                              "userName": "johndoe",
                              "phone": "+48123456789",
                              "email": "user@example.com",
                              "userRoles": ["USER"]
                            }
                          }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Illegal account access - invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "timeStamp": "2025-11-20 10:30:00",
                          "httpStatus": "FORBIDDEN",
                          "statusCode": 403,
                          "reason": "Authentication failed",
                          "message": "Illegal access to account!"
                        }
                        """
                            )
                    )
            )
    })
    ResponseEntity<HttpResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Login credentials with email, password and remember flag",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LoginRequest.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "email": "user@example.com",
                          "password": "SecurePassword123!",
                          "remember": true
                        }
                        """
                            )
                    )
            )
            LoginRequest request
    );

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account with the provided information. Username and email must be unique."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Registration successful",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    name = "Successful Registration",
                                    value = """
                    {
                      "timeStamp": "2025-11-20 10:30:00",
                      "httpStatus": "OK",
                      "statusCode": 200,
                      "reason": "Register request sent by User",
                      "message": "Successfully registered",
                      "data": {
                        "user": {
                          "userName": "johndoe",
                          "phone": "+48123456789",
                          "email": "user@example.com",
                          "userRoles": ["USER"]
                        }
                      }
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid input data",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User already exists with this email or username",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class)
                    )
            )
    })
    ResponseEntity<HttpResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data including username, password, phone, email and country calling code",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateUserRequest.class),
                            examples = @ExampleObject(
                                    value = """
                    {
                      "username": "johndoe",
                      "password": "SecurePassword123!",
                      "countryCode": "+48",
                      "phone": "123456789",
                      "email": "user@example.com"
                    }
                    """
                            )
                    )
            )
            CreateUserRequest request
    );

    @Operation(
            summary = "Get user authorities",
            description = "Returns the set of roles assigned to the authenticated user",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Authorities retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "timeStamp": "2025-11-20 10:30:00",
                          "httpStatus": "OK",
                          "statusCode": 200,
                          "reason": "Authorities request sent by User",
                          "message": "User authorities",
                          "data": {
                            "authorities": [
                              {"value": "USER"},
                              {"value": "ADMIN"}
                            ]
                          }
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token missing or invalid",
                    content = @Content(schema = @Schema(implementation = HttpResponse.class))
            )
    })
    ResponseEntity<HttpResponse> authorities(
            @io.swagger.v3.oas.annotations.Parameter(hidden = true)
            Authentication authentication
    );

    @Operation(
            summary = "Change user password",
            description = "Updates the password for the authenticated user. Requires current password verification.",
            security = @SecurityRequirement(name = "bearer-jwt")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Password changed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "timeStamp": "2025-11-20 10:30:00",
                          "httpStatus": "OK",
                          "statusCode": 200,
                          "reason": "Change password request sent by User",
                          "message": "Successfully changed password"
                        }
                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid old password or validation failed",
                    content = @Content(schema = @Schema(implementation = HttpResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - JWT token missing or invalid",
                    content = @Content(schema = @Schema(implementation = HttpResponse.class))
            )
    })
    ResponseEntity<HttpResponse> changePassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Password update data with old and new password",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateUserPasswordRequest.class),
                            examples = @ExampleObject(
                                    value = """
                        {
                          "oldPassword": "OldPassword123!",
                          "newPassword": "NewPassword456!"
                        }
                        """
                            )
                    )
            )
            UpdateUserPasswordRequest request,
            @io.swagger.v3.oas.annotations.Parameter(hidden = true)
            Authentication authentication
    );
}