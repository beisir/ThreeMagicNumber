package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.PageModel;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface BaseDao<T, PK extends Serializable>{
		/**
		 * 保存新增对象.
		 */
		public PK insert(final T entity) throws Exception;
		public PK insertInto(T entity) throws Exception;

		/**
		 * 保存新增对象列表.
		 */
		public List<PK> insert(final List<T> entitys) throws Exception;

		/**
		 * 保存新增对象.
		 */
		public Object insert(final String ql, final Object... values)
				throws Exception;

		/**
		 * 删除对象.
		 *
		 * @param entity
		 *            对象必须是session中的对象或含id属性的transient对象.
		 */
		public int delete(final T entity) throws Exception;

		/**
		 * 删除对象列表.
		 *
		 * @param entity
		 *            对象必须是session中的对象或含id属性的transient对象.
		 */
		public int delete(final List<T> entitys) throws Exception;

		/**
		 * 删除对象.
		 *
		 * @param values
		 *            删除语句中的参数.
		 */
		public int delete(final String ql, final Object... values) throws Exception;

		/**
		 * 保存修改的对象.
		 *
		 * * @param entity 对象必须是session中的对象或含id属性的transient对象.
		 */
		public int update(final T entity) throws Exception;
		public int update(final String ql,T entity) throws Exception;
		/**
		 * 保存修改的对象列表.
		 */
		public int update(final List<T> entitys) throws Exception;

		/**
		 * 保存修改的对象.
		 */
		public int update(final String ql, final Object... values) throws Exception;

		/**
		 * 按id获取对象.
		 */
		public T get(final PK id) throws Exception;

		/**
		 * 获取对象.
		 */
		public Object get(final String ql, final Object... values) throws Exception;

		/**
		 * 获取对象无参数.
		 * @throws Exception
		 */
		public List<T> findbysql(final String ql) throws Exception;
		/**
		 * 查询对象列表.
		 *
		 * @return List<T> 查询结果对象列表
		 *
		 * @param T
		 *            参数对象.
		 */
		public List<T> find(T entity) throws Exception;

		/**
		 * 查询对象列表的数量.
		 *
		 * @return 查询结果的数量
		 *
		 * @param T
		 *            参数对象.
		 */
		public long findCount(T entity) throws Exception;
		public  Object findCounts(final String ql,final T entity) throws Exception;
		/**
		 * 查询对象列表.
		 *
		 * @return List<X> 查询结果对象列表
		 *
		 * @param ql
		 * @param values
		 *            参数对象.
		 */
		public <X> List<X> find(final String hql, final Object... values)
				throws Exception;

		/**
		 * 查询对象列表的数量.
		 *
		 * @return 查询结果的数量
		 *
		 * @param ql
		 * @param values
		 *            参数对象.
		 */
		public long findCount(final String ql, final Object... values)
				throws Exception;

		/**
		 *
		 * @param entity
		 * @param start
		 * @param size
		 * @return
		 * @throws Exception
		 * @Description:分页查询数据
		 */
		public PageModel findByPage(final T entity, int start, int size) throws Exception;
		/**
		 * @param sql 基础查询语句
		 * @param entity
		 * @param start
		 * @param size
		 * @return
		 * @throws Exception
		 */
		public PageModel findByPage( final String sql,final T entity,int start ,int size) throws Exception;
		public PageModel findByPageV( final String sql,final T entity,String keyword,int start ,int size)throws Exception;

}
