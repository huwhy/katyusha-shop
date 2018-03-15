package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.Term;

public class ItemTerm extends Term {
    private int categoryId;
    private String title;
    private ItemPhyType phyType;
    private ItemStatus status;

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
}
