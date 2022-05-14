 package cn.twr.mvcDemo.service;

import java.sql.Connection;
import java.util.List;

import cn.twr.mvcDemo.dao.FactoryDao;
import cn.twr.mvcDemo.dao.UserDao;
import cn.twr.mvcDemo.model.User;
import cn.twr.mvcDemo.utils.JdbcUtils;

public class UserServiceImpl implements UserService {
	
	//工厂模式来获得UserDaoImpl
	UserDao userDao = FactoryDao.getUserDao();

	@Override
	public int saveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public int deleteUserById(int id) {
		return userDao.deleteUserById(id);
	}

	@Override
	public int updateUserById(User user) {
		return userDao.updateUserById(user);
	}

	@Override
	public User getOneUserById(int id) {
		return userDao.getOneUserById(id);
	}

	@Override
	public User getOneUserTransation(int id) {
		Connection conn = null;
		User user = null;
		try {
			conn = JdbcUtils.getConnection();
			conn.setAutoCommit(false);//开启事务
			user = userDao.getOneUser(conn, id);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			JdbcUtils.rollbackTransation(conn);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@Override
	public long getCountByName(String username) {
		return userDao.getCountByName(username);
	}
	
	/**
	 * 用户模糊查询方法
	 * @return 
	 */
	@Override
	public List<User> query(String username, String phoneNo, String sex) {
		return userDao.query(username,phoneNo,sex);
	}

	@Override
	public User loginByUp(String username, String password) {
		
		return userDao.loginByUp(username,password);
	}

}
