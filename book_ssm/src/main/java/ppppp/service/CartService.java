package ppppp.service;

import ppppp.pojo.Cart;
import ppppp.pojo.Cartitem;
import ppppp.pojo.CartitemExample;

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
}
