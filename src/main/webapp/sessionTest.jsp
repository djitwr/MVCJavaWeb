<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
	HttpSession session2 = request.getSession();
	session2.setAttribute("users", "admin");
	out.println("取到的session2中key(users)的value值："+session.getAttribute("users"));
%>

</body>
</html>