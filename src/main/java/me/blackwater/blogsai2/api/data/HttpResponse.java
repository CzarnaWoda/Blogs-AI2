package me.blackwater.blogsai2.api.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard HTTP response wrapper for all API endpoints")
public class HttpResponse {

    @Schema(
            description = "Timestamp when the response was generated",
            example = "2025-11-20T10:30:00"
    )
    protected String timeStamp;

    @Schema(
            description = "HTTP status enum",
            example = "OK"
    )
    protected HttpStatus httpStatus;

    @Schema(
            description = "HTTP status code",
            example = "200"
    )
    protected int statusCode;

    @Schema(
            description = "Short reason phrase for the status",
            example = "Success"
    )
    protected String reason;

    @Schema(
            description = "Detailed message describing the result",
            example = "Operation completed successfully"
    )
    protected String message;

    @Schema(
            description = "Response payload containing the actual data",
            example = "{\"token\": \"eyJhbGc...\", \"user\": {\"id\": 1, \"email\": \"user@example.com\"}}"
    )
    private Map<?,?> data;
}