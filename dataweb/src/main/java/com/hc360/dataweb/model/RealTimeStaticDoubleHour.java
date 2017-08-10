package com.hc360.dataweb.model;

import java.util.Date;

public class RealTimeStaticDoubleHour {
    private Integer id;

    private Integer dataType;

    private Double dataCount;

    private String irslDateH;

    private Date opDate;

    private Integer sumCount;

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

    public Double getDataCount() {
        return dataCount;
    }

    public void setDataCount(Double dataCount) {
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