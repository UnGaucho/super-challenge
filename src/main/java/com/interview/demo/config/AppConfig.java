package com.interview.demo.config;

import com.interview.demo.service.CachedPercentageService;
import com.interview.demo.service.CalcService;
import com.interview.demo.service.MockPercentageService;
import com.interview.demo.service.PercentageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Main business logic beans config
 */
@Configuration(proxyBeanMethods = false)
public class AppConfig {

    @Bean
    @Qualifier("mockPercentage")
    public PercentageService mockRemote() {
        return new MockPercentageService();
    }

    @Bean
    public PercentageService percentageService(CacheManager cacheManager,
                                               @Qualifier("mockPercentage") PercentageService mock) {
        return new CachedPercentageService(cacheManager.getCache("percentage"), mock);
    }

    @Bean
    public CalcService calcService(PercentageService percentageService) {
        return new CalcService(percentageService);
    }
}
