package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.RealtimeUserInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RealtimeUserInfoMapper {
    List<RealtimeUserInfo> selectByMisName(@Param("misname")String misname);

    List<RealtimeUserInfo> selectByMisNameForAdd(@Param("misname")String misname);

    List<RealtimeUserInfo> selectAllUsers();

    Integer insertAuthUser(@Param("misname")String misname);

    Integer updateUserState(@Param("id")BigDecimal id,@Param("state")Integer state,@Param("misname")String misname);
}
