package com.hc360.dataweb.inteceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hc360.dataweb.dao.RealtimeUserInfoMapper;
import com.hc360.dataweb.model.RealtimeUserInfo;
import com.hc360.dataweb.util.SpringContextHolder;
import com.hc360.sso.SSOInnerToken;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hc360.sso.SSOHelper;

import java.util.List;

/**
 *
 * @author liaoxn
 *
 */
public class LoginInteceptor implements HandlerInterceptor{

	private final Logger LOG = Logger.getLogger(LoginInteceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("进入拦截器");
		boolean isOk = false;

		//判断用户是否处于登录状态
		Cookie[] cookies = request.getCookies();//获取cookie数组
		if (null!=cookies) {
			for(Cookie cookie : cookies){
				if("dataUser".equalsIgnoreCase(cookie.getName())){
					cookie.setMaxAge(30 * 60);// 设置为30min
					cookie.setPath("/");
					response.addCookie(cookie);
					return true;
				}
			}
		}

		//response.sendRedirect("http://data.360jz.com/dataweb/index.html");
		response.sendRedirect("http://data.360jz.com/dataweb/unlogin");
		return isOk;
	}



}
