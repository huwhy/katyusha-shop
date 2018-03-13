package cn.huwhy.katyusha.shop.dao.po;

import java.io.Serializable;

public class StockPo implements Serializable {
    private long itemId;
    private long skuId;
    private int num;
    private int version;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
