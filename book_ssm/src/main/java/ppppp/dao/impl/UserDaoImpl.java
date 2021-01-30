package ppppp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ppppp.g_dao.UserMapper;
import ppppp.pojo.User;
import ppppp.pojo.UserExample;

import java.util.List;

/**
 * @author lppppp
 * @create 2020-12-31 19:28
 */
@Repository
public class UserDaoImpl{
    @Autowired
    UserMapper userMapper;
  
    public User getUserByName(String name) {
        List<User> user = null;
        try {
            UserExample userExample = new UserExample();
            // 创建一个查询准则
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andUsernameEqualTo(name);
            user =  userMapper.selectByExample(userExample);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return user.get(0);
        }
    }

  
    public User getUserByNameAndPass(String name, String password) {
        List<User> user = null;
        try {
            UserExample userExample = new UserExample();
            // 创建一个查询准则
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andUsernameEqualTo(name);
            criteria.andPasswordEqualTo(password);
            user =  userMapper.selectByExample(userExample);
        }catch (Exception e){
            System.out.println(e);
        }finally {
            return user.get(0);
        }

    }

  
    public int saveUser(User user) {
        int insert = userMapper.insert(user);

        // 模拟事务
        int i = 10/0;
        if(insert>0){
            System.out.println(" 保存成功 ...");
        }
        return insert;
    }
}
