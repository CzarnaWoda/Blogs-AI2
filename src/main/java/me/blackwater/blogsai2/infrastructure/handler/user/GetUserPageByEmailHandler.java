package me.blackwater.blogsai2.infrastructure.handler.user;


import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.enums.HandlerType;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.stereotype.Handler;
import me.blackwater.blogsai2.application.web.request.PageRequestStringFilter;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Handler(handlerType = HandlerType.GET, name = "Page users by email")
@RequiredArgsConstructor
public class GetUserPageByEmailHandler implements GetHandler<Page<User>, PageRequestStringFilter> {
    private final UserRepository userRepository;

    @Override
    public Page<User> execute(PageRequestStringFilter pageRequestStringFilter) {
        return userRepository.findAllByEmail(pageRequestStringFilter.stringFilter(), PageRequest.of(pageRequestStringFilter.page(), pageRequestStringFilter.size(), Sort.Direction.DESC, "createdAt"));
    }
}
