package com.interview.demo.auditing.config;

import com.interview.demo.auditing.http.RequestEventDispatchFilter;
import com.interview.demo.auditing.http.RequestEventListener;
import com.interview.demo.auditing.http.RequestJpaListener;
import com.interview.demo.auditing.RequestRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class AuditConfig {

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster =
                new SimpleApplicationEventMulticaster();

        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    public RequestEventDispatchFilter filter(ApplicationEventPublisher eventPublisher) {
        return new RequestEventDispatchFilter(eventPublisher);
    }

    @Bean
    public FilterRegistrationBean<RequestEventDispatchFilter> asyncEventFilterRegistration(
            RequestEventDispatchFilter filter) {
        FilterRegistrationBean<RequestEventDispatchFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setName("requestEventDispatchFilter");
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public RequestEventListener auditListener(RequestRepository repository) {
        return new RequestJpaListener(repository);
    }
}
