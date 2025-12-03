package me.blackwater.blogsai2.infrastructure.handler.user;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.UserRoleRequest;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;

@Handler(handlerType = HandlerType.UPDATE, name = "add role to user")
@RequiredArgsConstructor
public class AddRoleUserHandler implements UpdateHandler<User, UserRoleRequest> {

    private final GetUserByUserNameHandler getUserByUserNameHandler;
    private final UserRepository userRepository;

    @Override
    public User execute(UserRoleRequest dto) {
        final User user = getUserByUserNameHandler.execute(dto.username());

        user.addRole(dto.role());

        return userRepository.save(user);
    }
}
