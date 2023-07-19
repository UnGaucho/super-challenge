package com.interview.demo;

import com.interview.demo.service.CalcService;
import com.interview.demo.service.PercentageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableWebMvc
public class InterviewChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewChallengeApplication.class, args);
    }

}
