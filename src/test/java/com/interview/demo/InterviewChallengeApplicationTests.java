package com.interview.demo;

import com.interview.demo.config.TestRedisConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(properties = "spring.data.redis.port=6381")
@Import(TestRedisConfiguration.class)
class InterviewChallengeApplicationTests {

    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }

    @Test
    void cacheManagerWorks(@Autowired CacheManager cacheManager) {
        Cache testcache = cacheManager.getCache("testcache");

        testcache.put("key", "value");
        assertEquals(testcache.get("key", String.class), "value");
    }
}
