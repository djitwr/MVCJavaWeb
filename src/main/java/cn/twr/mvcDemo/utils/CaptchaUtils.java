package cn.twr.mvcDemo.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 生成验证码的工具类
 * @author admin
 *
 */
public class CaptchaUtils {
	private int width;
	private int heigth;
	private int num;
	private String code;//字典
	private static final Random ran = new Random();//使用静态变量创建一盒随机数对象ran,全局只会创建一个ran
	
	private static CaptchaUtils captcha;//使用静态变量初始化captcha
	private CaptchaUtils() {//使用构造方法初始化code字典和生成的验证码数量
		code = "0123456789abcdefghijklmnopq";
		num = 4;
	}
	
	//单例模式获取实例的方法，提供一个外部类获得CaptchaUtil实例的方法
	public static CaptchaUtils getInCaptchaUtils() {
		if(captcha==null) captcha = new CaptchaUtils();
		return captcha;
	}
	
	/**
	 * 提供一个设置宽高属性的方法
	 * @param width
	 * @param heigth
	 */
	public void set(int width,int heigth) {
		this.width = width;
		this.heigth = heigth;
	}
	
	/**
	 * 提供一个设置多个属性的方法
	 * @param width
	 * @param heigth
	 * @param num
	 * @param code
	 */
	public void set(int width,int heigth,int num,String code) {
		this.width = width;
		this.heigth = heigth;
		this.setNum(num);
		this.setCode(code);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static CaptchaUtils getCaptcha() {
		return captcha;
	}

	public static void setCaptcha(CaptchaUtils captcha) {
		CaptchaUtils.captcha = captcha;
	}
	
	/**
	 * 生成验证码的方法
	 * @return
	 */
	public String generateCheckcode() {
		StringBuffer cc = new StringBuffer();
		for(int i =0;i<num;i++) {
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();	
	}
	
	/**
	 * 画图的方法
	 * @param checkcode
	 * @return
	 */
	public BufferedImage generateCheckImg(String checkcode) {
		//创建一个图片对象											//图片的类型，RGB
		BufferedImage img = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_BGR);
		//获取图片img的画笔
		Graphics2D graphic = img.createGraphics();
		//具体的画图
//		graphic.setColor(Color.BLACK);
//		graphic.fillRect(0, 0, width-1, heigth-1); //画外框
		graphic.setColor(Color.WHITE);
		graphic.fillRect(0, 0, width, heigth);//画内框
		//设置字体
		Font font = new Font("宋体", Font.BOLD+Font.ITALIC, (int)(heigth*0.8));
		graphic.setFont(font);//font放入画笔graphic
		for(int i =0;i<num;i++) {
			//设置画笔颜色，每次随机颜色（RGB格式）
			graphic.setColor(new Color(ran.nextInt(155),ran.nextInt(255),ran.nextInt(255)));
			//将生成的验证码放入												//利用宽高设置每个checkcode的大小位置
			graphic.drawString(String.valueOf(checkcode.charAt(i)), i*(width/num)+4, (int)(heigth*0.8));
		}
		
		//加一些点drawOval方法
		for(int i=0;i<(width+heigth);i++) {
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			graphic.drawOval(ran.nextInt(width),ran.nextInt(heigth),1,1);
		}
		
		//加一些线
		for(int i =0;i<2;i++) {
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));
			graphic.drawLine(0, ran.nextInt(heigth), width, ran.nextInt(heigth));
		}
		
		return img;	
	}

}
