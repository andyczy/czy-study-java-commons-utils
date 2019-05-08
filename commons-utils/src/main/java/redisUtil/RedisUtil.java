package redisUtil;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Author: Chen zheng you
 * CreateTime: 2019-04-18 10:51
 * Description:
 */
public class RedisUtil {



    private static RedisTemplate redisTemplate;
    private static StringRedisTemplate stringRedisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 写入缓存设置时效时间 单位：秒
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value, long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    public static void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public static void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     * @param key
     */
    public static void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public static Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 批量读取缓存
     * @param keyList
     * @return
     */
    public static List<Object> multiGet(final List<String> keyList) {
        List<Object> result = null;
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        result = operations.multiGet(keyList);
        return result;
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public static void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希 添加 MAP 对象
     * @param key
     * @param map
     */
    public static void hmSet(String key, Map<String, Object> map){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
    }

    /**
     * 根据key 获取hashMap
     * @param key
     * @return
     */
    public static Map<Object, Object> hmEntries(String key){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    public static List<Object> hmValues(String key){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.values(key);
    }

    /**
     * 增加hashMap中hashKey字段对应的值---long
     * @param key
     * @param hashKey
     * @param num
     * @return
     */
    public static Long increment(String key, String hashKey, long num) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.increment(key, hashKey, num);
    }

    /**
     * 增加hashMap中hashKey字段对应的值--double
     * @param key
     * @param hashKey
     * @param num
     * @return
     */
    public static Double increment(String key, String hashKey, double num) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.increment(key, hashKey, num);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public static Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 根据给定的keyList，哈希获取数据集合
     * @param key
     * @param keyList
     * @return
     */
    public static List<Object> hmGet(String key, List<Object> keyList){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.multiGet(key, keyList);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public static void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public static List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public static void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public static Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public static void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合批量添加
     * @param key
     * @param typedTupleSet
     */
    public static void zAddBatch(String key, Set<ZSetOperations.TypedTuple<Object>> typedTupleSet){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key, typedTupleSet);
    }

    /**
     * 返回指定区间集合，升序，闭区间
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> range(String key,long start,long end){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.range(key, start, end);
    }

    /**
     * 有序集合获取，按照Score升序排序获取
     * @param key
     * @param score
     * @param score1
     * @return
     */
    public static Set<Object> rangeByScore(String key,double score,double score1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, score, score1);
    }

    /**
     * 返回指定区间集合，降序。闭区间
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> reverseRange(String key,long start,long end){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.reverseRange(key, start, end);
    }

    /**
     * 增加分数
     * @param key
     * @param value
     * @param score
     * @return
     */
    public static Double incrementScore(String key, Object value, double score) {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.incrementScore(key, value, score);
    }

    /**
     * JSON 字符串形式写入redis
     * @param key
     * @param value
     * @return
     */
    public static boolean setString(final String key,Object value) {
        boolean result = false;
        try {
            ValueOperations<String, String> valueOperate = stringRedisTemplate.opsForValue();
            valueOperate.set(key, JSON.toJSONString(value));
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        return result;
    }

    /**
     * JSON 字符串形式写入redis,带时间
     * @param key
     * @param value
     * @return
     */
    public static boolean setString(final String key, Object value, long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, String> valueOperate = stringRedisTemplate.opsForValue();
            valueOperate.set(key, JSON.toJSONString(value));
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取JSON字符串
     * @param key
     * @return
     */
    public static String getString(final String key) {
        String result = null;
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 获取JSON字符串反序列化对象
     * @param key
     * @return
     */
    public static Object getStringObject(final String key, Class clazz) {
        Object result = null;
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String value = operations.get(key);
            result = JSON.parseObject(value, clazz);
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
        return result;
    }

    public static void batchCacheMarketInfo(HashMap<String, HashMap<Integer, Double>> dateMap) {
        //使用pipeline方式
        redisTemplate.executePipelined(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                int reCoed = 1;
                try {
                    for (Map.Entry<String,HashMap<Integer, Double>> entry : dateMap.entrySet()) {
                        //序列化zSet的key
                        byte[] rawKey = redisTemplate.getKeySerializer().serialize(entry.getKey());
                        for (Map.Entry<Integer, Double> subEntry : entry.getValue().entrySet()) {
                            //序列化zSet的value即marketId
                            byte[] rawValue = redisTemplate.getStringSerializer().serialize(subEntry.getKey().toString());
                            connection.zSetCommands().zAdd(rawKey, subEntry.getValue(), rawValue);
                        }
                    }
                } catch (SerializationException e) {
                    reCoed = -1;
                    e.printStackTrace();
                }
                return null;
            }
        });

    }

}
