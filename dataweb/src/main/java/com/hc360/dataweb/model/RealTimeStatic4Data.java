package com.hc360.dataweb.model;

import java.util.Date;

public class RealTimeStatic4Data {
    private Integer id;

    private Integer dataType;

    private String element;

    private Integer dataCount1;

    private Integer dataCount2;

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

    public Integer getDataCount1() {
        return dataCount1;
    }

    public void setDataCount1(Integer dataCount1) {
        this.dataCount1 = dataCount1;
    }

    public Integer getDataCount2() {
        return dataCount2;
    }

    public void setDataCount2(Integer dataCount2) {
        this.dataCount2 = dataCount2;
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