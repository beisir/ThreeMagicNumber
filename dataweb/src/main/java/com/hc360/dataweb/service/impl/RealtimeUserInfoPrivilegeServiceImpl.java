package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.RealtimeUserInfoPrivilegeMapper;
import com.hc360.dataweb.model.RealtimePrivilege;
import com.hc360.dataweb.model.RealtimeUserInfoPrivilege;
import com.hc360.dataweb.service.RealtimeUserInfoPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/3.
 */
@Service
public class RealtimeUserInfoPrivilegeServiceImpl implements RealtimeUserInfoPrivilegeService {

    @Autowired
    private RealtimeUserInfoPrivilegeMapper mapper;

    //重新给用户赋权
    @Transactional
    @Override
    public void insertUserInfoPrivileges(BigDecimal userid, List<Integer> privilegeids) throws Exception {

        mapper.deleteUserInfoPrivileges(userid); // 先清空用户权限
        mapper.insertUserInfoPrivileges(userid,privilegeids);//重新赋权

    }

    @Override
    public void deleteUserInfoPrivileges(BigDecimal userid) throws Exception {
        mapper.deleteUserInfoPrivileges(userid); // 先清空用户权限

    }

    //获取用户权限
    @Override
    public List<RealtimeUserInfoPrivilege> getUserInfoPrivileges(BigDecimal userid) throws Exception {
        return mapper.getUserInfoPrivileges(userid);
    }


    //获取用户权限
    @Override
    public List<Integer> getUserInfoPrivilegeIds(BigDecimal userid) throws Exception {
        return mapper.getUserInfoPrivilegeIds(userid);
    }

    //获取权限列表
    @Override
    public List<Map<String, Object>> getAllRealtimePrivilegesMap() throws Exception{
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        //获取所有父级权限
        List<RealtimePrivilege>  p_privileges = mapper.getPrivilegesByPTYPE(0);

        //循环父权限获取其下所有子权限
        if(null != p_privileges && !p_privileges.isEmpty()){
            for(RealtimePrivilege p_privilege:p_privileges){
                Map<String, Object>  map = new HashMap<String, Object>();
                map.put("p_privilege",p_privilege);
                map.put("c_privilege",mapper.getPrivilegesByPTYPE(p_privilege.getId()));
                list.add(map);
            }
        }

        return list;
    }


    //获取权限列表
    @Override
    public List<RealtimePrivilege> getAllRealtimePrivileges() throws Exception{
        List<RealtimePrivilege> list =  mapper.getAllPrivileges();
        return list;
    }
}
