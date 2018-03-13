package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum AccountBillSubType implements EnumValue<Integer> {
    COMMISSION("分佣", 10),
    WITHDRAW("提现", 20),
    BUY("购买", 30);

    AccountBillSubType(String name, Integer value) {
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
