package ppppp.web;

import org.springframework.beans.factory.annotation.Autowired;
import ppppp.generate_bean.Order;
import ppppp.generate_bean.Orderitem;
import ppppp.generate_bean.User;
import ppppp.pojo.Book;
import ppppp.pojo.Cart;
import ppppp.pojo.CartItem;
import ppppp.service.BookService;
import ppppp.service.OrderService;
// import ppppp.utils.WebUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import static ppppp.pojo.Status.CHECKEDRECEIVED;

/**
 * @author lppppp
 * @create 2021-01-08 15:07
 */
@WebServlet("/client/orderServlet")
public class OrderServlet{
    @Autowired
    OrderService orderService;
    @Autowired
    BookService bookService;
    protected void createOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 1.将购物车里的东西转化为orderItem 以及 order
        Cart cart = (Cart)req.getSession().getAttribute("cart");
        User user = (User)req.getSession().getAttribute("user");

        if(user == null){
            req.getRequestDispatcher("/pages/user/login.jsp");
            return;
        }
        String id = user.getUsername()+"_"+String.valueOf(System.currentTimeMillis());
        req.getSession().setAttribute("orderId", id);
        Order order = new Order(id, LocalDateTime.now().toString(),cart.getTotalCount(), cart.getTotalPrice(), user.getId());
        // 2.创建订单
        orderService.createOrder(order);
        // 2.创建订单详情
        Map<Integer, CartItem> items = cart.getItems();
        for (CartItem cartItem:items.values()) {
            Orderitem orderItem = new Orderitem(cartItem.getName(), cartItem.getCount(),
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

        res.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
    }

}
