package com.wwb.mkeditor.dao.redisdao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis操作Hash数据结构
 * @author: wwb
 */
@Component
public class RedisHashDao extends RedisStringDao {
    /**
     * 查询在Hash中是否存在
     * @param key
     * @param hashKey
     * @return
     */
    public boolean hasKeyInHash(String key,String hashKey)
    {
        return stringRedisTemplate.opsForHash().hasKey(key,hashKey);
    }
    public void put(String key,String hashKey,String hashValue)
    {
        stringRedisTemplate.opsForHash().put(key,hashKey,hashValue);
    }
    public String get(String key,String hashKey)
    {
        return (String) stringRedisTemplate.opsForHash().get(key,hashKey);
    }
}
