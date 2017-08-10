package com.hc360.dataweb.model;

import java.math.BigDecimal;

/**
 * Created by dell on 2017/7/3.
 */
public class RealtimeUserInfoPrivilege {

    //用户id
    private BigDecimal userid;

    //权限id
    private Integer privilegeid;

    //权限名称
    private String privilege;

    //父权限
    private Integer p_type;

    //权限状态，0--可用，1--禁用
    private short state;

    public BigDecimal getUserid() {
        return userid;
    }

    public Integer getPrivilegeid() {
        return privilegeid;
    }

    public void setUserid(BigDecimal userid) {
        this.userid = userid;
    }

    public void setPrivilegeid(Integer privilegeid) {
        this.privilegeid = privilegeid;
    }

    public String getPrivilege() {
        return privilege;
    }

    public Integer getP_type() {
        return p_type;
    }

    public short getState() {
        return state;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public void setP_type(Integer p_type) {
        this.p_type = p_type;
    }

    public void setState(short state) {
        this.state = state;
    }
}
