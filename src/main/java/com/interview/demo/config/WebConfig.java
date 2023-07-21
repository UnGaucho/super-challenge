package com.interview.demo.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.apache.catalina.filters.RateLimitFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

import java.util.Map;

import static java.util.Map.entry;
import static org.apache.catalina.filters.RateLimitFilter.PARAM_BUCKET_REQUESTS;

@Configuration
public class WebConfig {

    @Bean
    public RateLimitFilter rateLimit() {
        return new RateLimitFilter();
    }

    @Bean("rateLimitFilter")
    @ConditionalOnProperty(name = "interview.ratelimit.enabled", matchIfMissing = true)
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilterRegistration(RateLimitFilter filter) {
        FilterRegistrationBean<RateLimitFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setInitParameters(Map.ofEntries(
                entry(PARAM_BUCKET_REQUESTS, "3")
        ));
        registration.setName("ratelimiter");
        registration.addUrlPatterns("/api/*");
        registration.setOrder(2);
        return registration;
    }

}
