package ppppp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppppp.dao.impl.OrderDaoImpl;
import ppppp.g_dao.OrderItemMapper;
import ppppp.g_dao.OrderMapper;
import ppppp.pojo.Order;
import ppppp.pojo.OrderExample;
import ppppp.pojo.OrderItem;
import ppppp.pojo.OrderItemExample;
import ppppp.service.OrderService;

import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-08 15:05
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDaoImpl orderDao;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Override
    public void createOrder(Order order) {
        orderDao.createOrder(order);
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        orderDao.createOrderItem(orderItem);
    }

    @Override
    public List<Order> selectOrderByExample(OrderExample orderExample) {
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public List<OrderItem> selectOrderItemByExample(OrderItemExample orderItemExample) {
        return orderItemMapper.selectByExample(orderItemExample);
    }
}
