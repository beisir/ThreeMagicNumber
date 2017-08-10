package com.hc360.dataweb.service;


import com.hc360.dataweb.model.PageModel;

import java.io.Serializable;
import java.util.List;

public abstract interface BaseService<T, PK extends Serializable> {
	public PK insert(final T entity) throws Exception;
	
	public List<PK> insert(final List<T> entitys) throws Exception;
	
	public Object insert(final String ql, final Object... values) throws Exception;
	
	public int delete(final T entity) throws Exception;
	
	public int delete(final List<T> entitys) throws Exception;
	
	public int delete(final String ql, final Object... values) throws Exception;
	
	public int update(final T entity) throws Exception;

	public int update(final List<T> entitys) throws Exception;
	
	public int update(final String ql, final Object... values) throws Exception;
	
	public T get(final PK id) throws Exception;
	
	public Object get(final String ql, final Object... values) throws Exception;

	public List<T> find(T entity) throws Exception;
	
	public long findCount(T entity) throws Exception;
	
	public List<T> find(final String ql, final Object... values) throws Exception;

	public long findCount(final String ql, final Object... values) throws Exception;
	
	public PageModel findByPage(final T entity, int start, int size) throws Exception;
	public PageModel findByPage(final String sql, final T entity, int start, int size) throws Exception;
	public PageModel findByPageV(final String sql, final T entity, String keyword, int start, int size) throws Exception;

}
