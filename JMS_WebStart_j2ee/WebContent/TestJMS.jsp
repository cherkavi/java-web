<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="jms.sender.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%!
		private java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat();
	%>
	<%!
		private String jmsPath="tcp://192.168.15.120:61616";
	%>	
	
	<form >
		<table>
			<% request.getSession(true); %>
			<tr>
				<td colspan="2"><a href="http://192.168.15.120:8080/JMS_WebStart_j2ee/client.jnlp"> web start application </a></td>
			</tr>
			<%
				if((request.getParameter("text_for_send")!=null)&&(!request.getParameter("text_for_send").equals(""))){
					String queueName=request.getSession(true).getId();
					System.out.println("Queue Server: "+queueName);
					JMSSender sender=new JMSSender(jmsPath, queueName, 10);
					{
						sender.start();
					}
					if(sender.addTextMessage(request.getParameter("text_for_send"))){
						out.println("<tr><td> SENDED </td></tr>");
					}else{
						out.println("<tr><td> add to queue Error</td></tr>");
					}
					sender.stopThread();
				}
			%>
			<tr>
				<td>Text for Send to client: </td>
				<td> <input type="text" name="text_for_send" value="<%=("server text: "+sdf.format(new java.util.Date())) %>" /> </td>				
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="send" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>