package com.interview.demo.auditing.http;

import com.interview.demo.auditing.RequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RequestJpaListenerTest {

    @Mock
    RequestRepository mockRepo;

    @Mock
    RequestEvent mockEvent;

    @Test
    public void repositoryCalledOnEvent() {
        RequestJpaListener listener = new RequestJpaListener(mockRepo);
        listener.onRequest(mockEvent);
        verify(mockRepo).save(any());
    }
}