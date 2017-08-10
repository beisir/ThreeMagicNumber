package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealtimePrivilege;
import com.hc360.dataweb.model.RealtimeUserInfoPrivilege;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dell on 2017/7/3.
 */
@Repository
public interface RealtimeUserInfoPrivilegeMapper {

    int insertUserInfoPrivileges(@Param("userid")BigDecimal userid,@Param("privilegeids")List<Integer> privilegeids);

    List<RealtimeUserInfoPrivilege> getUserInfoPrivileges(@Param("userid")BigDecimal userid);

    List<Integer> getUserInfoPrivilegeIds(@Param("userid")BigDecimal userid);

    int deleteUserInfoPrivileges(@Param("userid")BigDecimal userid);


    List<RealtimePrivilege> getPrivilegesByPTYPE(@Param("p_type")Integer p_type);

    List<RealtimePrivilege> getAllPrivileges();

}
