package ppppp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ppppp.dao.UserDao;
import ppppp.generate_bean.User;
import ppppp.generate_bean.UserExample;
import ppppp.generate_dao.UserMapper;

import java.util.List;

/**
 * @author lppppp
 * @create 2020-12-31 19:28
 */
@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @Autowired
    UserMapper userMapper;
    @Override
    public ppppp.generate_bean.User getUserByName(String name) {
        List<ppppp.generate_bean.User> user = null;
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

    @Override
    public ppppp.generate_bean.User getUserByNameAndPass(String name, String password) {
        List<ppppp.generate_bean.User> user = null;
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

    @Override
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
