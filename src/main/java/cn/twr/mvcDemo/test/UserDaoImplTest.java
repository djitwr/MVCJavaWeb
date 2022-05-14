package cn.twr.mvcDemo.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import cn.twr.mvcDemo.dao.UserDao;
import cn.twr.mvcDemo.dao.UserDaoImpl;
import cn.twr.mvcDemo.model.User;
import cn.twr.mvcDemo.utils.JdbcUtils;

class UserDaoImplTest {
	
	UserDao userDao = new UserDaoImpl();
	
	@Test
	void testGetOneUser() {
		User user = userDao.getOneUserById(2);
		System.out.println(user);
	}
	
	/**
	 * 支持事务的测试
	 */
	@Test
	void testGetOneUser02() {
		Connection conn = JdbcUtils.getConnection();
		User user =null;
		try {
			conn.setAutoCommit(false);//开启事务
			user = userDao.getOneUser(conn, 1);
			conn.commit();//提交事务
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();//回滚事务
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			JdbcUtils.closeConn(conn);//关闭数据库连接
		}
		System.out.println(user);
	}

}
