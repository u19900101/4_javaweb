package ppppp.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ppppp.pojo.Book;
import ppppp.pojo.Cart;
import ppppp.pojo.CartItem;
import ppppp.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lppppp
 * @create 2021-01-07 21:45
 */
@Controller
@RequestMapping("/client/cartServlet")
public class CartServlet{
    @Autowired
    BookServiceImpl bookService;
    @RequestMapping("/updateCount")
    protected void updateCount(HttpServletRequest req, HttpServletResponse res,Integer id,Integer count) throws ServletException, IOException {
        System.out.println("come into updateCount");
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart.getItems().get(id).getCount()<count){
            req.getSession().setAttribute("lastAddBook",cart.getItems().get(id).getName());
        }
        cart.updateCount(id,count);
        res.sendRedirect(req.getHeader("Referer"));

    }
    @RequestMapping("/clearCart")
    protected void clearCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        cart.clear();
        res.sendRedirect(req.getHeader("Referer"));
    }

    @RequestMapping("/deleteItem")
    protected void deleteItem(HttpServletRequest req, HttpServletResponse res,Integer id) throws ServletException, IOException {
        System.out.println("come into deleteItem");
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        cart.deleteItem(id);
        Book book = bookService.queryBookById(id);
        book.setStock(book.getStock()-1);
        bookService.updateBookById(book);
        res.sendRedirect(req.getHeader("Referer"));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    protected String addItem(HttpServletRequest req, Integer id){
        System.out.println("come into addItem");

        Book book = bookService.queryBookById(id);

        CartItem cartItem =new CartItem(id,book.getName(),1,book.getPrice(),book.getPrice().multiply(new BigDecimal(1)));
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        boolean flag = false;
        // 判断是否有购物车
        // 无购物车则创建
        if(cart== null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
            flag = true;
        }
        cart.addItem(cartItem);
        req.getSession().setAttribute("totalCount",cart.getTotalCount());
        req.getSession().setAttribute("lastAddBook",book.getName());

        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> itemEntry : items.entrySet()) {
            System.out.println(itemEntry.getKey() + "  "+ itemEntry.getValue());
        }
        // 使用ajax进行 简化
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalCount", cart.getTotalCount());
        map.put("lastAddBook",book.getName());
        // 判断是否是第一次加入购物车
        map.put("createCart", flag);
        return new Gson().toJson(map);

    }
}
