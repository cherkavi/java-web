<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<body>
	<input type="button" id="button_1" value="get Date/Time" onclick="exec()">
	<br>
	<input type="button" id="button_get_int" value="getInt value" onclick="SimpleData.getInt(callback_function)">
	<input type="text" id="edit_int" value="">
	<input type="button" id="button_int" value="set_int" onclick="SimpleData.setInt(document.getElementById('edit_int').value,callback_function)">
	<br>
	<input type="button" id="button_get_double" value="getDouble value" onclick="SimpleData.getDouble(callback_function)">
	<input type="text" id="edit_double" value="">
	<input type="button" id="button_double" value="set_double" onclick="SimpleData.setInt(document.getElementById('edit_double').value,callback_function)">
	<br>
	<input type="button" id="button_get_string" value="getString value" onclick="SimpleData.getString(callback_function)">
	<input type="text" id="edit_string" value="">
	<input type="button" id="button_string" value="set_string" onclick="SimpleData.setString(document.getElementById('edit_string').value,callback_function)">
	<br>
	<br>
	<input type="button" id="button_send_object" value="send_object" onclick="send_object()">
	<br>
	<input type="button" id="button_get_object" value="get_object" onclick="ObjectData.getObject(get_object)">
	
	<span id="console">
	</span>
</body>

<script src='/dwr_example_2/dwr/interface/JDate.js'></script>
<script src='/dwr_example_2/dwr/interface/SimpleData.js'></script>
<script src='/dwr_example_2/dwr/interface/ObjectData.js'></script>
<script src='/dwr_example_2/dwr/engine.js'></script>
<script type="text/javascript">
	// отправка объекта
	function send_object(){
		// объект должен создаваться именно так:
		var value=new Object();
		value.field_int=5;
		value.field_double=2;
		value.field_string="client send data";
		value.field_string_array=new Array("one","two","three");

		// а не через: var value=function{};
		//var value=function(){
		//	this.field_int=5;
		//	this.field_double=2;
		//	this.field_string="client send data";
		//	this.field_string_array=new Array("one","two","three");
		//}
		ObjectData.setObject(value,callback_function);
	}

	// получение объекта 
	function get_object(responce_object){
		toConsole(responce_object.field_int);
		toConsole(responce_object.field_double);
		toConsole(responce_object.field_string);
		for(var counter=0;counter<responce_object.field_string_array.length;counter++){
			toConsole(responce_object.field_string_array[counter]);
		}
	}

	// пример простого запроса
	function exec(){
		toConsole("exec:begin");
		JDate.toString(callback_function);
		toConsole("exec:end"); 
	}
	// функция, которая выводит на консоль полученные объекты
	function callback_function(responce){
		toConsole(responce);
	}
	// функция вывода на консоль
	function toConsole(text){
		var console=document.getElementById("console");
		if(console!=null){
			var text_element=document.createTextNode(text);
			var br_element=document.createElement("br");
			var span_element=document.createElement("span");
			span_element.appendChild(br_element);
			span_element.appendChild(text_element);
			console.appendChild(span_element);
		}
	}
</script>
</html>