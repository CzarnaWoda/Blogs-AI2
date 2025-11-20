package me.blackwater.blogsai2.infrastructure.handler.user;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.UpdateUserRequest;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;

@Handler(handlerType = HandlerType.UPDATE, name = "Update user", transactional = true)
@RequiredArgsConstructor
public class UpdateUserByIdHandler implements UpdateHandler<User, UpdateUserRequest> {

    private final UserRepository userRepository;

    private final GetUserByIdHandler getUserByIdHandler;

    @Override
    public User execute(UpdateUserRequest dto) {
        final User user = getUserByIdHandler.execute(dto.id());

        user.update(dto.countryCode(), dto.phoneNumber(), dto.userName());

        return userRepository.save(user);
    }
}
