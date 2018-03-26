package cn.huwhy.katyusha.shop.dao.po;

import java.io.Serializable;

public class MpPayPo implements Serializable {
    private long tid;
    private String prepayId;
    private String transactionId;

    public MpPayPo() {
    }

    public MpPayPo(long tid, String prepayId, String transactionId) {
        this.tid = tid;
        this.prepayId = prepayId;
        this.transactionId = transactionId;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
