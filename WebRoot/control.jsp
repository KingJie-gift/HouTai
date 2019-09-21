<%@page import="org.apache.commons.fileupload.FileUploadBase"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	
	//文件上传
	//判断此请求是否包含上传文件
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	if (isMultipart) {//是带有上传文件的
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(1000 * 1024);

		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem fi : items) {
				if (fi.isFormField()) {//判断是否普通表单元素
					String name = fi.getFieldName();//获取表单元素name属性的值

					if (name.equals("name")) {
						String value = fi.getString();
						out.print(value + "上传了文件");
						
					}

				} else {
					//是文件上传表单元素
					//获取上传文件的文件名
					String fileName = fi.getName();
					//获取文件类型
					String type = fileName.substring(fileName
							.lastIndexOf("."));
					List<String> typeList = Arrays.asList(".jpg",
							".bmp", ".png");
					if (!typeList.contains(type)) {
						//out.print("<h1>文件类型不合法</h1>");
						out.print("<script>alert('<h1>文件类型不合法</h1>');location.href='IndeximgIndex.jsp';</script>");
						return;
					}
					//再次判断上传的文件是否有文件名
					if (fileName != null && !fileName.equals("")) {
						//获取文件上传的服务器的路径地址
						String path = request.getSession()
								.getServletContext()
								.getRealPath("picture/");
						//创建一个文件
						File file = new File(path, fileName);
						fi.write(file);
						/* String op=request.getParameter("op");
						
						if(op.equals("addindexImg")){
						request.getRequestDispatcher("htServlet?op=addindexImg&imgSrc="+fileName).forward(request, response);
						}else if(op==""){ */
						int imgId=Integer.parseInt(request.getParameter("imgId"));
						//out.print("上传文件成功，文件名:" + fileName);
						request.getRequestDispatcher("IndeximgServlet?op=update&imgId="+imgId+"&imgSrc="+fileName).forward(request, response);
						//out.print("<script>alert('上传成功！文件名："+fileName+"');location.href='B-indexImg.jsp';</script>");
						//}
						
					}

				}
			}
		} catch (FileUploadBase.SizeLimitExceededException ex) {
			//out.print("上传文件过大");
			out.print("<script>alert('上传文件过大');location.href='IndeximgIndex.jsp';</script>");
		}
	} else {
		//out.print("没有文件");
		out.print("<script>alert('没有文件');location.href='IndeximgIndex.jsp';</script>");
	}
%>