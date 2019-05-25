package com.wwb.mkeditor.utils;

import com.wwb.mkeditor.dao.redisdao.RedisSetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UUID {
    @Autowired
    RedisSetDao redisSetDao=new RedisSetDao();
    static String key="uuid";
    public  String getUUID(){
        while(true){
            String uuid= java.util.UUID.randomUUID().toString().replace("-","").substring(0,8);
            if(redisSetDao.addElement(key,uuid)){
                return uuid;
            }
        }
    }
}
