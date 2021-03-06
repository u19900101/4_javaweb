package ppppp.generate_bean;

import java.math.BigDecimal;

public class Orderitem {
    private Integer id;

    private String name;

    private Integer count;

    private BigDecimal price;

    private BigDecimal totalprice;

    private String orderId;

    public Orderitem(String name, Integer count, BigDecimal price, String orderId) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalprice = totalprice;
        this.orderId = orderId;
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
        this.name = name == null ? null : name.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }
}