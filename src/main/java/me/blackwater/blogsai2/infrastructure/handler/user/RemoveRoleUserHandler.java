package me.blackwater.blogsai2.infrastructure.handler.user;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.UserRoleRequest;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;

@Handler(handlerType = HandlerType.UPDATE,name = "remove role user")
@RequiredArgsConstructor
public class RemoveRoleUserHandler implements UpdateHandler<User,UserRoleRequest> {

    private final UserRepository userRepository;
    private final GetUserByUserNameHandler getUserByUserNameHandler;
    @Override
    public User execute(UserRoleRequest dto) {
        final User user = getUserByUserNameHandler.execute(dto.username());

        user.addRole(dto.role());

        return userRepository.save(user);
    }
}
