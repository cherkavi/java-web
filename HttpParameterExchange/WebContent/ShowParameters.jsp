<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table border=1>
		<tr>
			<th>Attribute Name</th>
			<th>Attribute Value</th>
		</tr>
		<% 
			try{
				Enumeration<String> enumeration=(Enumeration<String>)request.getAttributeNames();
				String currentName;
				while(enumeration.hasMoreElements()){
					currentName=enumeration.nextElement();
					out.println("<tr>");
					out.println("<td>"+currentName+"</td>");
					out.println("<td>"+request.getAttribute(currentName)+"</td>");
					out.println("</tr>");
				}
			}catch(Exception ex){
				System.err.println("exception when try get all parameter's ");				
			}
		%>
	</table>
	<br>
	<br>
	<br>
	<table border=1>
		<tr>
			<th>Parameter Name</th>
			<th>Parameter Value</th>
		</tr>
		<% 
			try{
				Enumeration<String> enumeration=(Enumeration<String>)request.getParameterNames();
				String currentName;
				while(enumeration.hasMoreElements()){
					currentName=enumeration.nextElement();
					out.println("<tr>");
					out.println("<td>"+currentName+"</td>");
					out.println("<td>"+request.getParameter(currentName)+"</td>");
					out.println("</tr>");
				}
			}catch(Exception ex){
				System.err.println("exception when try get all parameter's ");				
			}
		%>
	</table>

</body>
</html>