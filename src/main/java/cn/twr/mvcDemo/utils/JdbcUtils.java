package cn.twr.mvcDemo.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * jdbc工具类
 * @author admin
 *
 */
public class JdbcUtils {
	
	//c3p0数据库连接池
	private static DataSource dataSource = null;
	static { //静态代码块，只会创建一次,使用c3p0的ComboPooledDataSource方法
		dataSource = new ComboPooledDataSource("mysql");
	}
	
	/**
	 *获取到数据库mysql的数据库连接对象conn
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConn(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void rollbackTransation(Connection conn) {
		if(conn != null) {
			try {
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
