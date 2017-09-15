package com.hc360.dataweb.controller;

import com.hc360.dataweb.dao.RealtimeUserInfoMapper;
import com.hc360.dataweb.model.RealtimePrivilege;
import com.hc360.dataweb.model.RealtimeUserInfo;
import com.hc360.dataweb.model.RealtimeUserInfoPrivilege;
import com.hc360.dataweb.service.RealtimeUserInfoPrivilegeService;
import com.hc360.dataweb.util.EmailUtil;
import com.hc360.dataweb.util.ResponseJson;
import com.hc360.dataweb.util.SpringContextHolder;
import com.hc360.sso.SSOHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017/7/4.
 */
@Controller
public class UserInfoPrivilegeController {

    private Logger logger = Logger.getLogger(UserInfoPrivilegeController.class);

    @Autowired
    RealtimeUserInfoPrivilegeService privilegeService;


    @RequestMapping("/getAllRealtimePrivileges")
    @ResponseBody
    public ResponseJson getAllRealtimePrivileges() {
        ResponseJson json = new ResponseJson();
        List<RealtimePrivilege> list = null;
       try{
           list = privilegeService.getAllRealtimePrivileges();
           json.setData(list);
           json.setErrno(0);
       }catch (Exception e){
           json.setErrno(1);
           json.setMsg("获取权限异常");
           EmailUtil.warnEveryOne("UserInfoPrivilegeController.findRegionData has error，"  + e.getMessage());
           logger.error("UserInfoPrivilegeController.findRegionData has error，",e);
       }
        return json;
    }


    @RequestMapping("/updateAuthData")
    @ResponseBody
    public ResponseJson insertUserInfoPrivileges(@RequestParam(required = false, value = "privileges[]") List<Integer> list,@RequestParam(required = false, value = "id")BigDecimal id) {
        ResponseJson json = new ResponseJson();
        try{
            if(null != list){
                privilegeService.insertUserInfoPrivileges(id,list);
                json.setErrno(0);
            }else{
                privilegeService.deleteUserInfoPrivileges(id);
                json.setMsg("已清空该用户权限");
            }
        }catch (Exception e){
            json.setErrno(1);
            json.setMsg("更新用户权限失败");
            EmailUtil.warnEveryOne("UserInfoPrivilegeController.insertUserInfoPrivileges has error，id="+id  + "," + e.getMessage());
           logger.error("UserInfoPrivilegeController.insertUserInfoPrivileges has error，id="+id ,e);
        }
        return json;
    }


    @RequestMapping("/getAuthData")
    @ResponseBody
    public ResponseJson getUserInfoPrivileges(@RequestParam(value = "id")BigDecimal id,@RequestParam(value = "misname")String misname) {
        ResponseJson json = new ResponseJson();

        try{
            List<Integer> list = privilegeService.getUserInfoPrivilegeIds(id);
            json.setData(list);
            json.setErrno(0);
        }catch (Exception e){
            json.setErrno(1);
            json.setMsg("获取用户权限异常");
            EmailUtil.warnEveryOne("UserInfoPrivilegeController.getUserInfoPrivileges has error，id="+id+",misname="+misname + "," + e.getMessage());
            logger.error( "UserInfoPrivilegeController.getUserInfoPrivileges has error，id="+id+",misname="+misname ,e );
        }
        return json;
    }


    @RequestMapping("/getAuthDatas")
    @ResponseBody
    public ResponseJson getAuthDatas(HttpServletRequest request) {
        ResponseJson json = new ResponseJson();
        try{
            Cookie[] cookies = request.getCookies();//获取cookie数组
            if (null!=cookies) {
                for(Cookie cookie : cookies){
                    if("dataUser".equalsIgnoreCase(cookie.getName())){
                        int i = Integer.parseInt(cookie.getValue());
                        List<Integer> list = privilegeService.getUserInfoPrivilegeIds(BigDecimal.valueOf((long)i));
                        json.setData(list);
                        json.setErrno(0);
                    }
                }
            }else{
                json.setErrno(2);
                json.setMsg("无登录用户");
            }

        }catch (Exception e){
            json.setErrno(1);
            json.setMsg("获取用户权限异常");
            EmailUtil.warnEveryOne("UserInfoPrivilegeController.getAuthDatas has error," + e.getMessage());
            logger.error("UserInfoPrivilegeController.getAuthDatas has error,",e);
        }
        return json;
    }


    @RequestMapping("/getAuthUsers")
    @ResponseBody
    public ResponseJson getAllUsers(HttpServletRequest request,HttpServletResponse response) {
        ResponseJson json = new ResponseJson();
        try{
            RealtimeUserInfoMapper realtimeUserInfoMapper = SpringContextHolder.getBean("realtimeUserInfoMapper");
            List<RealtimeUserInfo> list = realtimeUserInfoMapper.selectAllUsers();
            json.setData(list);
            json.setErrno(0);
        }catch (Exception e){
            json.setErrno(1);
            json.setMsg("获取用户列表异常");
            EmailUtil.warnEveryOne("UserInfoPrivilegeController.getAllUsers has error," + e.getMessage());
            logger.error("UserInfoPrivilegeController.getAllUsers has error,",e);
        }
        return json;
    }



    @RequestMapping("/addAuthUser")
    @ResponseBody
    public ResponseJson addAuthUser(@RequestParam(value = "misname")String misname) {
        ResponseJson json = new ResponseJson();
        try{
            //查看用户是否已存在
            RealtimeUserInfoMapper realtimeUserInfoMapper = SpringContextHolder.getBean("realtimeUserInfoMapper");
            List<RealtimeUserInfo> list = realtimeUserInfoMapper.selectByMisNameForAdd(misname);
            if(null != list && list.size()>0){
                json.setErrno(2);
                json.setMsg("用户已存在");
                return json;
            }
            //判断是否是慧聪员工
            if("1".equals(SSOHelper.queryHCWorker(misname))){
                json.setErrno(3);
                json.setMsg(misname+"--非MIS用户");
                return json;
            }

            //添加用户
            if(1==realtimeUserInfoMapper.insertAuthUser(misname)){
                list = realtimeUserInfoMapper.selectByMisName(misname);
                if(null != list){
                    json.setData(list.get(0));
                }
                json.setErrno(0);
                json.setMsg("添加成功");
                return json;
            }

        }catch (Exception e){
            json.setErrno(1);
            json.setMsg("添加用户异常");
            EmailUtil.warnEveryOne("UserInfoPrivilegeController.addAuthUser has error,misname="+misname+"-" + e.getMessage());
            logger.error("UserInfoPrivilegeController.addAuthUser has error,misname="+misname,e);
        }
        return json;
    }



    @RequestMapping("/updateUserState")
    @ResponseBody
    public ResponseJson updateUserState(@RequestParam(value = "id")BigDecimal id,@RequestParam(value = "state")Integer state,@RequestParam(value = "misname")String misname) {
        ResponseJson json = new ResponseJson();
        try{
            RealtimeUserInfoMapper realtimeUserInfoMapper = SpringContextHolder.getBean("realtimeUserInfoMapper");
            Integer i = realtimeUserInfoMapper.updateUserState(id,state,misname);
            if(i>0){
                json.setErrno(0);
            }else{
                json.setErrno(2);
                json.setMsg("更新用户状态失败");
            }
        }catch (Exception e){
            json.setErrno(1);
            json.setMsg("更新用户状态异常");
            EmailUtil.warnEveryOne("UserInfoPrivilegeController.updateUserState has error,misname=" + misname+",id="+id+",state="+state + "-" + e.getMessage());
            logger.error("UserInfoPrivilegeController.updateUserState has error,misname=" + misname+",id="+id+",state="+state,e);
        }
        return json;
    }

}
