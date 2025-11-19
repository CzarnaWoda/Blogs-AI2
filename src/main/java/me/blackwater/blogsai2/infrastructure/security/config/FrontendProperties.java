package me.blackwater.blogsai2.infrastructure.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "frontend")
public record FrontendProperties(
        String verificationCodeUrl,
        List<String> allowedMethods,
        OriginPatternProperties[] originPatternProperties,
        List<String> allowedHeaders,
        long maxAge,
        boolean allowCredentials
) {

    public record OriginPatternProperties(
            boolean https,
            String address,
            String port
    ) {
    }
}
