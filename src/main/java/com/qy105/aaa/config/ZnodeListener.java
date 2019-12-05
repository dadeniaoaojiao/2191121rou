package com.qy105.aaa.config;

import com.alibaba.druid.util.StringUtils;
import com.qy105.aaa.redis.ApplicationContextHolder;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ZnodeListener {
    private ZooKeeper zooKeeper;

    public ZnodeListener(){
        try {
            zooKeeper= new ZooKeeper("192.168.6.122:2181,192.168.6.122:2182,192.168.6.122:2183", 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("envent:"+watchedEvent);

                    try {
                        String rdisIps = new String(zooKeeper.getData("/Redis",true,null));
                        System.out.println("rdisIps:"+rdisIps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    }
                }
            });
            listenerZookeeper();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听zookeeper，并获取node上的数据
     */
    private void listenerZookeeper() {
        try {
            String rdisIps= new String(zooKeeper.getData("/Redis",true,null));
            System.out.println("rdisIps:"+rdisIps);
            resetRedis(rdisIps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    //重现配置redis连接
    private void resetRedis(String ips) {
        if (!StringUtils.isEmpty(ips) & ips.contains(":")){
            String[] res = ips.split(":");
            RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextHolder.getBean("redisTemplate");
            StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) ApplicationContextHolder.getBean("stringRedisTemplate");

            JedisConnectionFactory jedisConnectionFactory = redisConnectionFactory(res[0],Integer.valueOf(res[1]));

            redisTemplate.setConnectionFactory(jedisConnectionFactory);
            stringRedisTemplate.setConnectionFactory(jedisConnectionFactory);
        }
    }

    /* 创建jedis连接工厂
     */
    public JedisConnectionFactory redisConnectionFactory(String ip,int port) {
        JedisConnectionFactory jcf=new JedisConnectionFactory();
        jcf.setHostName(ip);
        jcf.setPort(port);
        return  jcf;
    }
}