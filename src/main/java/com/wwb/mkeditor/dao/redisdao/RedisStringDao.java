package com.wwb.mkeditor.dao.redisdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 操作Redis字符串类型
 * @author:wwb
 */
@Component
public class RedisStringDao implements RedisDao {
    @Autowired
    protected StringRedisTemplate stringRedisTemplate;
    @Override
    public boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate.expire(key,timeout,timeUnit);
    }

    /**
     * 添加值
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    synchronized public void addValue(String key,String value,long timeout,TimeUnit timeUnit){
        stringRedisTemplate.opsForValue().set(key,value,timeout,timeUnit);
    }

    synchronized public boolean deleteKey(String key){
       try{
           if(hasKey(key)){
               stringRedisTemplate.delete(key);
           }
           return true;
       }catch (Exception e){
           return false;
       }
    }
    /**
     * 获取值
     * @param key
     * @return
     */
    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

}
