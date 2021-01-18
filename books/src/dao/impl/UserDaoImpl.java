package dao.impl;

import dao.BaseDao;
import dao.UserDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import pojo.Order;
import pojo.User;
import utils.DBUtils;

import java.sql.Connection;

/**
 * @author lppppp
 * @create 2020-12-31 19:28
 */
@Repository
@Transactional
public class UserDaoImpl extends BaseDao<User> implements UserDao {

    @Override
    public User getUserByName(String name) {
        User user = null;
        String sql = "select * from t_user where username=?";
        try {
            user =  getInsance(sql, name);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return user;
        }
    }

    @Override
    public User getUserByNameAndPass(String name, String password) {
        String sql = "select * from t_user where username = ? and password = ?";
        User user = null;
        try {
            user = getInsance(sql,name,password);
        } catch (DataAccessException e) {
            System.out.println(e);
        } finally {
            return user;
        }

    }

    @Override
    public int saveUser(User user) {
        String sql = "insert into t_user(username,password,email)values(?,?,?)";
        int update = update(sql, user.getUsername(), user.getPassword(),user.getEmail());
        // 模拟事务
        int i = 10/0;
        if(update>0){
            System.out.println(" 保存成功 ...");
        }
        return update;
    }
}
