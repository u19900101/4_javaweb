package ppppp.g_dao;

import ppppp.pojo.Order;
import ppppp.pojo.OrderItem;

/**
 * @author lppppp
 * @create 2021-01-08 15:14
 */
public interface OrderDao {
    void createOrder(Order order);

    void createOrderItem(OrderItem orderItem);
}
