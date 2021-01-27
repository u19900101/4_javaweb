package ppppp.service;

import ppppp.generate_bean.Order;
import ppppp.generate_bean.Orderitem;

/**
 * @author lppppp
 * @create 2021-01-08 15:00
 */
public interface OrderService {
//    生成订单
     void createOrder(Order order);

    void createOrderItem(Orderitem orderItem);
}
