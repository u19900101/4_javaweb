package ppppp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppppp.dao.OrderDao;
import ppppp.generate_bean.Order;
import ppppp.generate_bean.Orderitem;
import ppppp.service.OrderService;

/**
 * @author lppppp
 * @create 2021-01-08 15:05
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Override
    public void createOrder(Order order) {
        orderDao.createOrder(order);
    }

    @Override
    public void createOrderItem(Orderitem orderItem) {
        orderDao.createOrderItem(orderItem);
    }
}
