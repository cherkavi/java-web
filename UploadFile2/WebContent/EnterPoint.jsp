<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="org.apache.commons.fileupload.servlet.*,org.apache.commons.fileupload.disk.*, java.util.*,org.apache.commons.fileupload.*,java.io.* " %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<%
		if(request.getParameter("edit_text")!=null){
			// form was submitted
			if(ServletFileUpload.isMultipartContent(request)){
				// Create a factory for disk-based file items
				ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
				List<FileItem> fileItemsList = servletFileUpload.parseRequest(request);
				for(int counter=0;counter<fileItemsList.size();counter++){
					FileItem item=fileItemsList.get(counter);
					String fieldName=item.getFieldName();
					if(item.isFormField()){
						System.out.println(counter+" : "+fieldName+"   Value:"+request.getParameter(fieldName));
						request.getParameter(fileItemsList.get(counter).getFieldName());
					}else{
						System.out.println(counter+" : "+fieldName+"   FILE");
						printItemFile(item);
					}
				}
			}else{
				System.out.println("This is not multipart ");
			}
		}else{
			// maybe first load page  
		}
	%>
	
	<%!
		private void printItemFile(FileItem item){
			try{
				BufferedReader reader=new BufferedReader(new InputStreamReader(item.getInputStream()));
				String currentString=null;
				while( (currentString=reader.readLine())!=null ){
					System.out.println(currentString);
				}
			}catch(Exception ex){
				System.err.println("Exception: "+ex.getMessage());
			}
		}
	%>

	<form method="post" enctype="mutlipart/form-data">
		<table>
			<tr>
				<td>
					Upload file
				</td>
				<td>
					<input name="file_upload" type="file" value="C:\\temp.txt " />
				</td>
			</tr>
			<tr>
				<td>
					Edit text
				</td>
				<td>
					<input name="edit_text" type="text" value="temp.txt" />
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<input type="submit" value="upload" />
				</td>
			</tr>
		</table>
		
	</form>
</body>
</html>