package com.wegoo.utils;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * @author 二师兄超级帅
 * @Title: RedisUtil
 * @ProjectName bootdo
 * @Description: TODO
 * @date 2018/11/811:52
 */
public class RedisUtil {
    public static void del(String reg) {
        Jedis jedis = new Jedis();
        try {
            Set<String> set = jedis.keys("*" + reg + "*");

            Iterator<String> it = set.iterator();

            while (it.hasNext()) {
                String keyStr = it.next();
                jedis.del(keyStr);

            }

        } catch (
                Exception e) {

            e.printStackTrace();

        } finally {

            if (jedis != null) {
                jedis.close();
            }

        }


    }

}
