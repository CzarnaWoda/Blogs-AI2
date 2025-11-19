package me.blackwater.blogsai2.api.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
public class HttpResponse {
    protected String timeStamp;
    protected HttpStatus httpStatus;
    protected int statusCode;
    protected String reason;
    protected String message;
    private Map<?,?> data;
}
