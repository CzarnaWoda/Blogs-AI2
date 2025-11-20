package me.blackwater.blogsai2.infrastructure.handler.user;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.exception.UserNotFoundException;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;

@Handler(name = "get user by phone", handlerType = HandlerType.GET)
@RequiredArgsConstructor
public class GetUserByPhoneNumberHandler implements GetHandler<User,String> {

    private final UserRepository userRepository;
    @Override
    public User execute(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new UserNotFoundException("User by phone has not been found"));
    }
}
