package me.blackwater.blogsai2;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.application.web.request.CreateUserRequest;
import me.blackwater.blogsai2.application.web.request.UpdateUserRequest;
import me.blackwater.blogsai2.domain.exception.UserNotFoundException;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;
import me.blackwater.blogsai2.infrastructure.handler.user.CreateUserHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByEmailHandler;
import me.blackwater.blogsai2.infrastructure.handler.user.UpdateUserByIdHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataFactory implements CommandLineRunner {
    private final CreateUserHandler createUserHandler;
    private final UpdateUserByIdHandler updateUserByIdHandler;
    private final GetUserByEmailHandler getUserByEmailHandler;
    private final UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
        try{
            final User user = getUserByEmailHandler.execute("admin@email.com");
        }catch (UserNotFoundException e){
            final User user = createUserHandler.execute(new CreateUserRequest("ADMIN","Admin!123","+48","801802803","admin@email.com"));

            user.addRole("ADMIN");

            userRepository.save(user);
        }
    }
}
