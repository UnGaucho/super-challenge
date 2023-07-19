package com.interview.demo.auditing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuditingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Given
     *  A few http requests (including unmapped endpoints)
     * When
     *  A http request on /logs is performed
     * Then
     *  Verify that logged requests are returned
     */
    @Test
    public void getPaginatedLogsReturnsLoggedRequests() throws Exception {

        // Few mock requests.
        mockMvc.perform(get("/api/test1"));
        mockMvc.perform(post("/api/test2"));
        mockMvc.perform(put("/api/test3"));


        // Do GET request
        ResultActions result = mockMvc.perform(get("/logs?sort=id"));

        // Expect logged requests
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3))
                .andExpect(jsonPath("$.content[*].path",
                        containsInAnyOrder("/api/test1", "/api/test2", "/api/test3")));
    }
}