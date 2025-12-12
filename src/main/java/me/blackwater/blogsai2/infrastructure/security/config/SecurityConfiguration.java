package me.blackwater.blogsai2.infrastructure.security.config;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.infrastructure.security.filter.JwtAuthenticationFilter;
import me.blackwater.blogsai2.infrastructure.security.provider.AccountAuthenticationProvider;
import me.blackwater.blogsai2.infrastructure.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
class SecurityConfiguration {

    private final FrontendProperties frontendProperties;
    private final CustomUserDetailsService detailsService;
    private final AccountAuthenticationProvider authenticationProvider;
    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = security.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        authenticationManagerBuilder.userDetailsService(detailsService);
        security.csrf(AbstractHttpConfigurer::disable);
        security.oauth2ResourceServer(Customizer.withDefaults());
        security.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));
        security.oauth2ResourceServer(o2auth -> o2auth.jwt(jwtConfigurer -> {
            jwtConfigurer.decoder(jwtDecoder);
            jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter());
        }));

        security.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(GET,"/swagger-ui.html").permitAll()
                        .requestMatchers(POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(POST,"/api/v1/auth/register").permitAll()
                        .requestMatchers(GET, "/api/v1/auth/authorities").authenticated()
                        .requestMatchers(POST, "/api/v1/auth/changePassword").authenticated()
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(POST, "/api/v1/sections").hasAuthority("ADMIN")
                        .requestMatchers(PUT, "/api/v1/sections").hasAuthority("MODERATOR")
                        .requestMatchers(GET, "/api/v1/sections/title/{title}").permitAll()
                        .requestMatchers(GET, "/api/v1/sections/type/{type}").permitAll()
                        .requestMatchers(GET, "/api/v1/sections/{id}").permitAll()
                        .requestMatchers(GET, "/api/v1/sections").permitAll()

                        // ===== ARTICLES =====
                        .requestMatchers(POST, "/api/v1/articles").hasAuthority("MODERATOR")
                        .requestMatchers(PUT, "/api/v1/articles").hasAuthority("MODERATOR")
                        .requestMatchers(PATCH, "/api/v1/articles/like").authenticated()
                        .requestMatchers(GET, "/api/v1/articles/title/{title}").permitAll()
                        .requestMatchers(GET, "/api/v1/articles/author/{authorId}").permitAll()
                        .requestMatchers(GET, "/api/v1/articles/section/**").permitAll()
                        .requestMatchers(GET, "/api/v1/articles/{id}").permitAll()
                        .requestMatchers(GET, "/api/v1/articles/count/{authorName}").authenticated()
                        .requestMatchers(GET, "/api/v1/articles").permitAll()

                        // ===== USERS =====
                        .requestMatchers(PATCH, "/api/v1/users/role/add").hasAuthority("ADMIN")
                        .requestMatchers(PATCH, "/api/v1/users/role/remove").hasAuthority("ADMIN")
                        .requestMatchers(GET, "/api/v1/users/byEmail").hasAuthority("ADMIN")
                        .requestMatchers(GET, "/api/v1/users/byRole").hasAuthority("ADMIN")
                        .requestMatchers(GET, "/api/v1/users/byEmailRole").hasAuthority("ADMIN")
                        .requestMatchers(GET, "/api/v1/users/username/{username}").authenticated()
                        .requestMatchers(PUT, "/api/v1/users/{id}").authenticated()
                        .requestMatchers(GET, "/api/v1/users").hasAuthority("ADMIN")

                        // ===== COMMENTS =====
                        .requestMatchers(POST, "/api/v1/comments").hasAuthority("USER")
                        .requestMatchers(PATCH, "/api/v1/comments/disable/**").hasAuthority("HELPER")
                        .requestMatchers(PATCH, "/api/v1/comments/like").authenticated()
                        .requestMatchers(GET, "/api/v1/comments/{id}").authenticated()
                        .requestMatchers(GET, "/api/v1/comments").permitAll()

                        // ===== AUTH =====
                        .requestMatchers(GET, "/api/v1/auth/me").authenticated()

                        // ===== DEFAULT =====
                        .anyRequest().authenticated()
        )
                .authenticationManager(authenticationManagerBuilder.build())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return security.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        final JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities"); // defaults to "scope" or "scp"
        grantedAuthoritiesConverter.setAuthorityPrefix(""); // defaults to "SCOPE_"

        final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(
                Arrays.stream(frontendProperties.originPatternProperties()).map(originPatternProperty -> originPatternProperty.https() ? "https://" : "http://" + originPatternProperty.address() + ":" + originPatternProperty.port()).toList()
        );

        configuration.setAllowedMethods(frontendProperties.allowedMethods());
        configuration.setAllowedHeaders(frontendProperties.allowedHeaders()); // Akceptuj wszystkie nagłówki
        configuration.setMaxAge(frontendProperties.maxAge());
        configuration.setAllowCredentials(frontendProperties.allowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
