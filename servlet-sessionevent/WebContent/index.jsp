<%@ page language="java" contentType="text/html; charset=WINDOWS-1251"
    pageEncoding="WINDOWS-1251" import="java.util.Date,utility.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=WINDOWS-1251">
<title>Login</title>
</head>
<body>
	<%
		// JSP autoCreate Session, check uniqueId;
		//HttpSession currentSession=request.getSession(false);
		//String userId=(String)currentSession.getAttribute("userId");
		String userId=request.getParameter("userId");
		if(userId==null){
			// отобразить окно идентификации клиентов
	%>
		<center>
			<form name="form_main"  action="<%=request.getContextPath()%>/Identifier">
			<div style="width:200 px">
				<table width=100%>
					<tr>
						<td width=100%>
							<center>
								<b> Login </b>
								<br>
								<input width="100%" type="text" name="login" />
							</center>
						</td>
					</tr>
					<tr>
						<td width=100%>
							<center>
								<b> Password </b>
								<br>
								<input width="100%" type="password" name="pass" />
							</center>
						</td>
					</tr>
					<tr>
						<td width="100%">
							<center>
								<input type="submit" value="Enter" />
							</center>
						</td>
					</tr>
				</table>
			</div>
			</form>
		</center>
	<% 
		}else{
			// отобразить окно подтверждения удаления пользователя, и разрушения сессии
	%>
		<b>Hello, <%
					String userName=utility.Users.getUserName(userId);
					out.println(userName);
		%>
		   you are login, do you have <a href="Destroy">logout</a> ?
		</b>
	<%}; %>
</body>
</html>