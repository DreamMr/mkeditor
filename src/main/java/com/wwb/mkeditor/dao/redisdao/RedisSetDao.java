package com.wwb.mkeditor.dao.redisdao;

import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author :wwb
 */
@Component
public class RedisSetDao extends RedisStringDao{
    /**
     * 添加元素
     * @param setKey
     * @param uid
     * @return
     */
    public boolean addElement(String setKey,String uid){
        if(stringRedisTemplate.opsForSet().isMember(setKey,uid)){
            return false;
        }
        stringRedisTemplate.opsForSet().add(setKey,uid);
        return true;
    }

    /**
     * 判断uid是否已经存在在setKey中了！
     * @param setKey
     * @param uid
     * @return
     */
    public boolean isMember(String setKey,String uid){
        if(stringRedisTemplate.opsForSet().isMember(setKey,uid)){return true;}
        else {return false;}
    }
    /**
     * 如果没有会返回0
     * @param key
     * @return
     */
    public Set<String> getAll(String key){
        return stringRedisTemplate.opsForSet().members(key);
    }
}
