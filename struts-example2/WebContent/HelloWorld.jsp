<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>Struts 2 Hello World Application!</title>
</head>
<body>
	<b>this is Struts example:</b>
	<br>
	<h2>
		<s:property value="message" />
	</h2>
	<p>Current date and time is: 
		<b>
			<s:property value="currentTime" />
		</b>
	</p>
	<br>
	<a href="/StrutsExample/temp/InputValue.action">LOGIN</a>
</body>
</html>