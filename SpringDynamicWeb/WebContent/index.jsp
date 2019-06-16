<%@ page language="java" contentType="text/html; charset=ISO-8859-2"
    pageEncoding="ISO-8859-2"
    import="org.springframework.context.*, org.springframework.web.context.support.*, com.test.spring.beans.*, com.test.spring.context.ApplicationContextHolder"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.test.spring.context.ContextAware"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-2">
<title>Session counter</title>
</head>
<body>
	<form>
		<h2>
		<%
			ApplicationContext context=WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
			System.out.println("WebContext:"+context);
			System.out.println("GlobalContext:"+ApplicationContextHolder.getInstance());
			Counter counter=(Counter)context.getBean("methodGetCounter");
			out.println(counter.increaseCounter());
		%>
		</h2>
		<br />
		<input type="submit" value="increase" /> 
	</form>
</body>
</html>