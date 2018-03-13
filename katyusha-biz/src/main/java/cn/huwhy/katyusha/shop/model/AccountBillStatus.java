package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum AccountBillStatus implements EnumValue<Integer> {
    WAIT_SETTLEMENT("未结算", 10),
    SETTLEMENT("结算", 20),
    CANCEL("取消", 30);

    AccountBillStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private Integer value;

    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
