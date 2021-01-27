package ppppp.dao;


import ppppp.generate_bean.User;

/**
 * @author lppppp
 * @create 2020-12-31 19:30
 */
public interface UserDao {
    ppppp.generate_bean.User getUserByName(String name);
    ppppp.generate_bean.User getUserByNameAndPass(String name, String password);
    int saveUser(User user);
}
