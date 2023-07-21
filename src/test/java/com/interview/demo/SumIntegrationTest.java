package com.interview.demo;

import com.interview.demo.config.TestRedisConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TestRedisConfiguration.class)
@AutoConfigureMockMvc
public class SumIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Given
     *  A post request with sum params
     * When
     *  A http request on /sum is performed
     * Then
     *  Verify that a 200 response is returned with a summed value
     */
    @Test
    public void getPaginatedLogsReturnsLoggedRequests() throws Exception {

        mockMvc.perform(post("/sum")
                .param("a", "4")
                .param("b", "6")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(notNullValue()));
    }
}