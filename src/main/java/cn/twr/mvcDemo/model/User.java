package cn.twr.mvcDemo.model;

import java.util.Date;

/**
 * 用户类
 * @author admin
 *
 */
public class User {
	/**
	 * 用户id
	 */
	private int id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String pasword;
	/**
	 * 用户性别
	 */
	private String sex;
	/**
	 * 用户电话
	 */
	private String phoneNo;
	/**
	 * 用户注册日期
	 */
	private Date regDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasword() {
		return pasword;
	}
	public void setPasword(String pasword) {
		this.pasword = pasword;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String username, String pasword, String sex, String phoneNo, Date regDate) {
		super();
		this.id = id;
		this.username = username;
		this.pasword = pasword;
		this.sex = sex;
		this.phoneNo = phoneNo;
		this.regDate = regDate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", pasword=" + pasword + ", sex=" + sex + ", phoneNo="
				+ phoneNo + ", regDate=" + regDate + "]";
	}
	
}
