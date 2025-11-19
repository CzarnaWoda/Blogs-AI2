package me.blackwater.blogsai2.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.domain.exception.IllegalAccountAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;


    public boolean hasAuthority(String refreshToken, String authority){
        final Jwt decodedToken = jwtDecoder.decode(refreshToken);

        final List<String> authorities = decodedToken.getClaimAsStringList("authorities");

        if(authorities.isEmpty()){
            throw new IllegalAccountAccessException("Illegal access to account");
        }
        if(!authorities.contains(authority)){
            throw new IllegalAccountAccessException("Illegal access to account");
        }
        return true;
    }

    public String generateToken(Authentication authentication){
        final Instant expiredTime = Instant.now().plus(24, ChronoUnit.HOURS);


        final JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(expiredTime)
                .subject(authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
