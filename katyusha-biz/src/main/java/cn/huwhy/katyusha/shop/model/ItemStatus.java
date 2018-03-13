package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum ItemStatus implements EnumValue<Integer> {
    IN_SALE("在售", 10),
    OFF_SHELF("下架", 20),
    LOCKED("冻结", 30);

    private int value;
    private String name;


    ItemStatus(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
