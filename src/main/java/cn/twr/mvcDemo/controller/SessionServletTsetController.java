package cn.twr.mvcDemo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 防止表单重复提交
 * @author admin
 *
 */
@WebServlet("/sessionServlet")
public class SessionServletTsetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		System.out.println("[doGet]来了一次请求");
		String token = req.getParameter("token"); //拿到隐藏字段的token
		HttpSession session = req.getSession();
		String ssUuid = (String) session.getAttribute("uuid");//从session中拿到uuid,值和token一样
		System.out.println("token："+token);
		System.out.println("ssUuid："+ssUuid);
		
		session.removeAttribute("uuid");//拿到后直接删除session中的uuid
		if(token.equals(ssUuid)) {  //知识点：xx.equals(yy),xx不能为null,否则运行此代码会报错，yy可以为null
			System.out.println("正常提交！");
			resp.getWriter().println("提交成功");
		}else {
			System.out.println("重复提交！");
			req.setAttribute("note", "请勿重复提交！");
			req.getRequestDispatcher("/sessionServletTest.jsp").forward(req, resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
