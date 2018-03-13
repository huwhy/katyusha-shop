package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum RefundStatus implements EnumValue<Integer> {
    NO_VALUE("", 0),
    APPLY_REFUND("申请退款", 10),
    SELLER_DENY("卖家拒绝", 20),
    WAIT_RETURN("等待买家退货", 30),
    WAIT_CONFIRM_RETURN("等待确认退货", 50),
    AGREE("同意退款", 60),
    FINISHED("完成退款", 100),
    CLOSED("退款关闭", 110);

    private Integer value;

    private String name;

    RefundStatus(String name, Integer value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}