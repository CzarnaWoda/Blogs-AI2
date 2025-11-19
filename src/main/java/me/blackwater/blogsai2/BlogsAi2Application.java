package me.blackwater.blogsai2;

import me.blackwater.blogsai2.infrastructure.security.config.FrontendProperties;
import me.blackwater.blogsai2.infrastructure.security.properties.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({RsaKeyProperties.class, FrontendProperties.class})
public class BlogsAi2Application {

    public static void main(String[] args) {
        SpringApplication.run(BlogsAi2Application.class, args);
    }

}
