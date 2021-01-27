package ppppp.dao;


import ppppp.generate_bean.Order;
import ppppp.generate_bean.Orderitem;


/**
 * @author lppppp
 * @create 2021-01-08 15:14
 */
public interface OrderDao {
    void createOrder(Order order);

    void createOrderItem(Orderitem orderItem);
}
