package com.hc360.dataweb.model;

import java.util.Date;

public class RealTimeStaticHour {
    private Integer id;

    private Integer dataType;

    private Integer dataCount;

    private String irslDateH;

    private Date opDate;

    private Integer sumCount;

    @Override
    public String toString() {
        return "RealTimeStaticHour{" +
                "id=" + id +
                ", dataType=" + dataType +
                ", dataCount=" + dataCount +
                ", irslDateH='" + irslDateH + '\'' +
                ", opDate=" + opDate +
                ", sumCount=" + sumCount +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataType() {
        return dataType;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    public String getIrslDateH() {
        return irslDateH;
    }

    public void setIrslDateH(String irslDateH) {
        this.irslDateH = irslDateH == null ? null : irslDateH.trim();
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}