package com.hc360.dataweb.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2017/12/7.
 */
public class OrderMoveTable {

    //id
    String id;

    //划转时间
    String moveDate;

    //订单编号
    Long orderId;

    //划出业务员
    String fromWho;

    //划入业务员
    String toWho;

    //金额
    String amount;

    //客户划给谁
    String clientToWho;

    //入库时间
    Date creatTime;

    //状态：0--正常；1--已删除
    Integer state;

    //操作人
    BigDecimal operatorId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(BigDecimal operatorId) {
        this.operatorId = operatorId;
    }

    public String getMoveDate() {
        return moveDate;
    }

    public void setMoveDate(String moveDate) {
        this.moveDate = moveDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getClientToWho() {
        return clientToWho;
    }

    public void setClientToWho(String clientToWho) {
        this.clientToWho = clientToWho;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
