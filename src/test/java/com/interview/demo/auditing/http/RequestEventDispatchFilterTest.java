package com.interview.demo.auditing.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestEventDispatchFilterTest {

    MockEventPublisher publisher;
    RequestEventDispatchFilter filter;
    MockHttpServletRequest req;
    MockHttpServletResponse res;

    @BeforeEach
    public void setupBefore() {
        req = new MockHttpServletRequest("GET", "test");
        res = new MockHttpServletResponse();
        res.setStatus(200);
        publisher = new MockEventPublisher();
        filter = new RequestEventDispatchFilter(publisher);
    }

    @Test
    public void doFilterPublishesRequestContainingRequestData() throws Exception {

        filter.doFilterInternal(req, res, new MockFilterChain());

        assertEquals(req.getMethod(), publisher.events.get(0).getMethod());
    }

    @Test
    public void doFilterPublishesResponseContainingResponseData() throws Exception {
        filter.doFilterInternal(req, res, new MockFilterChain());

        assertEquals(res.getStatus(), publisher.events.get(0).getStatus());
    }

    private static class MockEventPublisher implements ApplicationEventPublisher {
        public final List<RequestEvent> events = new ArrayList<>();

        @Override
        public void publishEvent(Object event) {
            events.add((RequestEvent) event);
        }
    }
}