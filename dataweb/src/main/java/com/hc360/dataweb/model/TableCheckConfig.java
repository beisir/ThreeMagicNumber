package com.hc360.dataweb.model;

import java.util.Date;

public class TableCheckConfig {
    private String tbName;

    private String source;

    private Integer checkCycle;

    private Integer dbType;

    private Date insertDate;

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName == null ? null : tbName.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getCheckCycle() {
        return checkCycle;
    }

    public void setCheckCycle(Integer checkCycle) {
        this.checkCycle = checkCycle;
    }

    public Integer getDbType() {
        return dbType;
    }

    public void setDbType(Integer dbType) {
        this.dbType = dbType;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
}