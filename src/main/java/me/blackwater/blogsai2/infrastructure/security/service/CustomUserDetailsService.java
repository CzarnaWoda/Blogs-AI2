package me.blackwater.blogsai2.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.model.User;
import me.blackwater.blogsai2.infrastructure.handler.user.GetUserByEmailHandler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final GetUserByEmailHandler getUserByEmailHandler;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final User user = getUserByEmailHandler.execute(email);

        return new org.springframework.security.core.userdetails.User(user.getEmail().value(),user.getPassword(),user.isEnabled(),true,true,!user.isBlocked(),user.getAuthorities());
    }
}
