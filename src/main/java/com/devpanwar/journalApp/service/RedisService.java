package com.devpanwar.journalApp.service;

import com.devpanwar.journalApp.api.response.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

//    giving key and class in which we want response...making generic method to access all types of data
    public <T> T get(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            if (o!=null){
                log.info("sahi h", "o is not null");
                return mapper.readValue(o.toString(), entityClass);
            }else{
                log.error("dikkat", "o is null");
                return null;
            }

        } catch (Exception e) {
            log.error("Exception ", e);
            return null;
        }
    }
// giving object to save with key name and expiry time
    public void set(String key, Object o, Long ttl){
        try {
            ObjectMapper objectMapper=new ObjectMapper();
//            because we are serialising and deserialising as string in redis template implementation
            String jsonValue= objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,jsonValue,ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
    }
}
