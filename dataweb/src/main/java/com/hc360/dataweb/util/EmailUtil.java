package com.hc360.dataweb.util;


import com.hc360.dataweb.dao.HadoopMonitorEmailMapper;
import com.hc360.dataweb.model.ConfigBean;
import com.hc360.dataweb.model.HadoopMonitorEmail;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by home on 2016/9/1.
 */
public class EmailUtil {
    private static Logger log = Logger.getLogger(EmailUtil.class);

    /**
     * @param msg 邮件告警
     */
    public static void warnEveryOne(String msg) {
        ConfigBean configBean = SpringContextHolder.getBean("configBean");
        warnEveryOne(msg,configBean.getEmails());
    }

    public static void warnEveryOne(String msg,HttpServletRequest request) {
        ConfigBean configBean = SpringContextHolder.getBean("configBean");
        warnEveryOne(msg + "-- "+ CommonUtil.getUserInfo(request,"dataUserName"),configBean.getEmails());
    }

    /**
     *
     * @param msg 告警信息
     * @param toUser 收件人
     */
    public static void warnEveryOne(String msg,String toUser) {
        warnEveryOne("统计数据平台报警",msg,toUser);
    }
    public static void warnEveryMe(String msg) {
        warnEveryOne("统计数据平台报警",msg,"zhangchen01@hc360.com;943956158@qq.com;");
    }

    public static void warnEveryMe(String title,String msg) {
        warnEveryOne(title,msg,"zhangchen01@hc360.com");
    }
    /**
     *@param title 报警标题
     * @param msg 告警信息
     * @param toUser 收件人
     */
    public static void warnEveryOne(String title,String msg,String toUser) {
        if (StringUtils.isBlank(msg)) {
            return;
        }
        int maxMsgLen = 3500;
        msg = DateUtil.getNow("yyyy-MM-dd hh:mm:ss") + "\n" + msg;
        msg = msg.length()>1000? msg.substring(0,1000):msg;
        HadoopMonitorEmailMapper hadoopMonitorEmailService = SpringContextHolder.getBean("hadoopMonitorEmailMapper");
        HadoopMonitorEmail emailWarnBean = new HadoopMonitorEmail("nagios_sj@hc360.com", toUser, title, msg.length() > maxMsgLen ? msg.substring(0, maxMsgLen) : msg);//告警邮件信息
        hadoopMonitorEmailService.insert(emailWarnBean);
        log.warn(msg);
    }

    /**
     *@param title 报警标题
     * @param msg 告警信息
     *
     */
    public static void warnEveryOneByTitle(String title,String msg) {
        if (StringUtils.isBlank(msg)) {
            return;
        }
        ConfigBean configBean = SpringContextHolder.getBean("configBean");
        int maxMsgLen = 3500;
        msg = DateUtil.getNow("yyyy-MM-dd hh:mm:ss") + "\n" + msg;
        HadoopMonitorEmailMapper hadoopMonitorEmailService = SpringContextHolder.getBean("hadoopMonitorEmailMapper");
        HadoopMonitorEmail emailWarnBean = new HadoopMonitorEmail("nagios_sj@hc360.com", configBean.getEmails(), title, msg.length() > maxMsgLen ? msg.substring(0, maxMsgLen) : msg);//告警邮件信息
        hadoopMonitorEmailService.insert(emailWarnBean);
        log.warn(msg);
    }
}
