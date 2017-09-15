package com.hc360.dataweb.model;

import java.util.Date;

public class RealtimeStaticMonth {
    private Long id;

    private Integer dataType;

    private Double dataCount;

    private String irslDate;

    private String dataDate;

    private Date opDate;

    @Override
    public String toString() {
        return "RealtimeStaticMonth{" +
                "id=" + id +
                ", dataType=" + dataType +
                ", dataCount=" + dataCount +
                ", irslDate='" + irslDate + '\'' +
                ", dataDate='" + dataDate + '\'' +
                ", opDate=" + opDate +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDataType() {
        return dataType;
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

    public String getIrslDate() {
        return irslDate;
    }

    public void setIrslDate(String irslDate) {
        this.irslDate = irslDate == null ? null : irslDate.trim();
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}