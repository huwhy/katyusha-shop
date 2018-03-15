package cn.huwhy.katyusha.shop.dao.po;

import cn.huwhy.katyusha.shop.model.ItemPhyType;
import cn.huwhy.katyusha.shop.model.ItemStatus;

import java.io.Serializable;
import java.util.Date;

public class ItemPo implements Serializable {
    private long id;
    private int categoryId;
    private String title;
    private String propDesc;
    private String summary;
    private String mainImg;
    private String images;
    private ItemPhyType phyType;
    private ItemStatus status;
    private String content;
    private int saleNum;
    private int evalNum;
    private int lowPrice;
    private int highPrice;
    private int lowMarketPrice;
    private int highMarketPrice;
    private String skuIds;
    private int  totalStock;
    private String outCode;
    private int commissionRate;
    private Date modified;
    private Date created;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPropDesc() {
        return propDesc;
    }

    public void setPropDesc(String propDesc) {
        this.propDesc = propDesc;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public ItemPhyType getPhyType() {
        return phyType;
    }

    public void setPhyType(ItemPhyType phyType) {
        this.phyType = phyType;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(int saleNum) {
        this.saleNum = saleNum;
    }

    public int getEvalNum() {
        return evalNum;
    }

    public void setEvalNum(int evalNum) {
        this.evalNum = evalNum;
    }

    public int getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(int lowPrice) {
        this.lowPrice = lowPrice;
    }

    public int getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(int highPrice) {
        this.highPrice = highPrice;
    }

    public int getLowMarketPrice() {
        return lowMarketPrice;
    }

    public void setLowMarketPrice(int lowMarketPrice) {
        this.lowMarketPrice = lowMarketPrice;
    }

    public int getHighMarketPrice() {
        return highMarketPrice;
    }

    public void setHighMarketPrice(int highMarketPrice) {
        this.highMarketPrice = highMarketPrice;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public String getOutCode() {
        return outCode;
    }

    public void setOutCode(String outCode) {
        this.outCode = outCode;
    }

    public int getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(int commissionRate) {
        this.commissionRate = commissionRate;
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
