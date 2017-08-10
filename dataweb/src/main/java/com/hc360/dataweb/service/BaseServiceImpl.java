package com.hc360.dataweb.service;

import java.io.Serializable;
import java.util.List;

import com.hc360.dataweb.dao.BaseDao;
import com.hc360.dataweb.model.PageModel;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T, PK> {
	private  Logger logger = Logger.getLogger(BaseServiceImpl.class);

	public abstract BaseDao<T, PK> getDao();

	public PK insert(T entity) throws Exception {
		try{
			return getDao().insert(entity);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public List<PK> insert(List<T> entitys) throws Exception {
		try{
			return getDao().insert(entitys);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public Object insert(String ql, Object... values) throws Exception {
		try{
			return getDao().insert(ql, values);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public int delete(T entity) throws Exception {
		try{
			return getDao().delete(entity);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public int delete(List<T> entitys) throws Exception {
		try{
			return getDao().delete(entitys);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public int delete(String ql, Object... values) throws Exception {
		try{
			return getDao().delete(ql, values);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public int update(T entity) throws Exception {
		try{
			return getDao().update(entity);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public int update(List<T> entitys) throws Exception {
		try{
			return getDao().update(entitys);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public int update(String ql, Object... values) throws Exception {
		try{
			return getDao().update(ql, values);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public T get(PK id) throws Exception {
		try{
			return getDao().get(id);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public Object get(String ql, Object... values) throws Exception {
		try{
			return getDao().get(ql, values);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public List<T> find(T entity) throws Exception {
		try{
			return getDao().find(entity);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public long findCount(T entity) throws Exception {
		try{
			return getDao().findCount(entity);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public List<T> find(String ql, Object... values) throws Exception {
		try{
			return getDao().find(ql, values);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public long findCount(String ql, Object... values) throws Exception {
		try{
			return getDao().findCount(ql, values);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}

	public PageModel findByPage(final T entity, int start, int size) throws Exception{
		try{
			return getDao().findByPage(entity,start,size);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}
	public PageModel findByPage( final String sql,final T entity,int start ,int size) throws Exception{
		try{
			return getDao().findByPage(sql,entity,start,size);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}


	public PageModel findByPageV( final String sql,final T entity,String keyword,int start ,int size) throws Exception{
		try{
			return getDao().findByPageV(sql,entity,keyword,start,size);
		}
		catch (Exception e){
			throw new Exception("业务异常，请重试！", e);
		}
	}
}