package ppppp.pojo;

import java.math.BigDecimal;

public class Cart {
    private Integer cartid;

    private Integer count;

    private BigDecimal totalprice;

    public Cart(Integer cartid) {
        this.cartid = cartid;
        this.count = 0;
        this.totalprice = new BigDecimal(0);
        this.userid = cartid;
    }

    public Cart(Integer cartid,Integer count, BigDecimal totalprice) {
        this.cartid = cartid;
        this.count = count;
        this.totalprice = totalprice;
        this.userid = cartid;
    }

    public Cart() {
    }

    private Integer userid;

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}