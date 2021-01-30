package ppppp.pojo;

import java.math.BigDecimal;

public class Cartitem {
    private Integer id;

    private String name;

    private Integer count;

    private BigDecimal price;

    private BigDecimal totalprice;

    private Integer cartid;

    @Override
    public String toString() {
        return "Cartitem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                ", totalprice=" + totalprice +
                ", cartid=" + cartid +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    // 来级联设置
    public void setCount2(Integer count) {
        this.count = count;
        this.totalprice=price.multiply(new BigDecimal(count));
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    public Cartitem(Integer id,String name, Integer count, BigDecimal price, BigDecimal totalprice, Integer cartid) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalprice = totalprice;
        this.cartid = cartid;
    }

    public Cartitem() {
    }
}