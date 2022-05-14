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
<title>商品列表页</title>
</head>
<body>
	<ul class="layui-nav">
		<li class="layui-nav-item"><a
			href="#">首页</a></li>
		<li class="layui-nav-item layui-this"><a
			href="#">订单管理</a></li>
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
			href="<%=request.getContextPath() %>/shoppingCar.jsp "> 购物车</a></li>
	</ul>

	<script>
		//注意：导航 依赖 element 模块，否则无法进行功能性操作
		layui.use('element', function() {
			var element = layui.element;

			//…
		});
	</script>
	<br/><br/>
	
	<%
	String pname = (String)request.getAttribute("p");
	out.println("商品名："+pname);
	%>
	<br/><br/>
	商品详情...
	<br/><br/>
	<a  class="layui-btn layui-btn-primary layui-border-red" href="<%=request.getContextPath() %>/shoppingCar.pdo?pname=<%=pname%> ">加入购物车

</body>
</html>