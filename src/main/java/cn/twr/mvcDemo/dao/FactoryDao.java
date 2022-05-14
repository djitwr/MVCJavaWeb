package cn.twr.mvcDemo.dao;

public class FactoryDao {
	
	public static UserDao getUserDao() {
		return new UserDaoImpl();
	}
}
