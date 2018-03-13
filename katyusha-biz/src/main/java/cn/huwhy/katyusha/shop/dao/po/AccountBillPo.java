package cn.huwhy.katyusha.shop.dao.po;

import cn.huwhy.katyusha.shop.model.AccountBillStatus;
import cn.huwhy.katyusha.shop.model.AccountBillSubType;
import cn.huwhy.katyusha.shop.model.AccountBillType;

import java.io.Serializable;
import java.util.Date;

public class AccountBillPo implements Serializable {
    private long id;
    private long memberId;
    private long tid;
    private long oid;
    private AccountBillType type;
    private int amount;
    private AccountBillSubType subType;
    private AccountBillStatus status;
    private Date endTime;
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

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public AccountBillType getType() {
        return type;
    }

    public void setType(AccountBillType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public AccountBillSubType getSubType() {
        return subType;
    }

    public void setSubType(AccountBillSubType subType) {
        this.subType = subType;
    }

    public AccountBillStatus getStatus() {
        return status;
    }

    public void setStatus(AccountBillStatus status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
