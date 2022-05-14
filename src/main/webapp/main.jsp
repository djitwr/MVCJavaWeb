<%@page import="cn.twr.mvcDemo.model.User"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/css/layui.css"
	tppabs="${pageContext.request.contextPath}/layui/css/layui.css"
	media="all">
<script src="${pageContext.request.contextPath}/layui/layui.js"
	charset="utf-8"></script>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	//session
	String username = (String) request.getSession().getAttribute("user");
	if (username == null || "".equals(username)) {
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	%>

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
		<li class="layui-nav-item"><a
			href="<%=request.getContextPath()%>/loginOut.udo"> 退出登陆</a></li>
	</ul>

	<script>
		//注意：导航 依赖 element 模块，否则无法进行功能性操作
		layui.use('element', function() {
			var element = layui.element;

			//…
		});
	</script>

	<br />
	<br />
	<!-- 查询表单 -->
	<form class="layui-form"
		action="<%=request.getContextPath()%>/query.udo" method="post">
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				<input type="text" name="username" lay-verify="required"
					placeholder="请输入用户名" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"> 电话</label>
			<div class="layui-input-block">
				<input type="text" name="phoneNo" lay-verify="required"
					placeholder="请输入手机号" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"> 性别</label>
			<div class="layui-input-block">
				<input type="radio" name="sex" value="男" title="男"> <input
					type="radio" name="sex" value="女" title="女" checked> <input
					type="radio" name="sex" value="" title="不限" checked>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<input type="submit" class="layui-btn" value="立即查询" />
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</form>

	<br />
	<!-- 查询表格 -->
	<div style="margin-left: 100px; margin-right: 100px">
		<table class="layui-table">
			<colgroup>
				<col width="150">
				<col width="200">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th>用户ID</th>
					<th>用户名</th>
					<th>用户密码</th>
					<th>用户电话</th>
					<th>性别</th>
					<th>注册日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<%
				List<User> list = (List<User>) request.getAttribute("userList");
				if (list != null && list.size() > 0) {
					for (User user : list) {
				%>
				<tr>
					<td><%=user.getId()%></td>
					<td><%=user.getUsername()%></td>
					<td><%=user.getPasword()%></td>
					<td><%=user.getPhoneNo()%></td>
					<td><%=user.getSex()%></td>
					<td><%=user.getRegDate()%></td>
					<td><a
						href="<%=request.getContextPath()%>/update.udo?id=<%=user.getId()%>"
						class="layui-btn">编辑 <a
							href="<%=request.getContextPath()%>/delete.udo?id=<%=user.getId()%>"
							class="layui-btn layui-btn-danger">删除 </td>
				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>
</body>
</html>