package me.blackwater.blogsai2.infrastructure.security.provider;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.exception.InvalidCredentialsException;
import me.blackwater.blogsai2.infrastructure.security.service.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        final String password = userDetails.getPassword();

        final String credentials = (String) authentication.getCredentials();

        if(credentials == null || password == null){
            throw new InvalidCredentialsException("Credentials are null or empty");
        }
        if(!passwordEncoder.matches(credentials,password)){
            throw new InvalidCredentialsException("Typed password is wrong");
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        return customUserDetailsService.loadUserByUsername(username);
    }
}
