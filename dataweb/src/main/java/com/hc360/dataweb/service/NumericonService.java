package com.hc360.dataweb.service;

import com.hc360.dataweb.model.RegionDataBean;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangwanru on 2017/5/4.
 */
public interface NumericonService {

    /**
     * 获取IP/UV/PV/询盘 的 站点、域名、来源分布明细
     * @param dataType
     * @param data
     * @throws Exception
     */
    void findNumericonData(Integer dataType, Map<String, Object> data) throws Exception;

    /**
     * 获取中国地区每个省份的数据
     * @param dataType
     * @param beans
     * @throws Exception
     */
    void findRegionData(Integer dataType, List<RegionDataBean> beans) throws Exception;
}
