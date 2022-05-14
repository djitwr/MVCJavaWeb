<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
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
<title>购物车</title>
</head>
<body>
<br/><br/>
<h2>购物车</h2>
<br/>
<%
	List<String> carList = (List<String>)session.getAttribute("car");
	if(carList!=null){
		for(String list :carList){
			out.print("商品："+list);
			out.print("<br/><br/>");
		}
	}
%>
<br/>
<a class="layui-btn" href="<%=request.getContextPath()%>/productList.jsp">继续选购商品</a>
<a class="layui-btn layui-btn-primary layui-border-red" href="#">结算购物车</a>
</body>
</html>