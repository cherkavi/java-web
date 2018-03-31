<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title> </title>
</head>
<body>
	<h2>
		Welcom user:
		<%
		String userId=null;
		try{
			userId=(String)request.getAttribute("userId");	
		}catch(Exception ex){
		}
		if(userId==null){
			try{
				userId=(String)request.getParameter("userId");	
			}catch(Exception ex){
			}
		}
		out.println(utility.Users.getUserName(userId));
	%>
	</h2>
	<br>
		<a href="<%=request.getContextPath()%>/index.jsp?userId=<%=userId%>" > go to Login</a>
	<br>
		<a href="<%=request.getContextPath()%>/wellcom2.jsp?userId=<%=userId%>" > go to Wellcom2</a>
	<br>
	<b>
		<a href="<%=request.getContextPath()%>/Destroy" > Exit</a>
	</b>
	<input type="hidden" name="userId" value="<%=userId%>">
</body>
</html>