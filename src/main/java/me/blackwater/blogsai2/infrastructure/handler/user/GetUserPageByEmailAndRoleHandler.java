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

@Handler(handlerType = HandlerType.GET , name = "user page by email and role")
@RequiredArgsConstructor
public class GetUserPageByEmailAndRoleHandler implements GetHandler<Page<User>, PageRequestStringFilter.PageRequestStringFilters> {

    private final UserRepository userRepository;
    @Override
    public Page<User> execute(PageRequestStringFilter.PageRequestStringFilters pageRequestStringFilters) {
        return userRepository.findAllByUserRoleAndEmail(pageRequestStringFilters.secondStringFilter(), pageRequestStringFilters.stringFilter(), PageRequest.of(pageRequestStringFilters.page(), pageRequestStringFilters.size(), Sort.Direction.DESC,"createdAt"));
    }
}
