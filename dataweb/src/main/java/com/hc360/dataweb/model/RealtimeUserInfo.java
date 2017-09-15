package com.hc360.dataweb.model;

import java.math.BigDecimal;

public class RealtimeUserInfo {
    private BigDecimal id;

    private String misname;

    private Short state;

    @Override
    public String toString() {
        return "RealtimeUserInfo{" +
                "id=" + id +
                ", misname='" + misname + '\'' +
                ", state=" + state +
                '}';
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getMisname() {
        return misname;
    }

    public void setMisname(String misname) {
        this.misname = misname == null ? null : misname.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }
}