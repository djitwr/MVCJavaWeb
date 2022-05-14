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
	
	//获取客户端传过来的cookie
	Cookie[] cookieArr = request.getCookies();
	if(cookieArr!=null && cookieArr.length >0){
		for(int i = 0;i < cookieArr.length ; i++ ){
			out.println("cookie的名字（key）："+cookieArr[i].getName());
			out.println("cookie的值："+cookieArr[i].getValue());
		}
	}else{
		//创建一个cookie
		Cookie cookie = new Cookie("uuid","qwe123");
		response.addCookie(cookie);
	}

%>

</body>
</html>