<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>InputValue</title>

</head>
<body>
	<s:form action="InputValue" method="POST">
		<table>
			<tr align="left">
				<td><s:textfield name="login" label="Login name"/></td>
			</tr>
			<tr align="left">
				<td><s:textfield name="password" label="Password name"/></td>
			</tr>
			<tr align="center">
				<td><s:submit value="Login"/></td>
			</tr>
			<tr align="left">
				<td> <b> <s:property value="tempProperty" /> </b> </td>
			</tr>
		</table>
	</s:form>
</body>
</html>