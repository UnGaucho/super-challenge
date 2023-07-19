package com.interview.demo.auditing.http;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.IOException;

@Getter
public class RequestEvent extends ApplicationEvent {
    private final String method;
    private final String path;
    private final String params;
    private final String body;
    private final int status;
    private final String responseBody;

    public RequestEvent(CachedBodyHttpServletRequest request, CachedHttpServletResponse response) {
        super(request);

        this.method = request.getCachedMethod();
        this.path = request.getCachedRequestUri();
        this.params = request.getCachedParams();

        String bodyString;
        try {
            bodyString = request.getRequestBody();
        } catch (IOException ignored) {
            bodyString = null;
        }
        this.body = bodyString;

        status = response.getCachedStatus();
        String responseBody = null;
        if (status >= 400) { // only log response body on error
            responseBody = response.getResponseBody();
        }
        this.responseBody = responseBody;
    }

}
