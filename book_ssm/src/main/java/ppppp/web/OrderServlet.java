package ppppp.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ppppp.g_dao.OrderItemMapper;
import ppppp.g_dao.OrderMapper;
import ppppp.pojo.*;
import ppppp.service.impl.BookServiceImpl;
import ppppp.service.impl.OrderServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ppppp.pojo.Status.CHECKEDRECEIVED;

/**
 * @author lppppp
 * @create 2021-01-08 15:07
 */
@Controller
@RequestMapping("/client/orderServlet")
public class OrderServlet{
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    BookServiceImpl bookService;


    public String gId(){
        String replace = LocalDateTime.now().toString().replace("T", "_");
        String replace1 = replace.replace("-", "_");
        String replace2 = replace1.replace(".", "_");
        String replace3= replace2.replace(":", "_");
        return replace3;
    }
    @RequestMapping("/createOrder")
    protected String createOrder(HttpServletRequest req){
        // 1.将购物车里的东西转化为orderItem 以及 order
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        User user = (User)req.getSession().getAttribute("user");

        if(user == null){
            return "redirect:/pages/user/login.jsp";
        }
        String id = user.getUsername()+"_"+gId();
        req.getSession().setAttribute("orderId", id);
        Order order = new Order(id, LocalDateTime.now().toString(),cart.getTotalCount(), cart.getTotalPrice(), user.getId(), CHECKEDRECEIVED);
        // 2.创建订单
        orderService.createOrder(order);
        // 2.创建订单详情
        Map<Integer, CartItem> items = cart.getItems();
        for (CartItem cartItem:items.values()) {
            OrderItem orderItem = new OrderItem(cartItem.getName(), cartItem.getCount(),
                    cartItem.getPrice(),order.getId());
            orderService.createOrderItem(orderItem);
        //    更新库存和销量
            Book book = bookService.queryBookById(cartItem.getId());
            book.setSales(book.getSales()+cartItem.getCount());
            book.setStock(book.getStock()-cartItem.getCount());
            bookService.updateBookById(book);
        }
        // 3.类似清空了购物车车
        req.getSession().removeAttribute("cart");
        return "redirect:/pages/cart/checkout.jsp";
    }

    @RequestMapping("/page")
    protected String getAllOrder(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                 HttpServletRequest req){
        PageHelper.startPage(pageNum, 5);
        //紧跟着的第一条查询语句才有用  后面的无分页功能

        //传入要连续显示多少页
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("create_time DESC");
        List<Order> orderList = orderMapper.selectByExample(orderExample);
        PageInfo<Order> info = new PageInfo<>(orderList, 5);
        req.setAttribute("info", info);
        // 带上当前的权限 路径  以便分页 区分跳转前缀
        req.setAttribute("url", "client/orderServlet/page?");
        return "forward:/pages/order/order.jsp";
    }

    @RequestMapping("/getOrderItem")
    protected String getOrderItem(String orderId,
                                  HttpServletRequest req,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        PageHelper.startPage(pageNum, 5);
        //紧跟着的第一条查询语句才有用  后面的无分页功能

        OrderItemExample orderItemExample = new OrderItemExample();
        OrderItemExample.Criteria criteria = orderItemExample.createCriteria();

        criteria.andOrderIdLike(orderId);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        PageInfo<OrderItem> info = new PageInfo<>(orderItems, 5);
        req.setAttribute("info", info);

        // 带上当前的权限 路径  以便分页 区分跳转前缀
        req.setAttribute("url", "client/orderServlet/getOrderItem?orderId="+orderId+"&");

        return "forward:/pages/order/orderItem.jsp";
    }

}
