/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.hc360.dataweb.controller;

import com.hc360.dataweb.util.ControllerDateUtil;
import com.hc360.dataweb.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *@Title:
 *@Description:生死监控
 *@Author:ggh
 *@Since:2015-4-8
 *@Version:1.1.0
 */
@Controller
@RequestMapping("/connTest")
public class ConnectTestController {

	protected final Logger log = Logger.getLogger(ConnectTestController.class);
	
	@Autowired
	private DataSource dataSource;

	@RequestMapping("/ok")
	public void testAll(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("application/json; charset=GBK");
		StringBuilder result = new StringBuilder();
		this.testOracle(result);
		try {
			response.getWriter().print(result.toString());
		} catch (IOException e) {
			log.error("response resultStr to client error. ", e);
		}
	}

	/**
	 * @param result, 记录测试结果
	 * @Description:数据库连接测试，执行一次查询（对服务器压力应尽可能的小），正确返回数据即可
	 */
	private void testOracle(StringBuilder result) {
		result.append("\"Oracle-");
		Connection conn = null;
		PreparedStatement statment = null;
		try {
			conn = dataSource.getConnection();
			//result.append(conn.getMetaData().getURL());
			String today = ControllerDateUtil.getToday();
			statment = conn.prepareStatement("select count(*) cnt from sjpt_realtime_static_hour where irsl_date_h >='" + today +"00'");
			statment.execute();
			ResultSet rs = statment.getResultSet();
			long cnt = 0;
			if(rs.next()){
				cnt = rs.getLong("cnt");
			}
			if(cnt > 0){
				result.append("\"OK\"\r\n");
			} else {
				throw new Exception("connect success, but table sjpt_realtime_static_hour has no today data");
			}
		} catch (Exception e) {
			result.append("\":").append("\"").append(e.getMessage()).append("\"\r\n");
			log.error("testOracle error, ",e);
		} finally {
			try {
				if(statment != null) {
					statment.close();
				}
			} catch (SQLException e) {}
			try {
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {}
		}
	}


}
