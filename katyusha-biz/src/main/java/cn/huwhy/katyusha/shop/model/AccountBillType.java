package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum AccountBillType implements EnumValue<Integer> {
    DEBIT("借", 1),
    CREDIT("贷", 0);

    AccountBillType(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private Integer value;

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
