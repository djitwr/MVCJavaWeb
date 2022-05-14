package cn.twr.mvcDemo.service;

public class FactoryService {

	public static UserService getUserService() {
		return new UserServiceImpl();
	}

}
