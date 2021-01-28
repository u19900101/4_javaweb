package ppppp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppppp.dao.impl.OrderDaoImpl;
import ppppp.pojo.Order;
import ppppp.pojo.OrderItem;
import ppppp.service.OrderService;

/**
 * @author lppppp
 * @create 2021-01-08 15:05
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDaoImpl orderDao;
    @Override
    public void createOrder(Order order) {
        orderDao.createOrder(order);
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        orderDao.createOrderItem(orderItem);
    }
}
