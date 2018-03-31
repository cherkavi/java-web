<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.io.*,java.util.*,javax.servlet.*,javax.servlet.http.*,org.apache.commons.fileupload.*,org.apache.commons.fileupload.disk.DiskFileItemFactory,org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
			List<FileItem> fileList=upload.parseRequest(request);
			for(FileItem file:fileList){
				if(file.isFormField()){
					System.out.println(file.getFieldName());
					System.out.println(file.getString());
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
			<form method="post" enctype="multipart/form-data" action="http://localhost:8080/JspLoadFile/uploadFile.jsp">
				<input type="hidden" name="hidden_type" value="this is hidden" /> 
				<input type="file" name="file_name" > <input type="submit" value="send file" /> 
			</form>
	<%} %>
</body>
</html>