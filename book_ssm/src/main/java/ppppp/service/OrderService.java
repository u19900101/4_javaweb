package ppppp.service;

import ppppp.pojo.Order;
import ppppp.pojo.OrderItem;

/**
 * @author lppppp
 * @create 2021-01-08 15:00
 */
public interface OrderService {
//    生成订单
     void createOrder(Order order);

    void createOrderItem(OrderItem orderItem);
}