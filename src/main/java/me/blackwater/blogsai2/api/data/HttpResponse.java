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
@Schema(
        description = "Standard HTTP response wrapper for all API endpoints",
        example = """
                {
                  "timeStamp": "2025-12-03T10:30:00",
                  "httpStatus": "OK",
                  "statusCode": 200,
                  "reason": "Success",
                  "message": "Operation completed successfully",
                  "data": {
                    "user": {
                      "userName": "johndoe",
                      "email": "user@example.com"
                    }
                  }
                }
                """
)
public class HttpResponse {

    @Schema(
            description = "Timestamp when the response was generated",
            example = "2025-12-03T10:30:00",
            required = true
    )
    protected String timeStamp;

    @Schema(
            description = "HTTP status enum",
            example = "OK",
            required = true
    )
    protected HttpStatus httpStatus;

    @Schema(
            description = "HTTP status code",
            example = "200",
            required = true
    )
    protected int statusCode;

    @Schema(
            description = "Short reason phrase for the status",
            example = "Success",
            required = true
    )
    protected String reason;

    @Schema(
            description = "Detailed message describing the result",
            example = "Operation completed successfully",
            required = true
    )
    protected String message;

    @Schema(
            description = "Response payload containing the actual data. This field is present only in successful responses (2xx status codes). For error responses (4xx, 5xx), this field will be null or absent.",
            example = "{\"user\": {\"id\": 1, \"email\": \"user@example.com\"}}",
            nullable = true
    )
    private Map<?, ?> data;
}