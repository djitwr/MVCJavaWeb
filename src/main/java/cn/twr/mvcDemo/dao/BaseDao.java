package cn.twr.mvcDemo.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.twr.mvcDemo.utils.JdbcUtils;

/**
 * dao类的基本类，用于被具体的dao类取继承它，不能new BaseDao()来直接用
 * @author admin
 *
 * @param <T>
 */
public class BaseDao<T> {

	private Class<T> clazz;

	/**
	 * 用BaseDao的构造方法初始化clazz属性，User-->User.calss
	 */
	@SuppressWarnings("unchecked")
	public BaseDao() { 
		//this:谁去调用BaseDao方法，this就指向谁,User
		//getGenericSuperclass方法：作用是拿到调用者的父类类型（父类-->BaseDao<T>,BaseDao<T>中T的具体类型），即User
		Type superTypr = this.getClass().getGenericSuperclass();
		if (superTypr instanceof ParameterizedType) {
			//ParameterizedType（为拿到class需要里面的一个方法）：Type一个子接口类型ParameterizedType，强转为子接口类型
			ParameterizedType pt = (ParameterizedType) superTypr;
			Type[] tarry = pt.getActualTypeArguments();//getActualTypeArguments方法:返回一个类型数组，第一个元素就是我们要的class，User.class
			if(tarry[0] instanceof Class) {
				clazz = (Class<T>) tarry[0];
			}
		}
	}

	//用到dbutils工具类的queryRunner方法
	QueryRunner queryRunner = new QueryRunner();

	/**
	 * 获取一条记录的通用方法
	 * 不支持事务
	 * 查询数据表，取出sql语句查询的结果集的第一条数据，封装成一个类的对象返回，用到dbutils工具类的queryRunner方法
	 * 
	 * @param sql
	 * @param args
	 */
	public T get(String sql, Object... args) {
		Connection conn = null;
		T entity = null;
		try {
			// 拿到conn
			conn = JdbcUtils.getConnection();
			entity = queryRunner.query(conn, sql, new BeanHandler<T>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeConn(conn);
		}
		return entity;
	}

	/**
	 * 获取一条记录的通用方法
	 * 支持事务
	 * 查询数据表，取出sql语句查询的结果集的第一条数据，封装成一个类的对象返回， 用到dbutils工具类的queryRunner方法
	 * 
	 * @param sql
	 * @param args
	 */
	public T get(Connection conn, String sql, Object... args) {
		T entity = null;
		try {
			entity = queryRunner.query(conn, sql, new BeanHandler<T>(clazz), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 获取多条记录的通用方法
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getList(String sql,Object... args){
		Connection conn = null;
		List<T> list = null;
		try {
			conn = JdbcUtils.getConnection();
			list = queryRunner.query(conn, sql, new BeanListHandler<>(clazz) , args);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return list;
	}
	
	/**
	 * 增、删、改通用方法
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(String sql,Object... args) {
		Connection conn = null;
		int rows = 0;
		try {
			conn = JdbcUtils.getConnection();
			rows = queryRunner.update(conn, sql, args);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return rows;
	}
	
	/**
	 * 通用的返回sql语句的结果只有一个值的查询方法，例如用户个数：count(id)
	 * @param sql
	 * @param args
	 * @return
	 */
	public Object getValue(String sql,Object... args) {
		Connection conn = null;
		Object obj = null;
		try {
			conn = JdbcUtils.getConnection();
			obj = queryRunner.query(conn, sql, new ScalarHandler<Object>(),args);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			JdbcUtils.closeConn(conn);
		}
		return obj;
	}
	
}
