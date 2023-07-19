package com.interview.demo.auditing.http;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

public interface RequestEventListener {
    @Async
    @EventListener
    @Transactional
    void onRequest(RequestEvent ev);
}
