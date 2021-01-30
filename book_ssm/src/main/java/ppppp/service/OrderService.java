package ppppp.service;

import ppppp.pojo.Order;
import ppppp.pojo.OrderExample;
import ppppp.pojo.OrderItem;
import ppppp.pojo.OrderItemExample;

import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-08 15:00
 */
public interface OrderService {
//    生成订单
     void createOrder(Order order);

    void createOrderItem(OrderItem orderItem);

    List<Order> selectOrderByExample(OrderExample orderExample);

    List<OrderItem> selectOrderItemByExample(OrderItemExample orderItemExample);
}
