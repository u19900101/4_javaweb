package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pojo.User;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lppppp
 * @create 2020-12-31 17:19
 *
 * 利用jdbcUtils 写通用的操作数据库方法
 */
/* 此处千万不可加 注解  扫描加入。。。。。。
*  需要new 的类才进行注入。。。。。*/
public class BaseDao<T> {
    // update insert delete操作
    public Class<T> type;
    @Autowired
    public JdbcTemplate jdbcTemplate;

    public int update(String sql,Object ...args){
        return jdbcTemplate.update(sql,args);
    }

    public BaseDao(){
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;
        type =  (Class<T>) parameterizedType.getActualTypeArguments()[0];
        System.out.println(genericSuperclass);
    }


    // select 操作
    // 单条查询

    public T getInsance(String sql,Object...args){
        T t = null;
        try {
            t = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(type),args);
            return t;
        } catch (Exception e) {
            System.out.println(e);
        }
        return t;
    }

    // 多条查询
    public List<T> getInsanceList(String sql,Object...args){

        try {
            return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(type),args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 特殊值查询
    public Object getSingleValue(String sql,Object object,Object...args){

        try {
            Object value = jdbcTemplate.queryForObject(sql, Object.class,args);
            return  value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
