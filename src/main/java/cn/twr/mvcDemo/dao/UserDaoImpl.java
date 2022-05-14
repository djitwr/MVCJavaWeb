package cn.twr.mvcDemo.dao;

import java.sql.Connection;
import java.util.List;

import cn.twr.mvcDemo.model.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public int saveUser(User user) {
		String sql ="INSERT INTO `user` (username,pasword,sex,phone_no,reg_date) VALUES (?,?,?,?,?)";
		return super.update(sql, user.getUsername(),user.getPasword(),user.getSex(),user.getPhoneNo(),user.getRegDate());
	}

	@Override
	public int deleteUserById(int id) {
		String sql = "DELETE FROM user WHERE id=?;";
		return super.update(sql, id);
	}

	@Override
	public int updateUserById(User user) {
		String sql = "UPDATE `user` set username=?,pasword=?,sex=?,phone_no=? WHERE id=?";
		return super.update(sql, user.getUsername(),user.getPasword(),user.getSex(),user.getPhoneNo(),user.getId());
	}

	/**
	 * 不支持事务的
	 */
	@Override
	public User getOneUserById(int id) {
		String sql = "SELECT id,username,pasword,sex,phone_no phoneNo,reg_date regDate FROM `user` WHERE id=?;";
		return super.get(sql, id);
	}
	
	/**
	 * 支持事务的
	 */
	@Override
	public User getOneUser(Connection conn, int id) {
		String sql = "SELECT id,username,pasword,sex,phone_no phoneNo,reg_date regDate FROM `user` WHERE id=?;";
		return super.get(conn, sql, id);
	}

	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT id,username,pasword,sex,phone_no phoneNo,reg_date regDate FROM `user`;";
		return super.getList(sql);
	}

	@Override
	public long getCountByName(String username) {
		String sql ="SELECT COUNT(id) FROM `user` WHERE username=?;";
		return (long) super.getValue(sql, username);
	}
	
	/**
	 * 模糊查询
	 */
	@Override
	public List<User> query(String username, String phoneNo, String sex) {
		String sql = "SELECT id,username,pasword,sex,phone_no phoneNo,reg_date regDate FROM `user` WHERE 1=1";
		if(username !=null && !"".equals(username)) {
			sql = sql + " AND username LIKE '%"+username+"%'";
		}
		if(phoneNo !=null && !"".equals(phoneNo)) {
			sql = sql + " AND phone_no LIKE '%"+phoneNo+"%'";
		}
		if(sex !=null && !"".equals(sex)) {
			sql = sql + " AND sex LIKE '%"+sex+"%'";
		}
		return super.getList(sql);
	}

	@Override
	public User loginByUp(String username, String password) {
		String sql = "SELECT id,username,pasword,sex,phone_no phoneNo,reg_date regDate FROM `user` WHERE username=? AND pasword=?;";
		return super.get(sql, username,password);
	}
	
}
