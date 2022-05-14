package cn.twr.mvcDemo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 购物车简单实现
 * @author admin
 *
 */
@WebServlet(urlPatterns = {"*.pdo"})
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 拿到方法名
				String mn = req.getServletPath();
				mn = mn.substring(1, mn.length() - 4);
				System.out.println(mn);

				// 使用反射，拿到方法名自动调用下面方法
				try {
					// 1.拿到这个类的全部方法
					Method method = this.getClass().getDeclaredMethod(mn, HttpServletRequest.class, HttpServletResponse.class);
					try {
						// 2.使用invoke方法直接调用本类的方法
						method.invoke(this, req, resp);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
	}
	
	/**
	 * 购物车
	 * 点击商品跳转到商品详情页
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void shopping(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pname = (String) req.getParameter("pname");
		req.setAttribute("p", pname);
		req.getRequestDispatcher("/productInfo.jsp").forward(req, resp);
		
	}
	
	/**
	 * 添加商品到购物车
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void shoppingCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pname = req.getParameter("pname");
		HttpSession session = req.getSession();
		
		List<String> list = (List<String>) session.getAttribute("car");
		
		if(list==null) {
			list = new ArrayList<String>();
		}
		list.add(pname);

		session.setAttribute("car", list);
		req.getRequestDispatcher("/shoppingCar.jsp").forward(req, resp);	
	}
	
}
