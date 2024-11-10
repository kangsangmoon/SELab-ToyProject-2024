package com.example.project.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Long> redisTemplate;

    public void saveToken(String token, Long userId){
        redisTemplate.opsForValue().set(token, userId);
    }

    public Long getUserIdByToken(String token){
        return redisTemplate.opsForValue().get(token);
    }

    public void deleteToken(String token){
        redisTemplate.delete(token);
    }
}
