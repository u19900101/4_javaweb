package ppppp.service;

import ppppp.pojo.User;

/**
 * @author lppppp
 * @create 2020-12-31 19:56
 */
public interface UserService {
    boolean register(User user);
    User login(User user);
    boolean existUsername(String username);
}