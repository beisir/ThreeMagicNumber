package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.service.RealTimeStaticDayService;
import com.hc360.dataweb.service.RealTimeStaticHourService;
import com.hc360.dataweb.util.EmailUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class P4PDataController {
  private Logger logger = Logger.getLogger(P4PDataController.class);
  @Autowired
  private RealTimeStaticDayService realTimeStaticDayService;
  @Autowired
  private RealTimeStaticHourService realTimeStaticHourService;

  /**
   * 顶部各种颜色的图块的数据
   *
   * @param response
   */
  @RequestMapping(value = "/realtimepfpdata/", method = RequestMethod.GET, produces = {"application/xml", "application/json"})
  public void findP4PTopDataByDay(HttpServletRequest request, HttpServletResponse response) throws Exception {
    response.setContentType("application/json; charset=UTF-8");
    //数据
    Map<String, Object> dataList = new HashMap<String, Object>();
    Map<String, Object> dataTotal = new HashMap<String, Object>();//P4P消耗
    Map _dataMap = new HashMap();
    Map dataMap = new HashMap();
    try {
      //橙色顶部 p4p消耗
      realTimeStaticDayService.initP4PDataList(dataTotal);
      //添加P4P的数据,从小时表中添加数据
      realTimeStaticHourService.addP4pData(dataList);

      dataMap.put("dataTotal", dataTotal);
      dataMap.put("dataList", dataList);

      _dataMap.put("errno", 0);
      _dataMap.put("data", dataMap);
    } catch (Exception e) {
      EmailUtil.warnEveryOne("P4PDataController.findP4PTopDataByDay has error，" + "," + e.getMessage());
      logger.error("findP4PTopDataByDay has error，", e);
    }

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      response.getWriter().print(objectMapper.writeValueAsString(_dataMap));
      response.getWriter().flush();
      response.getWriter().close();
    } catch (Exception e) {
      logger.error("LiveDataController.realtimedata:", e);
    }
  }
}
