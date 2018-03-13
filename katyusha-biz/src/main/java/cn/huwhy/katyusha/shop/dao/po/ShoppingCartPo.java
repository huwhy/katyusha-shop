package cn.huwhy.katyusha.shop.dao.po;

import java.io.Serializable;
import java.util.Date;

public class ShoppingCartPo implements Serializable {
    private long id;
    private long memberId;
    private long itemId;
    private long skuId;
    private int num;
    private Date modified;
    private Date created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

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

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
