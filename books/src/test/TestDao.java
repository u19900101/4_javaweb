package test;

import dao.UserDao;

import java.lang.reflect.ParameterizedType;

/**
 * @author lppppp
 * @create 2021-01-18 21:03
 */

class AbstractDao<T> {
    Class<T> type;
    public AbstractDao(){
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        type = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        System.out.println(type);
    }
}

public class TestDao extends AbstractDao<Integer> {
    public static void main(String[] args) {
        TestDao userDao = new TestDao();

        // Class<String> actualType = userDao.getActualType();
        // System.out.println(actualType.getName());
    }
}


