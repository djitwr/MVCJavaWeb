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
<title>管理员登陆</title>
	<!-- js操作Cookie -->
	<script type="text/javascript">
		//获取Cookie
		function getCookie(c_name) {
			if (document.cookie.length > 0) {
				var c_start = document.cookie.indexOf(c_name + "=");
				c_start = c_start + c_name.length + 1;
				var c_end = document.cookie.indexOf(";", c_start);
				if (c_end == -1) c_end = document.cookie.length;
				return unescape(document.cookie.substring(c_start, c_end));
			}
		}

		window.onload = function () {
			var form = document.getElementById("loginform");
			var username = document.getElementById("username");
			if (getCookie("userKey") != "" && getCookie("userKey") != null && getCookie("ssid") != "" && getCookie("ssid") != null) {
				username.value = getCookie("userKey");
				//alert(username.value);
				form.submit();
			}
		}
	</script>
</head>
<body>

	<br />
	<br />
	<!-- 管理员登陆表单 -->
	<%
	String note = (String) request.getAttribute("note");
	%>
	<div
		style="display: block; position: fixed; _position: absolute; left: 45%; top: 15%;">
		<h1>
			<i class="layui-icon layui-icon-face-smile"
				style="font-size: 30px; color: #1E9FFF;"></i><strong> 管理员登陆</strong>
		</h1>
	</div>
	<br />
	<div
		style="position: absolute; top: 50%; left: 50%; -webkit-transform: translate(-50%, -50%); -moz-transform: translate(-50%, -50%); -ms-transform: translate(-50%, -50%); -o-transform: translate(-50%, -50%); transform: translate(-50%, -50%);">

		<form id="loginform" class="layui-form"
			action="<%=request.getContextPath()%>/login.udo" method="post">
			<div class="layui-form-item">
				<label class="layui-form-label">提示信息</label>
				<div class="layui-input-block">
					<input type="text" style="color: red;" name="" value="<%=note%>"
						lay-verify="required" placeholder="" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input id="username" type="text" name="username" value="" lay-verify="required"
						placeholder="请输入用户名" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"> 密码</label>
				<div class="layui-input-block">
					<input type="text" name="password" lay-verify="required"
						placeholder="请输入密码" autocomplete="off" class="layui-input">
				</div>
			</div>
			<!-- 验证码 -->
			<div class="layui-form-item">
				<label class="layui-form-label">验证码</label>
				<div class="layui-input-block">
					<input type="text" name="checkCode" lay-verify="required"
						placeholder="请输入验证码" autocomplete="off" class="layui-input">
						<br/>
						<img alt="" src="<%=request.getContextPath() %>/drawCheckCode.udo">
				<br/>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<input type="submit" class="layui-btn" value="立即登陆" />
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"> 记住我</label>
				<div class="layui-input-block">
					<input type="radio" name="expiredays" value="7" title="七天">
					<input type="radio" name="expiredays" value="15" title="半个月">
					<input type="radio" name="expiredays" value="30" title="一个月">
				</div>
			</div>
		</form>

	</div>
</body>
</html>