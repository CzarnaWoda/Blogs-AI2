package me.blackwater.blogsai2.infrastructure.handler.user;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.exception.UserNotFoundException;
import me.blackwater.blogsai2.domain.repository.UserRepository;

@RequiredArgsConstructor
@Handler(name = "get user by id", handlerType = HandlerType.GET)
public class GetUserByIdHandler implements GetHandler<User,Long> {

    private final UserRepository userRepository;

    @Override
    public User execute(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
    }

    @Override
    public boolean trace() {
        return true;
    }
}
