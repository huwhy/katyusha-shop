package cn.huwhy.katyusha.shop.model;

import cn.huwhy.interfaces.Term;

import java.util.Date;

public class TradeTerm extends Term {
    private long id;
    private long memberId;
    private TradeStatus status;
    private Date payTimeFrom;
    private Date payTimeTo;
    private Date createdFrom;
    private Date createdTo;

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

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public Date getPayTimeFrom() {
        return payTimeFrom;
    }

    public void setPayTimeFrom(Date payTimeFrom) {
        this.payTimeFrom = payTimeFrom;
    }

    public Date getPayTimeTo() {
        return payTimeTo;
    }

    public void setPayTimeTo(Date payTimeTo) {
        this.payTimeTo = payTimeTo;
    }

    public Date getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(Date createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Date getCreatedTo() {
        return createdTo;
    }

    public void setCreatedTo(Date createdTo) {
        this.createdTo = createdTo;
    }
}
