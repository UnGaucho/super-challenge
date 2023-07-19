package com.interview.demo.auditing.http;


import com.interview.demo.auditing.RequestRepository;
import com.interview.demo.auditing.model.RequestLogEntry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public class RequestJpaListener implements RequestEventListener {

    private final RequestRepository repository;

    public RequestJpaListener(RequestRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener
    @Transactional
    @Override
    public void onRequest(RequestEvent ev) {
        var entry = RequestLogEntry.builder()
                .method(ev.getMethod())
                .path(ev.getPath())
                .params(ev.getParams())
                .body(ev.getBody())
                .status(ev.getStatus())
                .responseBody(ev.getResponseBody())
                .build();

        repository.save(entry);
    }

}