package cn.huwhy.katyusha.shop.dao.po;

import cn.huwhy.katyusha.shop.model.TradeStatus;
import java.io.Serializable;
import java.util.Date;

public class TradePo implements Serializable {
    private long id;
    private long memberId;
    private int totalAmount;
    private int discountAmount;
    private int totalPostFee;
    private int totalPayment;
    private TradeStatus status;
    private Date payTime;
    private Date consignTime;
    private String expressName;
    private String expressCode;
    private Date planReceiveTime;
    private String receivePerson;
    private String receiveProvince;
    private String receiveCity;
    private String receiveDistrict;
    private String receiveAddress;
    private String receivePhone;
    private String receiveZip;
    private String buyMemo;
    private String cardNo;
    private String realName;
    private Date endTime;
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

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public int getTotalPostFee() {
        return totalPostFee;
    }

    public void setTotalPostFee(int totalPostFee) {
        this.totalPostFee = totalPostFee;
    }

    public int getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(int totalPayment) {
        this.totalPayment = totalPayment;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getConsignTime() {
        return consignTime;
    }

    public void setConsignTime(Date consignTime) {
        this.consignTime = consignTime;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public Date getPlanReceiveTime() {
        return planReceiveTime;
    }

    public void setPlanReceiveTime(Date planReceiveTime) {
        this.planReceiveTime = planReceiveTime;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getReceiveProvince() {
        return receiveProvince;
    }

    public void setReceiveProvince(String receiveProvince) {
        this.receiveProvince = receiveProvince;
    }

    public String getReceiveCity() {
        return receiveCity;
    }

    public void setReceiveCity(String receiveCity) {
        this.receiveCity = receiveCity;
    }

    public String getReceiveDistrict() {
        return receiveDistrict;
    }

    public void setReceiveDistrict(String receiveDistrict) {
        this.receiveDistrict = receiveDistrict;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public String getReceiveZip() {
        return receiveZip;
    }

    public void setReceiveZip(String receiveZip) {
        this.receiveZip = receiveZip;
    }

    public String getBuyMemo() {
        return buyMemo;
    }

    public void setBuyMemo(String buyMemo) {
        this.buyMemo = buyMemo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
