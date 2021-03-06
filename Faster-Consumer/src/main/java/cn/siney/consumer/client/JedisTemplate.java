package cn.siney.consumer.client;

import cn.siney.consumer.config.ParamsConfig;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTemplate {

    private int port;
    private String hostname;
    private JedisPool jedisPool;

    private static JedisTemplate instance = new JedisTemplate();

    public static JedisTemplate getInstance(){
        return instance;
    }

    private JedisTemplate(){
        ParamsConfig paramsConfig = ParamsConfig.getInstance();
        hostname = paramsConfig.getRedisHostname();
        port = paramsConfig.getRedisPort();
        createJedisPool();
    }

    private void createJedisPool() {
        JedisPoolConfig conf = new JedisPoolConfig();
        conf.setMaxTotal(1024);
        conf.setMaxIdle(100);
        conf.setMaxWaitMillis(100);
        conf.setTestOnBorrow(false);//jedis 第一次启动时，会报错
        conf.setTestOnReturn(true);
        jedisPool = new JedisPool(conf, hostname, port, 5000);
    }

    public void set(String key, String value){
        jedisPool.getResource().set(ParamsConfig.REGISTRY + key, value);
    }

    public String get(String key){
        return jedisPool.getResource().get(ParamsConfig.REGISTRY + key);
    }

}
