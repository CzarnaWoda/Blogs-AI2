package me.blackwater.blogsai2.infrastructure.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("Blogs API")
                .version("1.0")
                        .description("Blogs API Documentation")
                        .contact(new Contact().name("BlackWater").email("kmimat299@gmail.com")));
    }
}
