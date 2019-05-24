package com.wwb.mkeditor.dao.redisdao;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 操作Redis的接口
 * @author wwb
 */
@Component
public interface RedisDao {
    /**
     * 判断key是否存在在Redis中
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 设置键值中超时日期
     * @param key  键
     * @param timeout  超时时间
     * @param timeUnit  DAYS
     * @return
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);
}
