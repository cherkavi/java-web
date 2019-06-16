<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"
         import="java.io.*,
    		java.util.List,
    		javax.servlet.*,
    		javax.servlet.http.*,
    		org.apache.commons.fileupload.disk.*,
    		org.apache.commons.fileupload.*,
    		org.apache.commons.fileupload.servlet.*,java.io.*"
     
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.commons.lang.StringEscapeUtils"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
  
 <%
 	if(ServletFileUpload.isMultipartContent(request)){
  		System.out.println("it is file upload");
  		// Create a factory for disk-based file items
  		FileItemFactory factory = new DiskFileItemFactory();
  		// Create a new file upload handler
  		ServletFileUpload upload = new ServletFileUpload(factory);
  		try{
   			@SuppressWarnings("unchecked")
  			List<FileItem> fileList=upload.parseRequest(request);
   			for(FileItem file:fileList){
    			if(file.isFormField()){
     				System.out.println(file.getFieldName());
     				System.out.println(file.getString());
     			    ByteArrayInputStream byteArray=new ByteArrayInputStream(file.getString().getBytes());				
     			   	BufferedReader reader=new BufferedReader(new InputStreamReader(byteArray,"UTF-8"));
     			   	reader.readLine();
     				//System.out.println("converter:"+org.apache.commons.lang.StringEscapeUtils.escapeHtml();
     				System.out.println("unconverter:"+org.apache.commons.lang.StringEscapeUtils.escapeHtml(org.apache.commons.lang.StringEscapeUtils.unescapeHtml(file.getString())));
     				//System.out.println(org.apache.commons.lang.StringEscapeUtils.unescapeJava(file.getString()));
    			}else{
     				System.out.println(file.getName());
     				file.write(new File("c:\\output_servlet.file"));
    			}	
   			}
  		}catch(Exception ex){
   			System.err.println("Exception:"+ex.getMessage()); 
  		}
%>
  		<b>OK</b>
<%
 	}else{
%>
   		<form method="post" enctype="multipart/form-data" action="http://localhost:8080/UploadFile/Upload.jsp" >
		<% System.out.println(org.apache.commons.lang.StringEscapeUtils.escapeHtml("это временное значение"));%>
    	<input type="text" name="text_parameter" value="<%=org.apache.commons.lang.StringEscapeUtils.escapeHtml("это временное значение")%>" />
		<input type="hidden" name="hidden_type" value="this is hidden" /> 
    	<input type="file" name="file_name" > <input type="submit" value="send file" /> 
   		</form>
 <%} %>

</body></html>