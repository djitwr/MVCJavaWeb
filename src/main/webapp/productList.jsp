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
	
<div style="">
<img alt="" src="phone-img/m1.png" height="150" width="140">
<a href="<%=request.getContextPath() %>/shopping.pdo?pname=三星 SAMSUNG Galaxy S22" ><h4><strong>三星 SAMSUNG Galaxy S22</h4></strong></a><br/>
<img alt="" src="phone-img/m2.png" height="150" width="140">
<a href="<%=request.getContextPath() %>/shopping.pdo?pname=realme真我GT Neo3 150W 天玑8100" ><h4><strong>realme真我GT Neo3 150W 天玑8100</strong></h4></a><br/>
<img alt="" src="phone-img/m3.png" height="150" width="140">
<a href="<%=request.getContextPath() %>/shopping.pdo?pname=Apple iPhone 12 (A2404) 64GB" ><h4><strong>Apple iPhone 12 (A2404) 64GB</strong></h4></a><br/>
<img alt="" src="phone-img/m4.png" height="150" width="140">
<a href="<%=request.getContextPath() %>/shopping.pdo?pname=华为 HUAWEI nova 7 5G " ><h4><strong>华为 HUAWEI nova 7 5G</strong></h4></a>
</div>


</body>
</html>