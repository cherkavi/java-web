<%
	if(StringUtils.trimToNull(request.getParameter(ParametersName.PARAMETER_REDISCOUNT_DATE))!=null){
		session.setAttribute(ParametersName.PARAMETER_REDISCOUNT_DATE, request.getParameter(ParametersName.PARAMETER_REDISCOUNT_DATE));
	}
	if(StringUtils.trimToNull(request.getParameter(ParametersName.PARAMETER_POINT_NUMBER))!=null){
		session.setAttribute(ParametersName.PARAMETER_POINT_NUMBER, request.getParameter(ParametersName.PARAMETER_POINT_NUMBER));
		session.setAttribute(ParametersName.PARAMETER_POINT_NAME, new PointRestWrapper().requestPointName(OutputHelper.SERVER_URL, request.getParameter(ParametersName.PARAMETER_POINT_NUMBER)));
		boolean rediscountExists=new RediscountRestWrapper().requestIsRediscountPresent(OutputHelper.SERVER_URL, 
				   request.getParameter(ParametersName.PARAMETER_POINT_NUMBER), 
				   request.getParameter(ParametersName.PARAMETER_REDISCOUNT_DATE ));
		if(rediscountExists==false){
			request.setAttribute("error_message", "need to Create Rediscount into Main Program ");
			// response.sendRedirect("index.jsp");
			try{
				request.getRequestDispatcher("index.jsp").forward(request, response);	
			}catch(IllegalStateException se){
				response.sendRedirect("index.jsp");
			}
		}
	}

	// check attributes of session
	if(StringUtils.trimToNull((String)session.getAttribute(ParametersName.PARAMETER_REDISCOUNT_DATE))==null){
		request.setAttribute(ParametersName.PARAMETER_ERROR_MESSAGE, "need to set Date");
		try{
			request.getRequestDispatcher("index.jsp").forward(request, response);	
		}catch(IllegalStateException se){
			response.sendRedirect("index.jsp");
		}
	}
	if(StringUtils.trimToNull((String)session.getAttribute(ParametersName.PARAMETER_POINT_NUMBER))==null){
		request.setAttribute(ParametersName.PARAMETER_ERROR_MESSAGE, "need to select point");
		try{
			request.getRequestDispatcher("index.jsp").forward(request, response);	
		}catch(IllegalStateException se){
			response.sendRedirect("index.jsp");
		}
	}
	
	
%>
<%@ page 
    import="java.util.*, java.io.*, java.text.*, com.cherkashyn.vitalii.computer_shop.rediscount.client.output.*, com.cherkashyn.vitalii.computer_shop.rediscount.client.restwrapper.*, org.apache.commons.lang3.*"
    %>
<%@ page    
	language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-2">
	<title>Rediscount</title>
	<script src="js/jquery-1.9.1.js"></script>
	<script>
		$(document).ready(function() {
			$("#serial").keyup(function(e){
			    if(e.keyCode == 13)
			    {
			    	$("button_add").click();
			    }
			});
			$("#serial").focus();
		});
	</script>
	</head>
<body>
	<fieldset style="width: auto;">
		<legend> <%=session.getAttribute(ParametersName.PARAMETER_POINT_NAME)%> : <%=session.getAttribute(ParametersName.PARAMETER_REDISCOUNT_DATE) %></legend>
		<div align="center">
			<table>
				<tr>
					<form method="post">
						<td><input type="text" id="serial" name="serial" autocomplete = "off" /> </td>
						<td><button type="submit" id="button_add" >add</button></td>
					</form>
				</tr>
				<%
					if(StringUtils.trimToNull(request.getParameter("serial"))!=null){
						out.write("<tr>");
						out.write("  <td colspan=\"2\">");
						try{
							String addResult=StringUtils.trim(new RediscountRestWrapper().addRediscountElement(OutputHelper.SERVER_URL, (String)session.getAttribute(ParametersName.PARAMETER_POINT_NUMBER), (String)session.getAttribute(ParametersName.PARAMETER_REDISCOUNT_DATE), request.getParameter(ParametersName.PARAMETER_SERIAL_NUMBER)));
							if(addResult.equals("ALREADY_SCANNED")){
								out.write("<embed src=\"sound/ALREADY_SCANNED.wav\" hidden=\"true\" autostart=\"true\" loop=\"false\" />");
								out.write("<span style=\"color:white;background-color:green\"> ALREADY_SCANNED</span>");
							}else if("ADDED".equals(addResult)){
								out.write("<embed src=\"sound/ADDED.wav\" hidden=\"true\" autostart=\"true\" loop=\"false\" />");
								out.write("<span style=\"color:white;background-color:blue;font:bold;\"> ADDED</span>");
							}else if("NOT_FOUND_IN_SOURCE".equals(addResult)){
								out.write("<embed src=\"sound/NOT_FOUND_IN_SOURCE.wav\" hidden=\"true\" autostart=\"true\" loop=\"false\" />");
								out.write("<span style=\"color:white;background-color:red\">Warning: NOT_FOUND_IN_SOURCE</span>");
							}else if("BAD_NUMBER".equals(addResult)){
								out.write("<embed src=\"sound/BAD_NUMBER.wav\" hidden=\"true\" autostart=\"true\" loop=\"false\" />");
								out.write("<span style=\"color:white;background-color:red\">Warning: BAD_NUMBER</span>");
							}
						}catch(IOException ex){
							out.write("<embed src=\"sound/ERROR.wav\" hidden=\"true\" autostart=\"true\" loop=\"false\" />");
							out.write("<span style=\"color:white;background-color:fuchsia\">!!! Server error: "+ex.getMessage()+" !!!</span>");
						}
						
						// out.write(request.getParameter(ParametersName.PARAMETER_SERIAL_NUMBER));
						out.write("  </td>");
						out.write("</tr>");
					}
				%>
			</table>
			<hr />
			<table border="1">
				<%
					OutputHelper.printLastRediscounted(new RediscountRestWrapper().getLastRediscounted(OutputHelper.SERVER_URL, (String)session.getAttribute(ParametersName.PARAMETER_POINT_NUMBER), (String)session.getAttribute(ParametersName.PARAMETER_REDISCOUNT_DATE)), out);
				%>
				<tr>
				</tr>
			</table>
		</div>
	</fieldset>	

</body>
</html>