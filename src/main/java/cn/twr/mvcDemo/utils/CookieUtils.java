package cn.twr.mvcDemo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Cookie创建以及MD5加密工具类
 * @author admin
 *
 */
public class CookieUtils {
	/**
	 * KEY:自定义密钥
	 */
	private static final String KEY = "qwe.str123456.!abc";

	/**
	 * 创建Cookie的方法
	 * 
	 * @param username
	 * @param req
	 * @param resp
	 * @param sec
	 */
	public static void createCookie(String username, HttpServletRequest req, HttpServletResponse resp, int sec) {
		Cookie userCookie = new Cookie("userKey", username);
		Cookie ssidCookie = new Cookie("ssid", md5Encrypt(username));
		userCookie.setMaxAge(sec);
		ssidCookie.setMaxAge(sec);
		resp.addCookie(userCookie);
		resp.addCookie(ssidCookie);
	}
	
	/**
	 * 对明文进行MD5加密
	 * @param ss
	 * @return
	 */
	public static String md5Encrypt(String ss) {
		ss = KEY + (ss == null ? "" : ss);//拿到的username和KEY拼接为新的明文ss
		char[] md5Digest = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };//字典：16进制的数
		try {
			byte[] ssar = ss.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");//创建加密类MessageDigest的实例，使用MD5加密算法
			md.update(ssar);// 将明文放入放到加密类MessageDigest的实例中去
			byte[] mssarr = md.digest();// 真正的加密：明文 ->密文

			// 再一次对密文加密
			int len = mssarr.length;
			char[] str = new char[len * 2];
			int k = 0;// 记数

			for (int i = 0; i < len; i++) {
				byte b = mssarr[i];// 0101011
				str[k++] = md5Digest[b >>> 4 & 0xf];//密文mssarr位移运算后在和0xf进行'与'运算，结果作为md5Digest字典的下标
				str[k++] = md5Digest[b & 0xf];//再和0xf进行'与'运算
			}
			System.out.println("密文：" + str);
			return new String(str);//将char类型转成String类型返回
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
