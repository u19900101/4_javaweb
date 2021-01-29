package ppppp.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ppppp.g_dao.CartMapper;
import ppppp.g_dao.CartitemMapper;
import ppppp.pojo.*;
import ppppp.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-07 21:45
 */
@Controller
@RequestMapping("/client/cartServlet")
public class CartServlet{
    @Autowired
    BookServiceImpl bookService;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartitemMapper cartitemMapper;

    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       Model model,HttpServletRequest req){
        System.out.println("come into cartServlet...");
        PageHelper.startPage(pageNum, 4);
        //紧跟着的第一条查询语句才有用  后面的无分页功能
        List<Cartitem> cartitemList = cartitemMapper.selectByExample(new CartitemExample());
        //传入要连续显示多少页
        PageInfo<Cartitem> info = new PageInfo<>(cartitemList, 5);
        model.addAttribute("info", info);

        User user = (User) req.getSession().getAttribute("user");
        Integer cartId = user.getId();
        Cart cart = cartMapper.selectByPrimaryKey(cartId);

        model.addAttribute("url", "client/cartServlet/page?");
        model.addAttribute("totalCount", cart.getCount());
        model.addAttribute("totalPrice", cart.getTotalprice());
        System.out.println(info);
        return "forward:/pages/cart/cart.jsp";
    }


    @RequestMapping("/updateCount")
    protected void updateCount(HttpServletRequest req, HttpServletResponse res,Integer id,Integer count) throws ServletException, IOException {
        System.out.println("come into updateCount");
        User user = (User) req.getSession().getAttribute("user");
        Cart cart = cartMapper.selectByPrimaryKey(user.getId());

        // if(cart.getItems().get(id).getCount()<count){
        //     req.getSession().setAttribute("lastAddBook",cart.getItems().get(id).getName());
        // }
        // cart.updateCount(id,count);
        res.sendRedirect(req.getHeader("Referer"));

    }
    @RequestMapping("/clearCart")
    protected void clearCart(HttpServletRequest req, HttpServletResponse res,Integer cartid) throws ServletException, IOException {
        //1.先清空 cartItem中的项
        CartitemExample cartitemExample = new CartitemExample();
        CartitemExample.Criteria criteria = cartitemExample.createCriteria();
        criteria.andCartidEqualTo(cartid);
        int deleteByExample = cartitemMapper.deleteByExample(cartitemExample);
        System.out.println("删除了 cartItem "+deleteByExample);
        //2.再删除 cart
        int i = cartMapper.deleteByPrimaryKey(cartid);
        System.out.println("删除了 cart"+i);
        res.sendRedirect(req.getHeader("Referer"));
    }

    @RequestMapping("/deleteItem")
    protected void deleteItem(HttpServletRequest req, HttpServletResponse res,Integer cartItemid) throws ServletException, IOException {
        System.out.println("come into deleteItem");
        cartitemMapper.deleteByPrimaryKey(cartItemid);
        // Book book = bookService.queryBookById(id);
        // book.setStock(book.getStock()-1);
        // bookService.updateBookById(book);
        res.sendRedirect(req.getHeader("Referer"));
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    protected String addItem(HttpServletRequest req, Integer bookId){
        System.out.println("come into addItem");

        User user = (User) req.getSession().getAttribute("user");
        Integer cartId = user.getId();
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        boolean flag = false;
        // 判断是否有购物车
        // 无购物车则创建
        if(cart== null){
            cart =new Cart(cartId);
            cartMapper.insert(cart);
            req.getSession().setAttribute("cart",cart);
            flag = true;
        }

        //将商品单项加入购物车
        Book book = bookService.queryBookById(bookId);

        // 查看购物车中是否有该商品 若无 则添加  有 则将数量相加
        Cartitem cartitem = cartitemMapper.selectByPrimaryKey(bookId);
        if(cartitem!= null){
            cartitem.setCount(cartitem.getCount()+1);
            cartitemMapper.updateByPrimaryKey(cartitem);
        }else {
            cartitem = new Cartitem(bookId,book.getName(),Integer.parseInt("1"),
                    book.getPrice(),
                    book.getPrice().multiply(new BigDecimal(1)),
                    cartId);
            cartitemMapper.insert(cartitem);
        }

        // 修改购物车中的商品数量和总价
        cart.setCount(cart.getCount()+1);
        cart.setTotalprice(cart.getTotalprice().add(book.getPrice()));
        cartMapper.updateByPrimaryKey(cart);

        req.getSession().setAttribute("totalCount",cart.getCount());
        req.getSession().setAttribute("lastAddBook",book.getName());


        // 使用ajax进行 简化
        HashMap<String, Object> map = new HashMap<>();
        map.put("totalCount", cart.getCount());
        map.put("lastAddBook",book.getName());
        // 判断是否是第一次加入购物车
        map.put("createCart", flag);
        return new Gson().toJson(map);
    }
}
