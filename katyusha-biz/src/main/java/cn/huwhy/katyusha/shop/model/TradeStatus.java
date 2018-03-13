package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum TradeStatus implements EnumValue<Integer> {
    CREATED(10, "新建"),
    WAIT_PAY(20, "待付款"),
    WAIT_DELIVER_GOODS(30, "待发货"),
    WAIT_CONFIRM_RECEIVE(50, "待收货"),
    IN_REFUND(60, "退款中"),
    FINISHED(200, "已完成"),
    PART_REFUND(201, "部分退款"),
    CLOSED(210, "已关闭"),
    CLOSED_BY_AUTO(211, "已关闭"),
    CLOSED_BY_REFUND(212, "已退款");

    private Integer value;
    private String name;

    TradeStatus(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
