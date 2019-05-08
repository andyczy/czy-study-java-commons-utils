package redisUtil;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.compress.utils.Sets;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.List;

/**
 * Author: Chen zheng you
 * CreateTime: 2019-05-08 17:20
 * Description:高可用连接（master/salve）
 */
public class JedisSentinelPoolUtil {


    //    （1）高可用场景 JedisSentinel
    public static void JedisSentinelPoolUtilTest(String[] args) {
        // Jedis 提供的哨兵模式的使用，我们都知道 Redis 支持 master 和 salve 模式，当发生故障的时候如何做专业。
        // 新版的 Redis 和 Jedis 已经做了很好的支持，来保证我们的 Reids 高可用，服务器端的配置这里忽略一下，我们看看 Jedis 的客户端下怎么写的。
        // 添加N个哨兵，当添加的时候，此时如果去看源码的化就会发现，顺带通过哨兵帮我们初始化了一个master连接地址
        JedisSentinelPool pool = new JedisSentinelPool("redis_master_name", Sets.newHashSet("127.0.0.1:63791", "127.0.0.1:63792"));
        //通过哨兵获得Master节点，如果有问题会重新通过哨兵获得一个Master节点
        Jedis jedis = pool.getResource();
        try {
            jedis.set("foot", "bar");
            String value = jedis.get("foot");
        } finally {
            //注意关闭
            jedis.close();
        }
    }

    //    （2）生产正确姿势
    //    和上面连接池的用法一样，也需要建立一个单利模式来获得 Pool，然后根据 Pool 对调用者提供 Jedis 的使用，此处不再重复叙述。
    //    客户端分片
    /**
     * 简单测试切片的写法
     */
    public static void main(String[] args) {
        List<JedisShardInfo> shards = Lists.newArrayList();
        shards.add(new JedisShardInfo("127.0.0.1", 6379));
        shards.add(new JedisShardInfo("127.0.0.1", 6378));
        //通过list可以创建N个切片
        ShardedJedisPool shardedJedisPool = new ShardedJedisPool(new GenericObjectPoolConfig(), shards);
        ShardedJedis shardedJedis = shardedJedisPool.getResource();
        shardedJedis.set("key1", "abc");
        System.out.println(shardedJedis.get("key1"));
    }
}
