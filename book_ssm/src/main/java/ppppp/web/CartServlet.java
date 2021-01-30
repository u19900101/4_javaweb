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
        if(cart!=null){
            model.addAttribute("totalCount", cart.getCount());
            model.addAttribute("totalPrice", cart.getTotalprice());
        }
        System.out.println(info);
        return "forward:/pages/cart/cart.jsp";
    }


    @RequestMapping("/updateCount")
    protected void updateCount(HttpServletRequest req, HttpServletResponse res,
                               Integer cartItemid,Integer count) throws ServletException, IOException {
        System.out.println("come into updateCount");
        Cartitem cartitem = cartitemMapper.selectByPrimaryKey(cartItemid);
        int orgCount = cartitem.getCount();
        //1.更新 cartItem
        cartitem.setCount2(count);// 更新数量时一并更新了总价
        cartitemMapper.updateByPrimaryKey(cartitem);

        //2.更新cart 中的count 和 total
        User user = (User) req.getSession().getAttribute("user");
        Cart cart = cartMapper.selectByPrimaryKey(user.getId());
        // 加上数量改变的值
        cart.setCount(cart.getCount()-orgCount+count);
        BigDecimal change = cartitem.getPrice().multiply(new BigDecimal(-orgCount+count));
        cart.setTotalprice(cart.getTotalprice().add(change));
        cartMapper.updateByPrimaryKey(cart);

        req.getSession().setAttribute("totalCount",cart.getCount());
        req.getSession().setAttribute("lastAddBook",bookService.queryBookById(cartItemid).getName());

        res.sendRedirect(req.getHeader("Referer"));

    }
    @RequestMapping("/clearCart")
    protected void clearCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //1.先清空 cartItem中的项
        Integer cartid = ((User)req.getSession().getAttribute("user")).getId();
        CartitemExample cartitemExample = new CartitemExample();
        CartitemExample.Criteria criteria = cartitemExample.createCriteria();
        criteria.andCartidEqualTo(cartid);
        int deleteByExample = cartitemMapper.deleteByExample(cartitemExample);

        Cart cart = new Cart(cartid,0,new BigDecimal(0));
        cartMapper.updateByPrimaryKey(cart);
        req.getSession().setAttribute("totalCount",0);
        req.getSession().removeAttribute("lastAddBook");

        res.sendRedirect(req.getHeader("Referer"));
    }

    @RequestMapping("/deleteItem")
    protected void deleteItem(HttpServletRequest req, HttpServletResponse res,Integer cartItemid) throws ServletException, IOException {
        System.out.println("come into deleteItem");

        Cartitem cartitem = cartitemMapper.selectByPrimaryKey(cartItemid);
        //1.更新 cartItem
        cartitemMapper.deleteByPrimaryKey(cartItemid);
        //2.更新cart 中的count 和 total
        User user = (User) req.getSession().getAttribute("user");
        Cart cart = cartMapper.selectByPrimaryKey(user.getId());

        //3. 更新页面显示信息
        cart.setCount(cart.getCount()-cartitem.getCount());
        cart.setTotalprice(cart.getTotalprice().subtract(cartitem.getTotalprice()));
        cartMapper.updateByPrimaryKey(cart);

        req.getSession().setAttribute("totalCount",cart.getCount());
        req.getSession().removeAttribute("lastAddBook");

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

        if(req.getSession().getAttribute("lastAddBook")==null){
            flag = true;
        }
        //1.将商品单项加入购物车 库存和销量不会变化
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
