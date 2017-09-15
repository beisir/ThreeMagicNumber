package com.hc360.dataweb.model;

import java.math.BigDecimal;

/**
 * Created by dell on 2017/7/4.
 */
public class RealtimePrivilege {

    //用户id
    private Integer id;

    //权限名称
    private String privilege;

    //父权限
    private Integer p_type;

    //权限状态，0--可用，1--禁用
    private short state;

    @Override
    public String toString() {
        return "RealtimePrivilege{" +
                "id=" + id +
                ", privilege='" + privilege + '\'' +
                ", p_type=" + p_type +
                ", state=" + state +
                '}';
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
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
