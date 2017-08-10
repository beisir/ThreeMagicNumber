package com.hc360.dataweb.service.impl;

import com.hc360.dataweb.dao.SjptRealtimeStatic1ElementMapper;
import com.hc360.dataweb.dao.SjptRealtimeStatic2ElementMapper;
import com.hc360.dataweb.dao.SjptRealtimeStatic3ElementMapper;
import com.hc360.dataweb.model.RegionDataBean;
import com.hc360.dataweb.model.SjptRealtimeStatic1Element;
import com.hc360.dataweb.model.SjptRealtimeStatic2Element;
import com.hc360.dataweb.model.SjptRealtimeStatic3Element;
import com.hc360.dataweb.service.NumericonService;
import com.hc360.dataweb.util.ControllerDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created by zhangwanru on 2017/5/4.
 */
@Service
public class NumericonServiceImpl implements NumericonService {
    private Logger logger = Logger.getLogger(NumericonServiceImpl.class);
    @Autowired
    SjptRealtimeStatic1ElementMapper element1Mapper;
    @Autowired
    SjptRealtimeStatic2ElementMapper element2Mapper;
    @Autowired
    SjptRealtimeStatic3ElementMapper element3Mapper;

    @Override
    public void findNumericonData(Integer dataType, Map<String, Object> data) throws Exception {
        //站点:JS WX M PC ZOL
        Map<String, Object> param = new HashMap<String, Object>();
        String day = ControllerDateUtil.getToday();//今日日期
        String yesterday = ControllerDateUtil.getYesterday();//昨天日期
        param.put("type", dataType);
        //根据data_type查询今天的最新数据时间
        String dataDate1 = element1Mapper.findElement1NewestDataDate(param);
        String dataDate2 = element2Mapper.findElement2NewestDataDate(param);
        String dataDate3 = element3Mapper.findElement3NewestDataDate(param);
        param.put("time", dataDate1);
        List<SjptRealtimeStatic1Element> element1s = element1Mapper.findElement1ToDayData(param);
        param.put("time", dataDate2);
        List<SjptRealtimeStatic2Element> element2s = element2Mapper.findElement2ToDayData(param);
        param.put("time", dataDate3);
        List<SjptRealtimeStatic3Element> element3s = element3Mapper.findElement3ToDayData(param);

        param.put("yesterday",yesterday);
        param.put("time", dataDate1);
        List<SjptRealtimeStatic1Element> yelement1s = element1Mapper.findElement1YesterDayData(param);
        param.put("time", dataDate2);
        List<SjptRealtimeStatic2Element> yelement2s = element2Mapper.findElement2YesterDayData(param);
        param.put("time", dataDate3);
        List<SjptRealtimeStatic3Element> yelement3s = element3Mapper.findElement3YesterDayData(param);
        convertMap1(data, element1s, element2s, element3s,yelement1s, yelement2s, yelement3s);//处理数据
    }

    @Override
    public void findRegionData(Integer dataType, List<RegionDataBean> beans) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        String day = ControllerDateUtil.getToday();//今日日期
        String yesterday = ControllerDateUtil.getYesterday();//昨天日期
        param.put("type", dataType);
        //根据data_type查询今天的最新数据时间
        String dataDate1 = element1Mapper.findElement1NewestDataDate(param);
        param.put("time", dataDate1);
        List<SjptRealtimeStatic1Element> element1s = element1Mapper.findElement1ToDayData(param);
        param.put("yesterday",yesterday);
        List<SjptRealtimeStatic1Element> yelement1s = element1Mapper.findElement1YesterDayData(param);
        convertList(beans,element1s,yelement1s);
    }
    /*地区*/
    private void convertList(List<RegionDataBean> beans, List<SjptRealtimeStatic1Element> element1s, List<SjptRealtimeStatic1Element> yelement1s) {
        RegionDataBean bean = null;
        if(element1s != null && !element1s.isEmpty()){
            for(SjptRealtimeStatic1Element element1 : element1s){
                bean = new RegionDataBean();
                bean.setFullname(element1.getOneElement());
                bean.setValue(element1.getDataCount());
                if (yelement1s != null && !yelement1s.isEmpty()){
                    for (SjptRealtimeStatic1Element yelement1 : yelement1s){
                        if(bean.getFullname().equals(yelement1.getOneElement())){
                            bean.setYestodayvalue(yelement1.getDataCount());
                        }
                    }
                }else{
                    logger.error("查询SjptRealtimeStatic1Element表数据为空:时间_"+ControllerDateUtil.getYesterday()+".");
                }
                beans.add(bean);
            }
        }else{
           logger.error("查询SjptRealtimeStatic1Element表数据为空:时间_"+ControllerDateUtil.getToday()+".");
        }
    }

    /*站点*/
    private void convertMap1(Map<String, Object> data,List<SjptRealtimeStatic1Element> element1s,List<SjptRealtimeStatic2Element> element2s,
                             List<SjptRealtimeStatic3Element> element3s,List<SjptRealtimeStatic1Element> yelement1s,
                             List<SjptRealtimeStatic2Element> yelement2s,List<SjptRealtimeStatic3Element> yelement3s) {
        Map<String, Object> map2 = null;//val child
        Map<String, Object> map3 = null;//域名 CRM SSO HOMEPAGE
        if(element1s != null && !element1s.isEmpty()){
            for (SjptRealtimeStatic1Element element1:element1s){
                map2 = new HashMap<>();
                map3 = new HashMap<>();
                data.put(element1.getOneElement(),map2);//PC
                map2.put("val",element1.getDataCount());
                for (SjptRealtimeStatic1Element yelement1:yelement1s){
                    if(element1.getOneElement().equals(yelement1.getOneElement())){
                        map2.put("yesval",yelement1.getDataCount());
                    }
                }
                map2.put("child",map3);
                convertMap2(map3,element1,element2s,yelement2s,element3s,yelement3s);
            }
        }
    }
    /*域名*/
    private void convertMap2(Map<String, Object> map3, SjptRealtimeStatic1Element element1,List<SjptRealtimeStatic2Element> element2s, List<SjptRealtimeStatic2Element> yelement2s,
                             List<SjptRealtimeStatic3Element> element3s, List<SjptRealtimeStatic3Element> yelement3s) {
        Map<String, Object> map4 = null;//val child
        Map<String, Object> map5 = null;//来源 inner seo sem
        if(element2s != null && !element2s.isEmpty()){
            for (SjptRealtimeStatic2Element element2 :element2s){
                map4 = new HashMap<>();
                map5 = new HashMap<>();
                if(element1.getOneElement().equals(element2.getOneElement())){
                    map3.put(element2.getTwoElement(),map4);//CRM
                    map4.put("val", element2.getDataCount());
                    for (SjptRealtimeStatic2Element yelement2:yelement2s){
                        if(element2.getOneElement().equals(yelement2.getOneElement()) && element2.getTwoElement().equals(yelement2.getTwoElement())){
                            map4.put("yesval",yelement2.getDataCount());
                        }
                    }
                    map4.put("child",map5);
                    convertMap3(map5, element2,element3s, yelement3s);
                }
            }
        }
    }
    /*来源*/
    private void convertMap3(Map<String, Object> map5, SjptRealtimeStatic2Element element2,List<SjptRealtimeStatic3Element> element3s, List<SjptRealtimeStatic3Element> yelement3s) {
        Map<String, Object> map6 = null;//val child
        if(element3s != null && !element3s.isEmpty()){
            for (SjptRealtimeStatic3Element element3 :element3s){
                map6 = new HashMap<>();
                if(element2.getOneElement().equals(element3.getOneElement()) && element2.getTwoElement().equals(element3.getTwoElement())) {
                    map5.put(element3.getThreeElement(), map6);//other seo sem
                    map6.put("val", element3.getDataCount());
                    for (SjptRealtimeStatic3Element yelement3 : yelement3s) {
                        if (element3.getOneElement().equals(yelement3.getOneElement()) && element3.getTwoElement().equals(yelement3.getTwoElement()) && element3.getThreeElement().equals(yelement3.getThreeElement())) {
                            map6.put("yesval", yelement3.getDataCount());
                        }

                    }
                }
            }
        }
    }
}
