package ppppp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ppppp.g_dao.CartMapper;
import ppppp.g_dao.CartitemMapper;
import ppppp.pojo.Cart;
import ppppp.pojo.Cartitem;
import ppppp.pojo.CartitemExample;
import ppppp.service.CartService;

import java.math.BigDecimal;
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
        int j = 10/0;
        Cart cart = new Cart(cartid,0,new BigDecimal(0));
        updateCartByPrimaryKey(cart);
    }

    @Override
    public List<Cartitem> selectCartitemByExample(CartitemExample cartitemExample) {
        return cartitemMapper.selectByExample(cartitemExample);
    }
}
