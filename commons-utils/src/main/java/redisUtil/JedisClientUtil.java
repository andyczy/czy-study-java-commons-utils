package redisUtil;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;


/**
 * 单线程环境下使用，简单Util
 * 正常正式开发中，会把Jedis包装在一个单利模式中，避免每次都去重新连接，把localhost和port放到properties的配置文件中
 **/
public class JedisClientUtil {

    /**
     * 单线程环境下使用，简单 Util
     **/
    public static void simple() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("foot", "bar");
        String value = jedis.get("foot");
        //通过这种方式就可以直接使用redis里面的很多命令了
    }

//    单线程环境的正确使用姿势如下，但是在实际环境中，我们（1）里面的写法可能过于简单，真正再生产模式下，写法如下：
    @Value("{spring.redis.host}")
    private String host;
    @Value("{spring.redis.port}")
    private Integer port;

    private final byte[] temp_lock = new byte[1];
    private Jedis jedis;

    private JedisClientUtil() {
    }

    public Jedis getRedisClient() {
        if (jedis == null) {
            synchronized (temp_lock) {
                if (jedis == null) {
                    jedis = new Jedis(host, port);
                }
            }
        }
        return jedis;
    }

    public static void main(String[] args) {
//        @Autowired
//        JedisClientUtil jedisClientUtil;
//        如果在其他地方使用，直接Autowired即可。
        JedisClientUtil jedisClientUtil = new JedisClientUtil();
        Jedis jedis = jedisClientUtil.getRedisClient();
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

