<%@ page language="java" contentType="text/html; charset=WINDOWS-1251"
    pageEncoding="WINDOWS-1251"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=WINDOWS-1251">
<title>Insert title here</title>
</head>
<body>
	<center>
	
		<form action="<%=request.getContextPath()%>/login_servlet" method="POST" >
			<table width=300px>
				<tr>
					<td>
						<center>Логин</center>
					</td>
				</tr>
				
				<tr>
					<td>
						<input type="text" name="edit_login" />
					</td>
				</tr>
				<tr>
					<td>
						<center>Пароль</center>
					</td>
				</tr>
				
				<tr>
					<td>
						<input type="password" name="edit_password" />
					</td>
				</tr>
				<tr>
					<td>
						<center>
							<input type="submit" value="login" />
						</center>
					</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>