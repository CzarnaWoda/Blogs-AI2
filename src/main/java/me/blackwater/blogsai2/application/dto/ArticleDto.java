package me.blackwater.blogsai2.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Article data transfer object containing article information")
public record ArticleDto(
        @Schema(
                description = "Unique identifier of the article",
                example = "1",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        long id,

        @Schema(
                description = "Title of the article",
                example = "Introduction to Spring Boot",
                maxLength = 255
        )
        String title,

        @Schema(
                description = "Full content of the article",
                example = "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can just run..."
        )
        String content,

        @Schema(
                description = "Number of times the article has been viewed",
                example = "150",
                minimum = "0",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        int views,

        @Schema(
                description = "Number of likes the article has received",
                example = "42",
                minimum = "0",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        int likes
) {
}