package me.blackwater.blogsai2.infrastructure.handler.user;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.UpdateUserPasswordRequest;
import me.blackwater.blogsai2.domain.exception.InvalidCredentialsException;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Handler(name = "Update password by id", handlerType = HandlerType.UPDATE, transactional = true)
public class UpdateUserPasswordByIdHandler implements UpdateHandler<User, UpdateUserPasswordRequest> {

    private final GetUserByIdHandler getUserByIdHandler;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User execute(UpdateUserPasswordRequest dto) {
        final User user = getUserByIdHandler.execute(dto.id());

        if(!passwordEncoder.matches(dto.oldPassword(),user.getPassword())){
            throw new InvalidCredentialsException("Invalid password!");
        }

        user.changePassword(passwordEncoder.encode(dto.newPassword()));

        return userRepository.save(user);
    }
}
