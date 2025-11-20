package me.blackwater.blogsai2.infrastructure.handler.user;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.CreateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.domain.model.Email;
import me.blackwater.blogsai2.domain.model.Phone;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.exception.UserAlreadyExistException;
import me.blackwater.blogsai2.domain.repository.UserRepository;
import me.blackwater.blogsai2.application.web.request.CreateUserRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Handler(name = "create user", handlerType = HandlerType.CREATE, transactional = true)
public class CreateUserHandler implements CreateHandler<User, CreateUserRequest> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User execute(CreateUserRequest dto) {
        if(userRepository.findByUserName(dto.username()).isPresent()){
            throw new UserAlreadyExistException("User with that userName already exists");
        }
        if(userRepository.findByEmail(new Email(dto.email())).isPresent()){
            throw new UserAlreadyExistException("User with that email already exists");
        }
        if(userRepository.findByPhoneNumber(dto.phone()).isPresent()){
            throw new UserAlreadyExistException("User with that phone number already exists");
        }
        //TODO encode passwords
        final User newUser  = new User(dto.username(),passwordEncoder.encode(dto.password()),true,false,new Phone(dto.phone(),"+48"),new Email(dto.email()));

        return userRepository.save(newUser);
    }

    @Override
    public boolean trace() {
        return true;
    }
}
