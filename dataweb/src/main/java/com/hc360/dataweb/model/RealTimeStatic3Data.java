package com.hc360.dataweb.model;

import java.util.Date;

public class RealTimeStatic3Data {
    private Integer id;

    private Integer dataType;

    private String element;

    private Double dataCount;

    private String irslDate;

    private Date opDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element == null ? null : element.trim();
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

    public Date getOpDate() {
        return opDate;
    }

    public void setOpDate(Date opDate) {
        this.opDate = opDate;
    }
}