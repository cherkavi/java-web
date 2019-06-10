<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8" import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Applet Example</title>
	<script type="text/javascript" language="javascript" src="/DisplayApplet/dwr/interface/GetBody.js"></script>
	<script type="text/javascript" language="javascript" src="/DisplayApplet/dwr/engine.js"></script>

	<script type="text/javascript">

	function toConsole(value,value2){
		var console_element=document.getElementById("console");
		if(console_element){
			var span_element=document.createElement("div");
			var text_element=document.createTextNode(value);
			if(value2){
				text_element=document.createTextNode(value+value2);
			}else{
				text_element=document.createTextNode(value);
			}

			//console_element.innerHTML=console_element.innerHTML+value+value2+"\n"
			<%-- вызывает перезагрузку апплета при первом обращении--%>
			span_element.appendChild(text_element);
			console_element.appendChild(span_element);
		}
	}
	// получить выделенный элемент из указанного ID
	function get_selected_value(element_id){
		var return_value="";
		var element=document.getElementById(element_id);
		if(element!=null){
			for(var counter=0;counter<element.childNodes.length;counter++){
				var current_element=element.childNodes.item(counter);
				if(current_element.selected){
					if(return_value!=""){
						return_value=return_value+","+current_element.value;						
					}else{
						return_value=current_element.value;
					}
				}
			}				
		}
		return return_value;
	}
	
		<%-- CONNECTION:5
		   место куда апплет передал
		   <Имя функции> <Int значение функции> <String значение функции> <Массив ключей> <Массив значений>
		--%> 
		function getAnswer(function_name, function_int, function_string, array_keys, array_values){
			toConsole("getAnswer:   function_name:"+function_name+"   function_int:"+function_int+"  function_string:"+function_string+" array_keys:"+array_keys.length);
			{
				var message=function_string+"\n";
				try{
					for(var counter=0;counter<array_keys.length;counter++){
						message=message+"key:"+array_keys[counter]+"   value:"+array_values[counter]+";\n";
					}
				}catch(ex){
					toConsole("getAnswer:",ex.message);
				}
				toConsole(message);
			}
			if(function_name=="SHOWMESSAGE"){
				alert(function_string);
			}else if(function_name=="WRITETOLOG"){
						toConsole("LOG:",function_string);
				   }else{
						change_interface(function_name,array_keys, array_values);
				   }
		}
	
		<%-- CONNECTION:6  --%>
		function change_interface(function_name,array_keys, array_values){
			var fragment=new Object();
			// апплет должен передать данные (шаг 1) 
			fragment.functionName=function_name;
			fragment.informationKeys=array_keys;
			fragment.informationValues=array_values;
			// посылка данных через DWR
			GetBody.sendToServer(fragment,get_body_response);
			toConsole("change_interface",function_name);
		}
		<%-- CONNECTION:9  --%>
		function get_body_response(fragment){
			if(fragment){
				try{
					if(fragment.functionName=="innerHTML"){
						if(fragment.informationCount>0){
							for(var counter=0;counter<fragment.informationCount;counter++){
								// console
								//toConsole(fragment.informationKeys[counter],fragment.informationValues[counter]);
								document.getElementById(fragment.informationKeys[counter]).innerHTML=fragment.informationValues[counter];
							}
						}else{
							alert("inner HTML error - parameter is empty");
						}
					}
					if(fragment.functionName=="ERROR"){
						alert(fragment.informationValues[0]);
					}
				}catch(ex){
					toConsole(" get_body_response: ERROR",ex.message);
				}
			}else{
				<%-- GetBody не получил ответ от DisplayApplet--%>
				alert("Error");
			}
		}

		function applet_action(action_name, param_key, param_value ){
			var applet_element=document.getElementById("japplet");
			if(applet_element){
				applet_element.action_clear_parameter();
				for(var counter=0;counter<param_key.length;counter++){
					applet_element.action_add_parameter(param_key[counter],param_value[counter]);
				}
				applet_element.action(action_name);
				toConsole("applet_action:",action_name);
			}else{
				alert("Applet not found");
			}
			
		}
	</script>

</head>
<body>
<%-- place for applet:begin--%>
<div>
	<%
		/** ключ для определения имени броузера пользователя */
		String user_agent_header_key="user-agent";
		/** показывает какой тэг закрывать - Applet или Object */
		boolean use_applet=false;
		/** полное имя броузера пользователя */
		String user_agent=request.getHeader(user_agent_header_key);
		//System.out.println("USER_AGENT: >>>>>"+user_agent);
		if((user_agent.toLowerCase().indexOf("konqueror")>=0)||(user_agent.toLowerCase().indexOf("macintosh")>=0)||(user_agent.toLowerCase().indexOf("opera")>=0)||(user_agent.toLowerCase().indexOf("firefox")>=0)){
			use_applet=true;
			out.println("<applet ");
			out.println("		 archive=\"applet.jar\"");
			out.println("		 code=\"gui.HTML.MainApplet\"");
			out.println("		 width=\"100\" "); 
			out.println("        MAYSCRIPT name=\"japplet\" "); 
			out.println("        id=\"japplet\"");
			out.println("		 height=\"30\">");
		}else{
			if(user_agent.indexOf("MSIE")>=0){
				//1.4.1 = out.println("<object classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\"");
				//1.4.2 = out.println("<object classid=\"clsid:CAFEEFAC-0014-0002-FFFF-ABCDEFFEDCBA\"");
				//1.5.0 = out.println("<object classid=\"clsid:CAFEEFAC-0015-0000-FFFF-ABCDEFFEDCBA\"");
				//1.6.0 = out.println("<object classid=\"clsid:CAFEEFAC-0016-0000-FFFF-ABCDEFFEDCBA\"");
				out.println("<object classid=\"clsid:CAFEEFAC-0016-0000-FFFF-ABCDEFFEDCBA\"");
				out.println("width= \"100\" height= \"30\" style=\"border-width:0;\"  ");
				out.println("codebase=\"http://java.sun.com/products/plugin/autodl/jinstall-1_4_1-windows-i586.cab#version=1,4,1\" name=\"japplet\" id=\"japplet\">");
			}else{
				//application/x-java-applet;version=1.4.1
				out.println("<object type=\"application/x-java-applet\" width= \"100\" height= \"30\"  name=\"japplet\" id=\"japplet\">");
			}
			out.println("<param name=\"archive\" value=\"applet.jar\">");
			out.println("<param name=\"code\" value=\"gui.HTML.MainApplet\">");
			out.println("<param name=\"mayscript\" value=\"yes\">");
			out.println("<param name=\"scriptable\" value=\"true\">");
			out.println("<param name=\"name\" value=\"japplet\">';");
		}
		if(use_applet){
			out.println("</applet>");
		}else{
			out.println("</object>");
		}
	%>
</div>
<%-- place for applet:end--%>
	<center><b> This header for page </b></center> 
	<hr align="center" width=100>
	<br>
<table>
	<tr >
		<td >
		</td>
	</tr>
</table>
	<span id="MAINFRAME">
 		<input type="button" value="GetAllDevices" onclick="applet_action('GetAllDevices',new Array(),new Array())" >
	</span>
	
	<hr align="center" width=100>
	<center><b> this is footer for page </b></center>
	<br>
	<input type="button" value="click for add information" onclick="toConsole('text for console    ')">
	<br>
	<span id="console">
	</span>
</body>
</html>