package me.blackwater.blogsai2.infrastructure.handler.user;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.PageRequest;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Handler(name = "get user page", handlerType = HandlerType.GET)
@RequiredArgsConstructor
public class GetUserPageHandler implements GetHandler<Page<User>, PageRequest> {

    private final UserRepository userRepository;

    @Override
    public Page<User> execute(PageRequest pageRequest) {
        return userRepository.findAll(org.springframework.data.domain.PageRequest.of(pageRequest.page(),pageRequest.size(), Sort.Direction.ASC,"createdAt"));
    }
}
