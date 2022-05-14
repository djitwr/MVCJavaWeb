package cn.twr.mvcDemo.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.twr.mvcDemo.model.User;
import cn.twr.mvcDemo.service.FactoryService;
import cn.twr.mvcDemo.service.UserService;
import cn.twr.mvcDemo.utils.CaptchaUtils;
import cn.twr.mvcDemo.utils.CookieUtils;

@WebServlet(urlPatterns = { "*.udo" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserService userService = FactoryService.getUserService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost方法");
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
	 * 验证码相关的方法，基于session
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void drawCheckCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/jpg");//设置响应类型
		int width = 135;
		int heigth = 45;
		//具体验证码
		CaptchaUtils captcha = CaptchaUtils.getInCaptchaUtils();
		captcha.set(width, heigth);
		String cc = captcha.generateCheckcode();
		HttpSession session = req.getSession();//拿到session
		session.setAttribute("checkCode", cc);//将验证码字符注入session中
		OutputStream out = resp.getOutputStream();//从resp中拿到一个OutputStream
		//通过OutputStream的形式响应给浏览器
		ImageIO.write(captcha.generateCheckImg(cc), "jpg", out);
	}
	
	/**
	 * 使用Cookie登陆验证
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		// 第一步：先拿到3个参数
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String expiredays = req.getParameter("expiredays");
		Cookie[] cookies = req.getCookies();
		String checkCode = req.getParameter("checkCode");

		boolean login = false;// 是否登陆成功的标识 false：没有登陆成功 ture：登陆成功
		String account = null;// 登陆账户
		String ssid = null;// 一个标记，判断用户是否该成功登陆的标识

		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userKey")) {
					account = cookie.getValue();
				}
				if (cookie.getName().equals("ssid")) {
					ssid = cookie.getValue();
				}
			}
		}

		if (account != null && ssid != null) {
			login = ssid.equals(CookieUtils.md5Encrypt(username));// 相等则login为ture
		}

		System.out.println("login===" + login);

		// 用户没有登陆过，没有cookies
		if (!login) { // login:false 取反：ture
			//没有登陆过则需要输入验证码
			HttpSession session = req.getSession();
			String scc =(String) session.getAttribute("checkCode");
			if(!checkCode.equals(scc)) {
				req.setAttribute("note", "验证码输入错误，请重新输入...");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
				return; //如果验证码错误，则不再查询数据库信息等下面代码
			}
			
			User user = userService.loginByUp(username, password);
			System.out.println("loginByUp:" + user);
			if (user != null) {
				expiredays = expiredays == null ? "" : expiredays;
				switch (expiredays) {
				case "7":
					CookieUtils.createCookie(username, req, resp, 7 * 24 * 60 * 60);
					break;
				case "15":
					CookieUtils.createCookie(username, req, resp, 15 * 24 * 60 * 60);
				case "30":
					CookieUtils.createCookie(username, req, resp, 30 * 24 * 60 * 60);
				default:
					CookieUtils.createCookie(username, req, resp, -1);// -1:即不设置cookie
					break;
				}
				// 运行了上面代码，即可以登陆成功，则跳转到mian.js页面
				System.out.println("[无cookie]登陆成功了...");
				req.getSession().setAttribute("user", user.getUsername());
				req.getRequestDispatcher("/main.jsp").forward(req, resp);
			} else {
				req.setAttribute("note", "用户名或密码错误，请重新登陆！");
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		}else {
			// login为ture时：客户端ssid的cookie和查询的cookie相等->登陆成功，则跳转到main.jsp页面
			System.out.println("[有cookie]登陆成功了...");
			req.getSession().setAttribute("user", username);
			req.getRequestDispatcher("/main.jsp").forward(req, resp);
		}

	}
	
	/**
	 * 注销用户
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void loginOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//删除Cookie(将MaxAge设置为0)
		Cookie[] cookies = req.getCookies();
		if(cookies!=null && cookies.length > 0) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("userKey")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);//将cookie添加到客服端
				}
				if(cookie.getName().equals("ssid")) {
					cookie.setMaxAge(0);
					resp.addCookie(cookie);
				}
			}
		}
		//删除Session
		HttpSession session = req.getSession();
		if(session != null) {
			session.removeAttribute("user");
		}
		//都删除成功，跳转到login.jsp
		resp.sendRedirect(req.getContextPath()+"/login.jsp");
	}
	

	private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String zcusername = req.getParameter("username");
		long count = userService.getCountByName(zcusername);
		System.out.println("[添加方法前的]zcusername："+zcusername);
		System.out.println("ADD方法中,查询注册用户名记录条数count=" + count);
		if (count > 0) {
			req.setAttribute("note", zcusername + ",该用户名已经被占用，请更换一个...");
			// 转发注册页面
			System.out.println("来到了注册名重复逻辑");
			req.getRequestDispatcher("/addUser.jsp").forward(req, resp);
			return;
		}
		
		System.out.println("========="+zcusername);
		if (zcusername != null && !"".equals(zcusername)) {
			User user = new User();
			System.out.println("[添加方法中的]zcusername："+zcusername);
			user.setUsername(zcusername);
			user.setPasword(req.getParameter("password"));
			user.setPhoneNo(req.getParameter("phoneNo"));
			user.setSex(req.getParameter("sex"));
			user.setRegDate(new Date());
			System.out.println("注册的用户信息user" + user);
			int rows = userService.saveUser(user);
			System.out.println("注册用户方法返回结果rows：" + rows);
			if (rows > 0) {
				resp.sendRedirect(req.getContextPath() + "/main.jsp");
			} else {
				resp.sendRedirect(req.getContextPath() + "/error.jsp");
			}
		}

	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));// Pararmeter中拿到的为String类型，需要使用Integer.parseInt("String")转成int类型
		int rows = userService.deleteUserById(id);
		if (rows > 0) {
			resp.sendRedirect(req.getContextPath() + "/main.jsp");
		} else {
			resp.sendRedirect(req.getContextPath() + "/error.jsp");
		}
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(req.getParameter("id"));
		User user = userService.getOneUserById(id);
		// 注入并转发到updateUser.jsp中去
		req.setAttribute("user", user);
		req.getRequestDispatcher("/updateUser.jsp").forward(req, resp);
	}

	private void updatedo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(req.getParameter("id"));
		System.out.println("ID:" + id);
		System.out.println("来到了updatedo方法发内...");
		// 拿到修改前的user信息
		User user = userService.getOneUserById(id);
		System.out.println("通过id拿到的原user===" + user);
		// 拿到原username
		String yusername = user.getUsername();
		// 修改后的username
		String xusername = req.getParameter("username");
		System.out.println("①查询数据库前xusername:" + xusername);
		// 查询修改后的username在原数据中存在的记录条数
		long count = userService.getCountByName(xusername);
		System.out.println("count====" + count);
		System.out.println("②查询数据库后xusername:" + xusername);
		if ((!xusername.equals(yusername)) && count > 0) {
			req.setAttribute("note", ",该用户名已经被占用，请更换一个...");
			// 转发
			req.getRequestDispatcher("/update.udo?id="+id).forward(req, resp);
			return;// 结束方法，下面的代码不会执行！
		}

		if (xusername != null && !"".equals(xusername)) {
			user.setUsername(xusername);
			user.setPasword(req.getParameter("password"));
			user.setPhoneNo(req.getParameter("phoneNo"));
			user.setSex(req.getParameter("sex"));
			System.out.println("最后修改后的user:" + user);
			System.out.println("update前获取user的Id:" + user.getId());
			int rows = userService.updateUserById(user);
			System.out.println("rows====" + rows);
			if (rows > 0) {
				resp.sendRedirect(req.getContextPath() + "/main.jsp");// 重定向
			} else {
				resp.sendRedirect(req.getContextPath() + "/error.jsp");// 重定向
			}
		}

	}

	/**
	 * 模糊查询的方法
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void query(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String username = req.getParameter("username");
		String phoneNo = req.getParameter("phoneNo");
		String sex = req.getParameter("sex");
		// 使用正则表达四忽略全部特殊字符
		// 符合正则表达式的用""替换
		username = username.replaceAll(
				"[\\\\s~·`!！@#￥$%^……&*（()）\\\\-——\\\\-_=+【\\\\[\\\\]】｛{}｝\\\\|、\\\\\\\\；;：:‘'“”\\\"，,《<。.》>、/？?]", "");
		List<User> list = userService.query(username, phoneNo, sex);
		System.out.println("UserController返回的信息：" + list);
		// 将查询的list放入属性空间
		req.setAttribute("userList", list);
		// 转发到index.jsp
		req.getRequestDispatcher("/main.jsp").forward(req, resp);
	}

}
