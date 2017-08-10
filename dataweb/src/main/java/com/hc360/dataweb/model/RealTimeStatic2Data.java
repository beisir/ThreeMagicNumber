package com.hc360.dataweb.model;

public class RealTimeStatic2Data {
    private Integer id;

    private Integer dataType;

    private String element;

    private Integer dataCount1;

    private Double dataCount2;

    private String dataDate;

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

    public Double getDataCount2() {
        return dataCount2;
    }

    public void setDataCount2(Double dataCount2) {
        this.dataCount2 = dataCount2;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }
}