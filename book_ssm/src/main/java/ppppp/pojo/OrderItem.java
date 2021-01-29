package ppppp.pojo;

import java.math.BigDecimal;

public class OrderItem {
    private Integer id;

    private String name;

    private Integer count;

    private BigDecimal price;

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    private BigDecimal totalprice;

    private String orderId;

    public OrderItem(String name, Integer count, BigDecimal price, String orderId) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.orderId = orderId;
        this.totalprice = getTotalPrice();

    }

    public OrderItem(Integer id, String name, Integer count, BigDecimal price, BigDecimal totalprice, String orderId) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.price = price;
        this.totalprice = getTotalPrice();
        this.orderId = orderId;
    }

    public OrderItem() {
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

    public BigDecimal getTotalPrice() {
        return price.multiply(new BigDecimal(count));
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