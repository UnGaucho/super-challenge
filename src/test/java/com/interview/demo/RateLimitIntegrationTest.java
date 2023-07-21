package com.interview.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "interview.ratelimit.enabled=true"

)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RateLimitIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Given
     *  A few requests
     * When
     *  A http request on /sum is performed
     * Then
     *  Verify that a 429 response is returned
     */
    @Test
    public void rateLimitedStatusCode429() throws Exception {

        // Attempt to trigger rate limiter with additional requests
        for (int i = 0; i < 3; i++) {
            mockMvc.perform(post("/api/sum")
                    .param("a", "4")
                    .param("b", "6")
            ).andExpect(status().isOk());
        }

        mockMvc.perform(post("/api/sum")
                .param("a", "4")
                .param("b", "6")
        ).andExpect(status().isTooManyRequests());
    }
}
