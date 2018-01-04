<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<span id="container_combobox">
		<select id="combobox">
			<option value=1>one</option>
			<option value=2>two</option>
			<option value=3>three</option>
		</select>
	</span>
	<input type="button" value="show child" onclick="show_child()">
	<span id="console">
	</span>
	<hr width="300" align="center">
	<input type="text" id="edit_1" value="temp value">
	<br>
	<input type="button" value="click_me" onclick="exec()">
	<br>
	<input type="button" value="get russian text" onclick="SimpleObject.getString(toConsole)">
</body>


<script type='text/javascript' src='/dwr_example/dwr/interface/JDate.js'></script>
<script type='text/javascript' src='/dwr_example/dwr/interface/SimpleObject.js'></script>
<script type='text/javascript' src='/dwr_example/dwr/engine.js'></script>
<script type="text/javascript">
	var flag=false;
	function show_child(){
		//var combobox=document.getElementById("container_combobox");
		//combobox.innerHTML="<select> <option value=4>four</option> <option value=5>five</option> </select>"
		if(flag==true){
			var combobox_body=document.getElementById("combobox");
			combobox_body.innerHTML="<option value=1>one</option> <option value=2>two</option>";
		}else{
			var combobox_body=document.getElementById("combobox");
			combobox_body.innerHTML="<option value=4>four</option> <option value=5>five</option>";
		}
		flag=!flag;
	}

	function toConsole(value){
		var console=document.getElementById("console");
		if(console!=null){
			var span_element=document.createElement("span");
			var br_element=document.createElement("br");			
			var text_value=document.createTextNode(value);
			span_element.appendChild(text_value);
			console.appendChild(span_element);
			console.appendChild(br_element);
		}		
	}

	function exec(){
		<%-- Получение данных от сервера --%>
		JDate.toString(function(responce){alert(responce)});
		//toConsole(date);
	}
</script>
</html>