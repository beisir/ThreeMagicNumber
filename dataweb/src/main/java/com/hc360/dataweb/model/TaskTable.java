package com.hc360.dataweb.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dell on 2017/12/7.
 */
public class TaskTable {

    //id
    String id;

    //任务周期，格式：20171201，表示2017年12月第一周
    Integer taskTime;

    //部门
    String department;

    //任务数量
    String taskNum;

    //入库时间
    Date creatTime;

    //状态：0--正常；1--已删除
    Integer state;

    //操作人
    BigDecimal operatorId;


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public BigDecimal getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(BigDecimal operatorId) {
        this.operatorId = operatorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTaskTime() {
        return taskTime;
    }

    public String getDepartment() {
        return department;
    }


    public Date getCreatTime() {
        return creatTime;
    }


    public void setTaskTime(Integer taskTime) {
        this.taskTime = taskTime;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
