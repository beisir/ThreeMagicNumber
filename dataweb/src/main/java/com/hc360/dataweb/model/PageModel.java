/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.hc360.dataweb.model;


/**
 *@Title:
 *@Description:
 *@Author:zc
 *@Since:2015-8-6
 *@Version:1.1.0
 */
public class PageModel {
	//总条数
	private long total;
	//具体的数据
	private Object rows;

	@Override
	public String toString() {
		return "PageModel [total=" + total + ", rows=" + rows + "]";
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}


}
