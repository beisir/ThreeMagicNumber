package com.hc360.dataweb.model;

public class SjptRealtimeStatic1Element {
    private Long id;

    private String oneElement;

    private Integer dataType;

    private Long dataCount;

    private String dataDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOneElement() {
        return oneElement;
    }

    public void setOneElement(String oneElement) {
        this.oneElement = oneElement == null ? null : oneElement.trim();
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public String getDataDate() {
        return dataDate;
    }

    public void setDataDate(String dataDate) {
        this.dataDate = dataDate == null ? null : dataDate.trim();
    }
}