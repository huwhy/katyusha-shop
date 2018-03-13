package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.EnumValue;

public enum ItemPhyType implements EnumValue<Integer> {
    ACTUAL("实物商品", 1),
    VIRTUAL("虚拟商品", 2);

    ItemPhyType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
