package redisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import org.springframework.beans.factory.annotation.Value;

/**
 * Author: Chen zheng you
 * CreateTime: 2019-05-08 17:16
 * Description:连接池的使用、多线程环境的正确使用姿势
 */
public class JedisClientPoolUtil {

    // 一般正常工作中很少有单线程模式，在 Web 环境下都是多线程进行的，这个时候引入连接池的概念来帮我们管理各个连接。
    // 简单概念提一下，引入连接池是为了管理连接对象，也就是 Jedis 对象可能要从一个池里面取，所以 Jedis 提供了 JedisPool 的类。
    public static void JedisPoolTest(String[] args) {
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1", 6379);
        Jedis jedis = pool.getResource();
        try {
            jedis.set("foot", "bar");
            String value = jedis.get("foot");
            System.out.println(value);
        } finally {
            //注意关闭
            jedis.close();
        }
    }


    /**
     * 多线程环境下，线程池的正确使用方法，单例的连接池，单例的配置。
     * 此处给大家提供一个种思路，如果用spring boot的话，可以基于@Configuration 和@Bean的配置方法，此处仅仅是举例说明。
     **/
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;

    private final static byte[] temp_lock = new byte[1];
    private JedisPool jedisPool;

    /**
     * 把连接池做成单例的，这点需要注意
     *
     * @return
     */
    private JedisPool getJedisPool() {
        if (jedisPool == null) {
            synchronized (temp_lock) {
                if (jedisPool == null) {
                    jedisPool = new JedisPool(jedisPoolConfig(), host, port);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 设置一些连接池的配置，来管理每一个连接。
     *
     * @return
     */
    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxWaitMillis(1000);
        return jedisPoolConfig;
    }

    /**
     * 对外只暴露这一个方法即可
     *
     * @return
     */
    public Jedis getJedis() {
        return getJedisPool().getResource();
    }

    public static void main(String[] args) {
//        @Autowired
//        JedisClientPoolUtil jedisClientPoolUtil;
//        如果在其他地方使用，直接Autowired即可。
        JedisClientPoolUtil jedisClientPoolUtil = new JedisClientPoolUtil();
        Jedis jedis = jedisClientPoolUtil.getJedis();
        try {
            jedis.set("foot", "bar");
            String value = jedis.get("foot");
            System.out.println(value);
        } finally {
            //注意关闭

            jedis.close();
        }
    }

}
