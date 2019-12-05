package com.qy105.aaa.redis;

import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisCache implements Cache {
    /**
     * id 为mapper 的命名空间  com.aaa.mapper.BookMapper
     */
    private final String id;

    private RedisTemplate<String, Object> redisTemplate;

    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private static final long EXPIRE_TIME = 30;


    public RedisCache(String id) {
        if (null == id) {
            throw new IllegalArgumentException("Cache instance requires an id");
        }
        this.id = id;
    }

    /**
     * MyBatis缓存操作对象的标识符，一个mapper对象对应一个MyBatis缓存操作对象
     *
     * @return
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 将查出结果存入缓存
     * @param key
     * @param value
     */
    @Override
    public void putObject(Object key, Object value) {
        redisTemplate = getRedisTemplate();
        if (null != value) {
            try {
                redisTemplate.opsForValue().set(key.toString(), value, EXPIRE_TIME, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("");
            }
        } else {
            logger.info("value is null");
        }
    }

    /**
     * 从缓存中获取被缓存的查询结果。
     * @param key
     * @return
     */
    @Override
    public Object getObject(Object key) {
        redisTemplate = getRedisTemplate();
        if (null != key) {
            try {
                System.out.println("=============" + key.toString());
                Object o = redisTemplate.opsForValue().get(key.toString());
                return o;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("");
            }
        } else {
            logger.info("key is null");
        }
        return null;
    }

    /**
     * 从缓存中删除对应的key、value。只有在回滚时触发。 一般我们也可以不用实现
     *
     * @param key
     * @return
     */
    @Override
    public Object removeObject(Object key) {

        redisTemplate = getRedisTemplate();
        try {
            if (key != null) {
                redisTemplate.delete(key.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("");
        }
        return null;
    }

    /**
     * 发生更新时，清除缓存,因为是二级缓存，根据mapper id删除当前mapper
     */
    @Override
    public void clear() {
        redisTemplate = getRedisTemplate();
        Set<String> keys = redisTemplate.keys("*" + this.id + "*");
        try {
            redisTemplate.delete(keys);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("");
        }
    }

    /**
     * 获取缓存个数
     * @return
     */
    @Override
    public int getSize() {
        return getRedisTemplate().keys("*").size();
    }

    /**
     * 获取redisTemplate
     * @return
     */
    public RedisTemplate getRedisTemplate() {

        if (null == this.redisTemplate) {
            this.redisTemplate = ApplicationContextHolder.getBean("redisTemplate");
        }
        return redisTemplate;
    }
}
