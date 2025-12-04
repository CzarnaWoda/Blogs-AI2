package me.blackwater.blogsai2.application.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.application.web.request.UpdateUserRequest;
import me.blackwater.blogsai2.application.web.request.UserRoleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

@Tag(
        name = "User Management",
        description = "Endpoints for managing users, their profiles, and role assignments"
)
public interface UserController {

    @Operation(
            summary = "Add role to user",
            description = "Assigns a new role to an existing user. Requires ADMIN privileges.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role successfully added to user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Add role to user request",
                                              "message": "Role has been added",
                                              "data": {
                                                "user": {
                                                  "userName": "johndoe",
                                                  "phone": "+48123456789",
                                                  "email": "user@example.com",
                                                  "userRoles": ["USER", "ADMIN"]
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/ForbiddenResponse")
    })
    ResponseEntity<HttpResponse> addRole(@Valid UserRoleRequest addUserRoleRequest);

    @Operation(
            summary = "Remove role from user",
            description = "Removes an assigned role from an existing user. Requires ADMIN privileges.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role successfully removed from user",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Remove role from user request",
                                              "message": "Role has been removed",
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
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/ForbiddenResponse")
    })
    ResponseEntity<HttpResponse> removeRole(@Valid UserRoleRequest removeUserRoleRequest);

    @Operation(
            summary = "Update user profile",
            description = "Updates user profile information. If ID is provided, updates specified user (requires appropriate permissions). If ID is null, updates the authenticated user's own profile.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully updated",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Update user request",
                                              "message": "User has been updated",
                                              "data": {
                                                "user": {
                                                  "userName": "johndoe_updated",
                                                  "phone": "+48987654321",
                                                  "email": "user@example.com",
                                                  "userRoles": ["USER"]
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse"),
            @ApiResponse(responseCode = "403", ref = "#/components/responses/ForbiddenResponse")
    })
    ResponseEntity<HttpResponse> update(
            @Valid UpdateUserRequest updateUserRequest,
            @Parameter(hidden = true) Authentication authentication
    );

    @Operation(
            summary = "Get paginated list of users",
            description = "Retrieves a paginated list of all users in the system.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved users page",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "Users page request",
                                              "message": "Users page",
                                              "data": {
                                                "totalElements": 25,
                                                "totalPages": 5,
                                                "users": [
                                                  {
                                                    "id": 1,
                                                    "userName": "johndoe",
                                                    "phone": "+48123456789",
                                                    "email": "john@example.com",
                                                    "userRoles": ["USER", "ADMIN"]
                                                  },
                                                  {
                                                    "id": 2,
                                                    "userName": "janedoe",
                                                    "phone": "+48987654321",
                                                    "email": "jane@example.com",
                                                    "userRoles": ["USER"]
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> users(
            @Parameter(description = "Page number (zero-based)", example = "0") int page,
            @Parameter(description = "Number of items per page", example = "5") int size
    );

    @Operation(
            summary = "Get user by ID",
            description = "Retrieves detailed information about a specific user by their ID.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "User by id request",
                                              "message": "User by id data",
                                              "data": {
                                                "user": {
                                                  "userName": "johndoe",
                                                  "phone": "+48123456789",
                                                  "email": "user@example.com",
                                                  "userRoles": ["USER", "ADMIN"]
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> user(
            @Parameter(description = "User ID", required = true, example = "1") long id
    );

    @Operation(
            summary = "Get user by username",
            description = "Retrieves detailed information about a specific user by their username.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully retrieved",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HttpResponse.class),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "timeStamp": "2025-12-03T10:30:00",
                                              "httpStatus": "OK",
                                              "statusCode": 200,
                                              "reason": "User by username request",
                                              "message": "User by username data",
                                              "data": {
                                                "user": {
                                                  "userName": "johndoe",
                                                  "phone": "+48123456789",
                                                  "email": "user@example.com",
                                                  "userRoles": ["USER", "ADMIN"]
                                                }
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequestResponse"),
            @ApiResponse(responseCode = "401", ref = "#/components/responses/UnauthorizedResponse")
    })
    ResponseEntity<HttpResponse> user(
            @Parameter(description = "Username", required = true, example = "johndoe") String userName
    );
}