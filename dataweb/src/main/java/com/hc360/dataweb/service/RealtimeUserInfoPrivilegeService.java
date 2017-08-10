package com.hc360.dataweb.service;

import com.hc360.dataweb.model.RealtimePrivilege;
import com.hc360.dataweb.model.RealtimeUserInfoPrivilege;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/3.
 */
public interface RealtimeUserInfoPrivilegeService {

    //重新给用户赋权
    void insertUserInfoPrivileges(BigDecimal userid,List<Integer> privilegeids) throws Exception;

    //获取用户权限
    List<RealtimeUserInfoPrivilege> getUserInfoPrivileges(BigDecimal userid) throws Exception;


    //获取权限列表
    List<Map<String, Object>> getAllRealtimePrivilegesMap() throws Exception;

    //获取权限列表
    List<RealtimePrivilege> getAllRealtimePrivileges() throws Exception;

    //获取用户权限id列表
    List<Integer> getUserInfoPrivilegeIds(BigDecimal userid) throws Exception;

    public void deleteUserInfoPrivileges(BigDecimal userid) throws Exception;

}
