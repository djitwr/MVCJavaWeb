package cn.twr.mvcDemo.dao;

import java.sql.Connection;
import java.util.List;

import cn.twr.mvcDemo.model.User;

/**
 * User增删改查接口
 * @author admin
 *
 */
public interface UserDao {
	
	/**
	 * 增加用户信息
	 * @param user
	 * @return
	 */
	public int saveUser(User user);
	
	/**
	 * 根据用户id删除对应用户信息
	 * @param id
	 * @return
	 */
	public int deleteUserById(int id);
	
	/**
	 * 根据用户id修改用户信息
	 * 传过来的信息为user信息
	 * @param user
	 * @return
	 */
	public int updateUserById(User user);
	
	/**
	 * 不支持事务的
	 * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	public User getOneUserById(int id);
	
	/**
	 * 支持事务的
	 * 根据用户id查询用户信息
	 * @param id
	 * @return
	 */
	public User getOneUser(Connection conn, int id);
	
	/**
	 * 查询全部用户信息
	 * @return
	 */
	public List<User> getAllUsers();
	
	/**
	 * 根据指定用户名查询有多少条
	 * @param username
	 * @return
	 */
	public long getCountByName(String username);//需要使用long型，BaseDao中Object转换到int会出现类型转换异常，所以使用long
	
	/**
	 * 模糊查询
	 * @param username
	 * @param phoneNo
	 * @param sex
	 * @return
	 */
	public List<User> query(String username, String phoneNo, String sex);
	
	/**
	 * 根据用户名和密码验证登陆
	 * @param username
	 * @param password
	 * @return
	 */
	public User loginByUp(String username, String password);
	
}
