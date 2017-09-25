package com.hc360.dataweb.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by home on 2017/2/13.
 */
public class MonitorAop {
    private Logger logger = Logger.getLogger(MonitorAop.class);
    //任何通知方法都可以将第一个参数定义为 org.aspectj.lang.JoinPoint类型
    public void before(JoinPoint call) {
        //获取目标对象对应的类名
        String className = call.getTarget().getClass().getName();
        //获取目标对象上正在执行的方法名
        String methodName = call.getSignature().getName();
        System.out.println("前置通知:" + className + "类的" + methodName + "方法开始了");
    }

    public void afterReturn() {
        System.out.println("后置通知:方法正常结束了");
    }

    public void after() {
        System.out.println("最终通知:不管方法有没有正常执行完成，一定会返回的");
    }

    public void afterThrowing(JoinPoint call) {
        System.out.println("异常抛出后通知:方法执行时出异常了" +call.getTarget() + "--"+call.toString()+ "---" );
    }

    //用来做环绕通知的方法可以第一个参数定义为org.aspectj.lang.ProceedingJoinPoint类型
    public Object doAround(ProceedingJoinPoint call) throws Throwable {
        Object result = null;
//        this.before(call);//相当于前置通知
        try {
            result = call.proceed();
//            System.out.println("--------------" + call.getTarget());
//            this.afterReturn(); //相当于后置通知
        } catch (Throwable e) {
//            this.afterThrowing(call);  //相当于异常抛出后通知
//            System.out.println(e +"---------" + call.toString());
            String msg =  getStackMsg(e);
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            HttpServletRequest request = sra.getRequest();

            EmailUtil.warnEveryOne(call.toString() + " 出现异常: " +msg +"--user="+getUserInfo(request,"dataUser")+"---"+getUserInfo(request,"dataUserName"));
            logger.error(getStackMsg(e));
            throw e;

        } finally {
//            this.after();  //相当于最终通知
        }
        return result;
    }
    /*程序发生异常记录堆栈信息*/
    private String getStackMsg(Throwable e) {

        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackArray = e.getStackTrace();
        for (int i = 0; i < stackArray.length; i++) {
            StackTraceElement element = stackArray[i];
            sb.append(element.toString()).append("\r\n");
        }
        return sb.toString();
    }

    private String getUserInfo(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();//获取cookie数组
        String username = null;
        if (null!=cookies) {
            for(Cookie cookie : cookies){
                if(cookieName.equalsIgnoreCase(cookie.getName())){
                    username = cookie.getValue();
                }
            }
        }
        return username;
    }
}
