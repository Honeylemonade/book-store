package com.xyp.backendbookstore.util;

import com.xyp.backendbookstore.dao.BookDao;
import com.xyp.backendbookstore.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * RedisUtil:缓存内容只进行书籍相关信息的缓存
 *
 * @Author XvYanpeng
 * @Date 2019/12/1 11:56
 */
@Component
public class RedisUtil {
    @Autowired
    BookDao bookDao;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @Author XvYanpeng
     * @Description 将数据库中的数据更新到缓存中
     * @Date 2019/12/1 11:57
     */
    public void refresh() {
        //配置redis的KV序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Book.class));
        ArrayList<Book> bookArrayList = bookDao.findAll();
        for (Book book : bookArrayList) {
            redisTemplate.opsForValue().set(book.getBookId().toString(), book);
        }
    }

    public ArrayList<Book> findAll() {
        //配置redis的KV序列化器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Book.class));
        ArrayList<Book> bookArrayList = new ArrayList<>();
        //获取全部key并按照书籍id排序
        Set<String> keys = redisTemplate.keys("*");
        TreeSet<String> treeSet=new TreeSet(keys);
        for (String key : treeSet) {
            bookArrayList.add((Book) redisTemplate.opsForValue().get(key));
        }
        return bookArrayList;
    }
}
