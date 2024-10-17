package com.sparta.tse.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching // Spring 에서 캐시 기능을 활성화
public class RedisConfig {

    // Redis 서버랑 연결
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // "localhost"와 6379는 Redis 서버의 기본 호스트와 포트입니다.
        return new LettuceConnectionFactory("localhost", 6379);
    }

    // RedisTemplate<String, Integer> 설정
    @Bean
    public RedisTemplate<String, Integer> redisTemplate() {
        // RedisTemplate<Key,Value>
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Key를 String으로 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        // Value를 Integer로 직렬화
        template.setValueSerializer(new GenericToStringSerializer<>(Integer.class));

        return template;
    }
}