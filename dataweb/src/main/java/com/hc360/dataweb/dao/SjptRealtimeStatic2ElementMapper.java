package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.SjptRealtimeStatic1Element;
import com.hc360.dataweb.model.SjptRealtimeStatic2Element;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangwanru on 2017/5/4.
 */
@Component
public interface SjptRealtimeStatic2ElementMapper {
    /**
     * 获取今天元素{域名}拆分数据
     * @param param
     * @return
     * @throws Exception
     */
    List<SjptRealtimeStatic2Element> findElement2ToDayData(Map<String, Object> param) throws Exception;
    /**
     * 获取昨天元素{域名}拆分数据
     * @param param
     * @return
     * @throws Exception
     */
    List<SjptRealtimeStatic2Element> findElement2YesterDayData(Map<String, Object> param) throws Exception;

    /**
     * 获取当天最新数据时间
     * @param param
     * @return
     * @throws Exception
     */
    String findElement2NewestDataDate(Map<String, Object> param) throws Exception;
}
