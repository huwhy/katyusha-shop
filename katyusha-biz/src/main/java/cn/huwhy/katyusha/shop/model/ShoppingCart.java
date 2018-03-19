package cn.huwhy.katyusha.shop.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.Serializable;
import java.util.Date;

public class ShoppingCart implements Serializable {
    private long id;
    private long memberId;
    private long itemId;
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private Item item;
    private long skuId;
    @JSONField(serialzeFeatures = {SerializerFeature.DisableCircularReferenceDetect})
    private Sku sku;
    private int num;
    private Date modified;
    private Date created;

    public ShoppingCart() {
    }

    public ShoppingCart(long memberId, long itemId, long skuId, int num) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.skuId = skuId;
        this.num = num;
    }

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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }
}
