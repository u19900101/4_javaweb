package ppppp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppppp.g_dao.CartMapper;
import ppppp.g_dao.CartitemMapper;
import ppppp.pojo.*;
import ppppp.service.CartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author lppppp
 * @create 2021-01-30 20:48
 */
@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    CartitemMapper cartitemMapper;
    @Autowired
    CartMapper cartMapper;
    @Override
    public List<Cartitem> getCartInfo() {
        return cartitemMapper.selectByExample(new CartitemExample());
    }

    @Override
    public Cart selectCartByPrimaryKey(Integer cartId) {
        return cartMapper.selectByPrimaryKey(cartId);
    }

    @Override
    public Cartitem selectCartItemByPrimaryKey(Integer cartItemid) {
        return cartitemMapper.selectByPrimaryKey(cartItemid);
    }

    @Override
    public void updateCartitemByPrimaryKey(Cartitem cartitem) {
        cartitemMapper.updateByPrimaryKey(cartitem);

    }

    @Override
    public void updateCartByPrimaryKey(Cart cart) {
        cartMapper.updateByPrimaryKey(cart);
    }

    @Override
    public int deleteCartitemByExample(CartitemExample cartitemExample) {
        return cartitemMapper.deleteByExample(cartitemExample);

    }

    @Override
    public void deleteCartItemByPrimaryKey(Integer cartItemid) {
        cartitemMapper.deleteByPrimaryKey(cartItemid);
    }

    @Override
    public void insertCart(Cart cart) {
        cartMapper.insert(cart);
    }

    @Override
    public void insertCartitem(Cartitem cartitem) {
        cartitemMapper.insert(cartitem);
    }

    @Override
    public void clearCart(CartitemExample cartitemExample, Integer cartid) {
        int deleteByExample = deleteCartitemByExample(cartitemExample);
        // 模拟事务
        // int j = 10/0;
        Cart cart = new Cart(cartid,0,new BigDecimal(0));
        updateCartByPrimaryKey(cart);
    }

    @Override
    public List<Cartitem> selectCartitemByExample(CartitemExample cartitemExample) {
        return cartitemMapper.selectByExample(cartitemExample);
    }

    @Override
    public Cart deleteItem(Integer cartItemid, User user) {
        Cartitem cartitem = selectCartItemByPrimaryKey(cartItemid);


        //1.更新 cartItem
        deleteCartItemByPrimaryKey(cartItemid);
        //2.更新cart 中的count 和 total

        Cart cart = selectCartByPrimaryKey(user.getId());
        // 演示事务控制
        // int j = 1/0;
        //3. 更新页面显示信息
        cart.setCount(cart.getCount()-cartitem.getCount());
        cart.setTotalprice(cart.getTotalprice().subtract(cartitem.getTotalprice()));
        updateCartByPrimaryKey(cart);

        return cart;
    }

    @Override
    public HashMap<String, Object> addItem(Integer cartId, Book book, HttpServletRequest req) {
        Cart cart = selectCartByPrimaryKey(cartId);
        boolean flag = false;
        // 判断是否有购物车
        // 无购物车则创建
        if(cart== null){
            cart =new Cart(cartId);
            insertCart(cart);
            req.getSession().setAttribute("cart",cart);
            flag = true;
        }

        if(req.getSession().getAttribute("lastAddBook")==null){
            flag = true;
        }


        // 查看购物车中是否有该商品 若无 则添加  有 则将数量相加  总价也要更新
        Cartitem cartitem =selectCartItemByPrimaryKey(book.getId());
        if(cartitem!= null){
            cartitem.setCount2(cartitem.getCount()+1);
            updateCartitemByPrimaryKey(cartitem);
        }else {
            cartitem = new Cartitem(book.getId(),book.getName(),Integer.parseInt("1"),
                    book.getPrice(),
                    book.getPrice().multiply(new BigDecimal(1)),
                    cartId);
            insertCartitem(cartitem);
        }

        // 修改购物车中的商品数量和总价
        cart.setCount(cart.getCount()+1);
        cart.setTotalprice(cart.getTotalprice().add(book.getPrice()));
        updateCartByPrimaryKey(cart);

        HashMap<String,Object> map = new HashMap<>();
        map.put("bookName",book.getName());
        map.put("count",cart.getCount());
        map.put("flag",flag);

        return map;
    }
}
