<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*, java.text.*, com.cherkashyn.vitalii.computer_shop.rediscount.client.output.*, com.cherkashyn.vitalii.computer_shop.rediscount.client.restwrapper.*, org.apache.commons.lang3.*"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Rediscount</title>
<meta charset="utf-8" />
<title>jQuery UI Datepicker - Default functionality</title>
<link rel="stylesheet"
	href="css/jquery-ui.css" />
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css" />
<%! String serverUrl="http://localhost:8080/ComputerShopRediscount";  %>
<script>
	<!-- create UI -->
  $(function() {
    $( "#rediscount_date" )
    .datepicker();
    $( "#rediscount_date" )
    .datepicker("option", "dateFormat", "dd.mm.yy");
    $( "#rediscount_date" )
    .datepicker("setDate", new Date());
  });
  <!-- fill select point -->
  </script>
</head>
<body>
	<form method="post" action="rediscount.jsp">
	<fieldset style="width: auto;">
		<legend> Select Trade Rediscount</legend>
		<div align="center">
			<table>
				<tr>
					<th>Trade Point</th>
					<th>Rediscount date</th>
				</tr>
				<tr>
					<td>
						<select id="point" name="<%=ParametersName.PARAMETER_POINT_NUMBER %>">
							<%
								OutputHelper.printSelectOptions(new PointRestWrapper().requestPoints(OutputHelper.SERVER_URL), out, request.getParameter(ParametersName.PARAMETER_POINT_NUMBER));
							%>
						</select>
					</td>
					<td>
						<input type="text" id="rediscount_date" name="<%=ParametersName.PARAMETER_REDISCOUNT_DATE %>" />		
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<button type="submit"  >Enter</button>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<%
						if(StringUtils.trim((String)request.getAttribute(ParametersName.PARAMETER_ERROR_MESSAGE))!=null){
							out.write("<span style=\"color:red\" >");
							out.write((String)request.getAttribute(ParametersName.PARAMETER_ERROR_MESSAGE));
							out.write("</span>"); 	
						}
					%>
					</td>
				</tr>
			</table>
		</div>
	</fieldset>
	</form>
</body>
</html>