package cn.huwhy.katyusha.shop.dao.po;

import java.io.Serializable;

public class AccountPo implements Serializable {

    private long id;
    //总收入金额
    private int totalAmount;
    //收入余额
    private int amount;
    //未入帐收入
    private int amount2;
    //提现中金额
    private int outingAmount;
    //数据版本号
    private int version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    public int getOutingAmount() {
        return outingAmount;
    }

    public void setOutingAmount(int outingAmount) {
        this.outingAmount = outingAmount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
