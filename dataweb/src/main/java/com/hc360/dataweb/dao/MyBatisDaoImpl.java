package com.hc360.dataweb.dao;

import com.hc360.dataweb.model.PageModel;
import com.hc360.dataweb.util.Reflections;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class MyBatisDaoImpl<T, PK extends Serializable> extends
		SqlSessionDaoSupport implements BaseDao<T, PK> {
	private  Logger logger = Logger.getLogger(MyBatisDaoImpl.class);
	@Autowired
	protected SqlSessionFactory sessionFactory;

	/**
	 */
	protected Class<T> entityClass;

	/**
	 */
	protected Class<PK> pkClass;

	public String sqlMapNamespace = null;

	public static final String POSTFIX_INSERT = "insert";
	public static final String POSTFIX_INSERTINTO = "insertInto";

	public static final String POSTFIX_UPDATE = "update";

	public static final String POSTFIX_DELETE = "delete";

	public static final String POSTFIX_GET = "get";

	public static final String POSTFIX_SELECT = "find";

	public static final String POSTFIX_SELECT_COUNT = "findCount";

	public static final String POSTFIX_SELECTPAGE = "findByPage";

	public static final String POSTFIX_SELECTPAGE_COUNT = "findByPageCount";

	/**
	 * SimpleHibernateDao<User, Long>
	 */
	public MyBatisDaoImpl() {
		this.entityClass = Reflections.getSuperClassGenricType(getClass());
		this.pkClass = Reflections.getSuperClassGenricType(getClass(), 1);
		this.sqlMapNamespace = entityClass.getName();
	}

	@Resource(name = "sqlSessionFactory")
	public void setSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	public String getSqlMapNamespace() {
		return sqlMapNamespace;
	}

	public PK insert(T entity) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		int num = getSqlSession().insert(
				sqlMapNamespace + "." + POSTFIX_INSERT, entity);
		return pkClass.getConstructor(String.class).newInstance(
				String.valueOf(num));
	}
	
	public PK insertInto(T entity) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		int num = getSqlSession().insert(
				sqlMapNamespace + "." + POSTFIX_INSERTINTO, entity);
		return pkClass.getConstructor(String.class).newInstance(
				String.valueOf(num));
	}
	public List<PK> insert(List<T> entitys) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<PK> pkList = new ArrayList<PK>();
		for (T e : entitys)
			pkList.add(null == e ? null : insert(e));
		return pkList;
	}

	public Object insert(final String ql, final Object... values) throws Exception {
		int num = 0;
		if (values != null && values.length>0) {
			for (int i = 0; i < values.length; i++) {
				num += getSqlSession().insert(sqlMapNamespace + "." + ql,
						values[i]);
			}
		} else {
			num += getSqlSession().insert(sqlMapNamespace + "." + ql);
		}
		return num;
	}

	public int delete(T entity) throws Exception {
		return getSqlSession().delete(sqlMapNamespace + "." + POSTFIX_DELETE,
				entity);
	}

	public int delete(List<T> entitys) throws Exception {
		int rowsEffected = 0;
		for (T e : entitys)
			rowsEffected += null == e ? 0 : delete(e);
		return rowsEffected;
	}

	public int delete(final String ql, final Object... values) throws Exception{
		int num = 0;
		if (values != null && values.length>0) {
			for (int i = 0; i < values.length; i++) {
				num += getSqlSession().delete(sqlMapNamespace + "." + ql,
						values[i]);
			}
		} else {
			num += getSqlSession().delete(sqlMapNamespace + "." + ql);
		}
		return num;
	}

	public int update(T entity) throws Exception{
		getSqlSession().update(sqlMapNamespace + "." + POSTFIX_UPDATE, entity);
		return 1;
	}
	
	public int update(final String ql,T entity) throws Exception{
		getSqlSession().update(sqlMapNamespace + "." + ql, entity);
		return 1;
	}
	public int update(List<T> entity) throws Exception {
		int rowsEffected = 0;
		for (T e : entity)
			rowsEffected += null == e ? 0 : update(e);
		return rowsEffected;
	}

	public int update(final String ql, final Object... values) throws Exception{
		int num = 0;
		if (values != null && values.length>0) {
			for (int i = 0; i < values.length; i++) {
				num += getSqlSession().update(sqlMapNamespace + "." + ql,
						values[i]);
			}
		} else {
			num += getSqlSession().update(sqlMapNamespace + "." + ql);
		}
		return num;
	}

	public T get(final PK id) throws Exception{
		return (T) getSqlSession().selectOne(
				sqlMapNamespace + "." + POSTFIX_GET, id);
	}

	public Object get(final String ql, final Object... values) throws Exception{
		if (values != null && values.length>0) {
			Object result = null;
			for (int i = 0; i < values.length; i++) {
				result = getSqlSession().selectOne(sqlMapNamespace + "." + ql,
						values[i]);
			}
			return result;
		} else {
			return getSqlSession().selectOne(sqlMapNamespace + "." + ql);
		}
	}

	public List<T> find(final T entity) throws Exception{
		return getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECT, entity);
	}

	public final long findCount(final T entity) throws Exception{
		return getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECT, entity).size();
	}
	
	public  Object findCounts(final String ql,final T entity) throws Exception{
		  Object selectOne = getSqlSession().selectOne(sqlMapNamespace + "." + ql, entity);
		return selectOne;
	}

	public List<T> find(final String ql, final Object... values) throws Exception{
		if (values != null && values.length>0) {
			List<T> result = null;
			for (int i = 0; i < values.length; i++) {
				result = getSqlSession().selectList(sqlMapNamespace + "." + ql,values[i]);
			}
			return result;
		} else {
			return getSqlSession().selectList(sqlMapNamespace + "." + ql);
		}
	}
	
	public List<T> findbysql(final String ql) throws Exception{
		
			return getSqlSession().selectList(sqlMapNamespace + "." + ql);
	}

	public long findCount(final String ql, final Object... values) throws Exception {
		Long result = null;
		if (values != null && values.length>0) {
			for (int i = 0; i < values.length; i++) {
				result = (Long) getSqlSession().selectOne(sqlMapNamespace + "." + ql, values[i]);
			}
		} else {
			result = (Long) getSqlSession().selectOne(sqlMapNamespace + "." + ql);
		}
		return result.longValue();
	}
	public PageModel findByPage( final T entity,int start ,int size) throws Exception {
		PageModel page = new PageModel();
		RowBounds rowBounds = new RowBounds((start - 1) * size, size);
		page.setRows(getSqlSession().selectList(sqlMapNamespace + "." + POSTFIX_SELECTPAGE, entity, rowBounds));
		page.setTotal(findCount(POSTFIX_SELECTPAGE_COUNT, entity));
		return page;
	}
	
	public PageModel findByPage( final String sql,final T entity,int start ,int size) throws Exception {
		PageModel page = new PageModel();
		RowBounds rowBounds = new RowBounds((start - 1) * size, size);
		page.setRows(getSqlSession().selectList(sqlMapNamespace + "." + sql, entity, rowBounds));
		page.setTotal(findCount(POSTFIX_SELECTPAGE_COUNT, entity));
		return page;
	}
	
	public PageModel findByPageV( final String sql,final T entity,String keyword,int start ,int size) throws Exception {
		PageModel page = new PageModel();
		RowBounds rowBounds = new RowBounds((start - 1) * size, size);
		page.setRows(getSqlSession().selectList(sqlMapNamespace + "." + sql, entity, rowBounds));
		page.setTotal(findCount(POSTFIX_SELECTPAGE_COUNT, entity,keyword));
		return page;
	}
	
}
