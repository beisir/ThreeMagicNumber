package com.hc360.dataweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hc360.dataweb.service.test.TableCheckConfigService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by home on 2017/2/5.
 */
@Controller
public class TestController {
    private  Logger logger = Logger.getLogger(TestController.class);
    @Autowired
    private TableCheckConfigService tableCheckConfigService;

    @RequestMapping("init")
    public ModelAndView resource() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/screen/test/test");
        return mv;
    }
    @RequestMapping(value="/test/find")
    @ResponseStatus(HttpStatus.OK)
    public Map<String,String> find( HttpServletResponse response){
        response.setContentType("application/json; charset=UTF8");
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = new HashMap<String,String>();
        map.put("user","useraaaa");
        map.put("emaill","zhangchen01@3344.com");
        try {
            logger.info(mapper.writeValueAsString(map));
        } catch (Exception e) {
            logger.error("",e);
        }
        return map;
    }


}
