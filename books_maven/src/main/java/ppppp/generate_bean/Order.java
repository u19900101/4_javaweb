package ppppp.generate_bean;

import java.math.BigDecimal;

public class Order {
    private String id;

    private String createTime;

    private Integer count;

    private BigDecimal totalprice;

    private Integer userId;

    public Order(String id, String createTime, Integer count, BigDecimal totalprice, Integer userId) {
        this.id = id;
        this.createTime = createTime;
        this.count = count;
        this.totalprice = totalprice;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}