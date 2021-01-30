package ppppp.service;

import ppppp.pojo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @author lppppp
 * @create 2020-12-31 19:56
 */
public interface CartService {
    List<Cartitem> getCartInfo();

    Cartitem selectCartItemByPrimaryKey(Integer cartItemid);

    void updateCartitemByPrimaryKey(Cartitem cartitem);

    Cart selectCartByPrimaryKey(Integer id);

    void updateCartByPrimaryKey(Cart cart);

    int deleteCartitemByExample(CartitemExample cartitemExample);

    void deleteCartItemByPrimaryKey(Integer cartItemid);

    void insertCart(Cart cart);

    void insertCartitem(Cartitem cartitem);

    void clearCart(CartitemExample cartitemExample, Integer cartid);

    List<Cartitem> selectCartitemByExample(CartitemExample cartitemExample);

    Cart deleteItem(Integer cartItemid, User user);

    HashMap<String, Object> addItem(Integer cartId, Book book, HttpServletRequest req);
}