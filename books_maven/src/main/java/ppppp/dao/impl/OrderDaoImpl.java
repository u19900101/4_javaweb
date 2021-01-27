package ppppp.dao.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ppppp.dao.OrderDao;
import ppppp.generate_bean.Order;
import ppppp.generate_bean.Orderitem;
import ppppp.generate_dao.OrderMapper;
import ppppp.generate_dao.OrderitemMapper;
/**
 * @author lppppp
 * @create 2021-01-08 15:15
 */
@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderitemMapper orderitemMapper;
    @Override
    public void createOrder(Order order) {
        int update = orderMapper.insert(order);
        if(update>0){
            System.out.println("添加了订单");
        }
    }

    @Override
    public void createOrderItem(Orderitem orderItem) {

        int update = orderitemMapper.insert(orderItem);
          if(update>0){
            System.out.println("添加了订单Item");
        }
    }
}
