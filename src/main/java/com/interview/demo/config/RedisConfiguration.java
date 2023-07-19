package com.interview.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Overrides config, not much else. Spring docker compose is intelligent enough to autoconfigure Redis connection
 * details from a started redis container or the docker-compose.yml :)
 */
@Configuration
@ConditionalOnExpression("'${interview.redis.override:false}' == 'true'")
public class RedisConfiguration {

    @Configuration
    @ConditionalOnExpression("'${interview.redis.override:false}' == 'true'")
    public static class RedisProperties {
        private final int redisPort;
        private final String redisHost;

        public RedisProperties(
                @Value("${spring.data.redis.port}") int redisPort,
                @Value("${spring.data.redis.host}") String redisHost) {
            this.redisPort = redisPort;
            this.redisHost = redisHost;
        }

        public int getRedisPort() {
            return redisPort;
        }

        public String getRedisHost() {
            return redisHost;
        }
    }


    @Bean
    public LettuceConnectionFactory redisConnectionFactory(
      RedisProperties redisProperties) {
        return new LettuceConnectionFactory(
          redisProperties.getRedisHost(), 
          redisProperties.getRedisPort());
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<byte[], byte[]> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}