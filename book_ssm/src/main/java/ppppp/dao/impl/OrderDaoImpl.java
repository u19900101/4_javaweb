package ppppp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ppppp.g_dao.OrderItemMapper;
import ppppp.g_dao.OrderMapper;
import ppppp.pojo.Order;
import ppppp.pojo.OrderItem;

/**
 * @author lppppp
 * @create 2021-01-08 15:15
 */
@Repository
public class OrderDaoImpl{
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderitemMapper;
  
    public void createOrder(Order order) {
        int update = orderMapper.insert(order);
        if(update>0){
            System.out.println("添加了订单");
        }
    }

  
    public void createOrderItem(OrderItem orderItem) {

        int update = orderitemMapper.insert(orderItem);
        if(update>0){
            System.out.println("添加了订单Item");
        }
    }
}
