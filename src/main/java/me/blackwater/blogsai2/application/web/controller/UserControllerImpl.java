package me.blackwater.blogsai2.application.web.controller;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.api.util.TimeUtil;
import me.blackwater.blogsai2.application.mapper.UserDtoMapper;
import me.blackwater.blogsai2.application.web.request.PageRequest;
import me.blackwater.blogsai2.application.web.request.PageRequestStringFilter;
import me.blackwater.blogsai2.application.web.request.UpdateUserRequest;
import me.blackwater.blogsai2.application.web.request.UserRoleRequest;
import me.blackwater.blogsai2.domain.exception.IllegalActionException;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.infrastructure.handler.user.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController{

    private final GetUserByUserNameHandler getUserByUserNameHandler;
    private final GetUserByIdHandler getUserByIdHandler;
    private final UpdateUserByIdHandler updateUserByIdHandler;
    private final GetUserPageHandler getUserPageHandler;
    private final UserDtoMapper userDtoMapper;
    private final AddRoleUserHandler addRoleUserHandler;
    private final RemoveRoleUserHandler removeRoleUserHandler;
    private final GetUserByEmailHandler getUserByEmailHandler;
    private final GetUserPageByEmailHandler getUserPageByEmailHandler;
    private final GetUserPageByRoleHandler getUserPageByRoleHandler;
    private final GetUserPageByEmailAndRoleHandler getUserPageByEmailAndRoleHandler;
    @Override
    @PatchMapping("/role/add")
    public ResponseEntity<HttpResponse> addRole(@RequestBody UserRoleRequest addUserRoleRequest) {
        final User user = addRoleUserHandler.execute(addUserRoleRequest);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .reason("Add role to user request")
                        .message("Role has been added")
                        .data(Map.of("user", userDtoMapper.toDto(user)))
                        .build());
    }

    @Override
    @PatchMapping("/role/remove")
    public ResponseEntity<HttpResponse> removeRole(@RequestBody UserRoleRequest removeUserRoleRequest) {
        final User user = removeRoleUserHandler.execute(removeUserRoleRequest);

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .reason("Remove role from user request")
                        .message("Role has been removed")
                        .data(Map.of("user", userDtoMapper.toDto(user)))
                        .build());
    }

    @Override
    @PutMapping
    public ResponseEntity<HttpResponse> update(@RequestBody UpdateUserRequest updateUserRequest, Authentication authentication) {
        if (updateUserRequest.id() == null){
            final User user = getUserByEmailHandler.execute(authentication.getName());

            final User updatedUser = updateUserByIdHandler.execute(new UpdateUserRequest(user.getId(),updateUserRequest.userName(),updateUserRequest.countryCode(),updateUserRequest.phoneNumber()));

            return ResponseEntity.status(OK)
                    .body(HttpResponse.builder()
                            .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                            .httpStatus(OK)
                            .statusCode(OK.value())
                            .reason("Update user request")
                            .message("User has been updated")
                            .data(Map.of("user", userDtoMapper.toDto(updatedUser)))
                            .build());
        }else{
            if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
                throw new IllegalActionException("Illegal action for update!");
            }
            final User user = updateUserByIdHandler.execute(updateUserRequest);

            return ResponseEntity.status(OK)
                    .body(HttpResponse.builder()
                            .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                            .httpStatus(OK)
                            .statusCode(OK.value())
                            .reason("Update user request")
                            .message("User has been updated")
                            .data(Map.of("user", userDtoMapper.toDto(user)))
                            .build());
        }
    }

    @Override
    @GetMapping("")
    public ResponseEntity<HttpResponse> users(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final Page<User> users = getUserPageHandler.execute(new PageRequest(page,size));

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .message("Users page")
                        .reason("Users page request")
                        .data(Map.of("totalElements", users.getTotalElements(),"totalPages", users.getTotalPages(),"users", users.getContent().stream().map(userDtoMapper::toDtoWithId).toList()))
                .build());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> user(@PathVariable long id) {
        final User user = getUserByIdHandler.execute(id);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .data(Map.of("user", userDtoMapper.toDto(user)))
                        .message("User by id data")
                        .reason("User by id request")
                        .httpStatus(OK)
                        .statusCode(OK.value())
                .build());
    }

    @Override
    @GetMapping("/username/{username}")
    public ResponseEntity<HttpResponse> user(@PathVariable String username) {
        final User user = getUserByUserNameHandler.execute(username);

        return ResponseEntity.status(OK).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .data(Map.of("user", userDtoMapper.toDto(user)))
                .message("User by username data")
                .reason("User by username request")
                .httpStatus(OK)
                .statusCode(OK.value())
                .build());
    }

    @Override
    @GetMapping("/byEmail")
    public ResponseEntity<HttpResponse> usersByEmail(@RequestParam(defaultValue = ".com") String email, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final var users = getUserPageByEmailHandler.execute(new PageRequestStringFilter(page,size,email));

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .reason("Users page request by email filter")
                        .message("Users page data by email filter")
                        .data(Map.of("users", users.getContent().stream().map(userDtoMapper::toDtoWithId).toList(), "totalElements" , users.getTotalElements(), "totalPages", users.getTotalPages()))
                        .build());
    }

    @Override
    @GetMapping("/byRole")
    public ResponseEntity<HttpResponse> usersByRole(@RequestParam(defaultValue = "USER") String role, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final var users = getUserPageByRoleHandler.execute(new PageRequestStringFilter(page,size,role));

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .reason("Users page request by role filter")
                        .message("Users page data by role filter")
                        .data(Map.of("users", users.getContent().stream().map(userDtoMapper::toDtoWithId).toList(), "totalElements" , users.getTotalElements(), "totalPages", users.getTotalPages()))
                        .build());
    }

    @Override
    @GetMapping("/byEmailRole")
    public ResponseEntity<HttpResponse> usersByEmailAndRole(@RequestParam(defaultValue = "USER") String role, @RequestParam(defaultValue = ".com") String email, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        final var users = getUserPageByEmailAndRoleHandler.execute(new PageRequestStringFilter.PageRequestStringFilters(page,size,email,role));

        return ResponseEntity.status(OK)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .httpStatus(OK)
                        .statusCode(OK.value())
                        .reason("Users page request by role and email filter")
                        .message("Users page data by role and email filter")
                        .data(Map.of("users", users.getContent().stream().map(userDtoMapper::toDtoWithId).toList(), "totalElements" , users.getTotalElements(), "totalPages", users.getTotalPages()))
                        .build());
    }
}
