package com.interview.demo.auditing.http;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestEventDispatchFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ApplicationEventPublisher eventPublisher;
    public RequestEventDispatchFilter(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        //  Wrapped up as they can only be read once usually
        var wrapped = new CachedBodyHttpServletRequest(request);
        var wrappedResponse = new CachedHttpServletResponse(response);
        try {
            filterChain.doFilter(wrapped, wrappedResponse);
            wrappedResponse.copyBodyToResponse();
        } finally {
            logger.debug("Publishing request event");
            eventPublisher.publishEvent(new RequestEvent(wrapped, wrappedResponse));
        }

    }
}
