/**  
* @Title: SpringContextHolder.java
* @Package com.hc360.util
* @Description: TODO(��һ�仰�������ļ���ʲô)
* @author zc   
* @date 2014-6-13 ����5:44:06
* @version V1.0  
*/
 
package com.hc360.dataweb.util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * @ClassName: SpringContextHolder
 * @Description:
 * @author zc
 * @date 2014-6-13 下午5:44:06
 *
 */
public class SpringContextHolder implements ApplicationContextAware {

	/**
	 * 以静态变量保存ApplicationContext,可在任意代码中取出ApplicaitonContext.
	 */
	private static ApplicationContext context;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext context) {
		SpringContextHolder.context =context;
	}
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) context.getBean(name);
	}
}
