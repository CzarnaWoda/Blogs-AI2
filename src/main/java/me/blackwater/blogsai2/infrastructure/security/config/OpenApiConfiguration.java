package me.blackwater.blogsai2.infrastructure.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
class OpenApiConfiguration {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("BlogsAI API")
                        .version("2.0")
                        .description("REST API for BlogsAI application - User management, authentication, and blog operations")
                        .contact(new Contact()
                                .name("BlackWater")
                                .email("kmimat299@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter JWT token obtained from login endpoint. Format: just paste the token without 'Bearer' prefix."))

                        // Global response schemas
                        .addSchemas("HttpResponse", new Schema<>()
                                .type("object")
                                .description("Standard HTTP response wrapper")
                                .addProperty("timeStamp", new Schema<>().type("string").example("2025-12-03T10:30:00"))
                                .addProperty("httpStatus", new Schema<>().type("string").example("OK"))
                                .addProperty("statusCode", new Schema<>().type("integer").example(200))
                                .addProperty("reason", new Schema<>().type("string").example("Success"))
                                .addProperty("message", new Schema<>().type("string").example("Operation completed successfully"))
                                .addProperty("data", new Schema<>().type("object").nullable(true)))

                        // Global error response examples
                        .addExamples("BadRequestError", new Example()
                                .value("""
                                        {
                                          "timeStamp": "2025-12-03T10:30:00",
                                          "httpStatus": "BAD_REQUEST",
                                          "statusCode": 400,
                                          "reason": "Bad Request",
                                          "message": "Invalid input data"
                                        }
                                        """))

                        .addExamples("UnauthorizedError", new Example()
                                .value("""
                                        {
                                          "timeStamp": "2025-12-03T10:30:00",
                                          "httpStatus": "UNAUTHORIZED",
                                          "statusCode": 401,
                                          "reason": "Authentication required",
                                          "message": "Please authenticate to access this resource"
                                        }
                                        """))

                        .addExamples("ForbiddenError", new Example()
                                .value("""
                                        {
                                          "timeStamp": "2025-12-03T10:30:00",
                                          "httpStatus": "FORBIDDEN",
                                          "statusCode": 403,
                                          "reason": "Access denied",
                                          "message": "You don't have permission to perform this action"
                                        }
                                        """))

                        .addExamples("NotFoundError", new Example()
                                .value("""
                                        {
                                          "timeStamp": "2025-12-03T10:30:00",
                                          "httpStatus": "NOT_FOUND",
                                          "statusCode": 404,
                                          "reason": "Resource not found",
                                          "message": "The requested resource was not found"
                                        }
                                        """))

                        .addExamples("ValidationError", new Example()
                                .value("""
                                        {
                                          "timeStamp": "2025-12-03T10:30:00",
                                          "httpStatus": "BAD_REQUEST",
                                          "statusCode": 400,
                                          "reason": "Validation failed",
                                          "message": "Username cannot be blank"
                                        }
                                        """))

                        .addExamples("UserNotFoundError", new Example()
                                .value("""
                                        {
                                          "timeStamp": "2025-12-03T10:30:00",
                                          "httpStatus": "BAD_REQUEST",
                                          "statusCode": 400,
                                          "reason": "User not found",
                                          "message": "User with the specified identifier was not found"
                                        }
                                        """))

                        // Global responses that can be reused
                        .addResponses("UnauthorizedResponse", new ApiResponse()
                                .description("Unauthorized - Authentication required")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/HttpResponse"))
                                                .addExamples("default", new Example()
                                                        .$ref("#/components/examples/UnauthorizedError")))))

                        .addResponses("ForbiddenResponse", new ApiResponse()
                                .description("Forbidden - Insufficient privileges")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/HttpResponse"))
                                                .addExamples("default", new Example()
                                                        .$ref("#/components/examples/ForbiddenError")))))

                        .addResponses("BadRequestResponse", new ApiResponse()
                                .description("Bad Request - Invalid input")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/HttpResponse"))
                                                .addExamples("validation", new Example()
                                                        .$ref("#/components/examples/ValidationError"))
                                                .addExamples("userNotFound", new Example()
                                                        .$ref("#/components/examples/UserNotFoundError")))))

                        .addResponses("NotFoundResponse", new ApiResponse()
                                .description("Not Found - Resource not found")
                                .content(new Content()
                                        .addMediaType("application/json", new MediaType()
                                                .schema(new Schema<>().$ref("#/components/schemas/HttpResponse"))
                                                .addExamples("default", new Example()
                                                        .$ref("#/components/examples/NotFoundError"))))));
    }
}
