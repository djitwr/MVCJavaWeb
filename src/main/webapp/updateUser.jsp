<%@page import="cn.twr.mvcDemo.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>编辑用户信息</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/css/layui.css"
	tppabs="${pageContext.request.contextPath}/layui/css/layui.css"
	media="all">
<script src="${pageContext.request.contextPath}/layui/layui.js"
	charset="utf-8"></script>
</head>
<body>
	<ul class="layui-nav" lay-filter="">
		<li class="layui-nav-item"><a
			href="<%=request.getContextPath()%>/addUser.jsp">注册用户</a></li>
		<li class="layui-nav-item layui-this"><a
			href="<%=request.getContextPath()%>/main.jsp">用户管理</a></li>
		<li class="layui-nav-item"><a href="javascript:;">其他操作</a>
			<dl class="layui-nav-child">
				<!-- 二级菜单 -->
				<dd>
					<a href="#">模块1</a>
				</dd>
				<dd>
					<a href="#">模块2</a>
				</dd>
				<dd>
					<a href="#">模块3</a>
				</dd>
			</dl>
	</ul>

	<script>
		//注意：导航 依赖 element 模块，否则无法进行功能性操作
		layui.use('element', function() {
			var element = layui.element;

			//…
		});
	</script>

	<br/>
	<br/>
	<%
	User user = (User) request.getAttribute("user"); 
	String note = (String)request.getParameter("note");
	out.println("===note==="+note);
	%>

	<!-- 更新用户信息表单 -->
	<form class="layui-form"
		action="<%=request.getContextPath()%>/updatedo.udo?id=<%=user.getId()%>"
		method="post">
		<div class="layui-form-item">
			<label class="layui-form-label">用户ID</label>
			<div class="layui-input-block">
				<input type="text" name="id" value="<%=user.getId()%>"
					lay-verify="required" placeholder="" autocomplete="off"
					class="layui-input" disabled>
			</div>
		</div>

		<div class="layui-form-item">
			<label class="layui-form-label">提示信息</label>
			<div class="layui-input-block">
				<input type="text" style="color: red;" name="" value="<%=note %>" lay-verify="required"
					placeholder="" autocomplete="off" class="layui-input">
			</div>
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				<input type="text" name="username" value="<%=user.getUsername()%>"
					lay-verify="required" placeholder="请输入新用户名" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"> 密码</label>
			<div class="layui-input-block">
				<input type="text" name="password" value="<%=user.getPasword()%>"
					lay-verify="required" placeholder="请输入新密码" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"> 电话</label>
			<div class="layui-input-block">
				<input type="text" name="phoneNo" value="<%=user.getPhoneNo()%>"
					lay-verify="required" placeholder="请输入新手机号" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"> 性别</label>
			<div class="layui-input-block">
				<input type="radio" name="sex" value="男" title="男"> <input
					type="radio" name="sex" value="女" title="女" checked>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<input type="submit" class="layui-btn" value="提交修改" />
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>
</body>
</html>