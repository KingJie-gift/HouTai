package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entity.Classimg;
import entity.News;
import entity.Page;
import service.impl.ClassimgServiceimpl;

public class ClassimgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		ClassimgServiceimpl csi = new ClassimgServiceimpl();
		String op = request.getParameter("op");
		if (op == null || op.equals("page")) {
			Page pg = new Page();
			pg.setPageSize(6);
			pg.setAllCount(csi.getAllCount());
			String temp = request.getParameter("index");
			int index = temp == null ? 1 : Integer.parseInt(temp);
			pg.setPageIndex(index);
			csi.get4Page(pg);
			request.setAttribute("pg", pg);
			request.getRequestDispatcher("ClassimgIndex.jsp").forward(request,
					response);
		} else if (op.equals("add")) {
			// 文件上传
			// 判断此请求是否包含上传文件
			String fileName = "";
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			Map<String, String> params = new HashMap();
			String path = "";
			if (isMultipart) {
				// 是带有文件上传的请求
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				upload.setSizeMax(1024 * 1024);
				try {

					List<FileItem> items;
					try {
						items = upload.parseRequest(request);
						for (FileItem fi : items) {
							if (fi.isFormField()) {// 判断是否时普通表单元素
								String name = fi.getFieldName();// 获取表单元素name属性的值
								String value = new String(fi.getString()
										.getBytes("ISO-8859-1"), "utf-8");
								params.put(name, value);

							} else {// 是文件上传表单元素
									// 获取上传文件的文件名
								fileName = fi.getName();
								// 获取文件类型（带.）
								String type = fileName.substring(fileName
										.lastIndexOf("."));
								List<String> typeList = Arrays.asList(".jpg",
										".bmp", ".png", ".PNG");
								if (!typeList.contains(type)) {
									out.print("<h1>文件类型不合法!</h1>");
									return;
								}
								// 再次判断上传的文件是否有文件名
								if (fileName != null && !fileName.equals("")) {
									// 获取文件上传的服务器路径地址
									path = request.getSession()
											.getServletContext()
											.getRealPath("images/");
									// 创建一个文件
									File file = new File(path, fileName);
									// 上传文件
									try {
										fi.write(file);
									} catch (Exception e) {
										e.printStackTrace();
									}
									out.print("上传文件成功，文件名是：" + fileName);
								}
							}
						}
					} catch (FileUploadException e1) {
						e1.printStackTrace();
					}

				} catch (Exception ex) {
					out.print("文件大小超出了限制！");
				}
			} else {
				out.print("没有找到要上传的文件");
			}
			String content = "";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (entry.getKey().equals("content")) {
					content = entry.getValue();
				}
			}
			Classimg c = new Classimg();
			c.setContent(content);
			c.setImgSrc("images/" + fileName);
			int result = csi.add(c);
			if (result > 0) {
				request.getSession().setAttribute("msg", "新增成功");
				response.sendRedirect("ClassimgIndex.jsp");
			} else {
				request.getSession().setAttribute("msg", "新增失败");
				response.sendRedirect("ClassimgIndex.jsp");
			}
		} else if (op.equals("delete")) {
			int imgId = Integer.parseInt(request.getParameter("imgId"));
			int result = csi.delete(imgId);
			if (result > 0) {
				out.print("<script>alert('删除成功!');location.href='ClassimgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('删除失败!');location.href='ClassimgIndex.jsp'</script>");
			}
		} else if (op.equals("update")) {
			int imgId = Integer.parseInt(request.getParameter("imgId"));
			String content = request.getParameter("content");
			content=new String(content.getBytes("iso-8859-1"),"utf-8");
			Classimg c = new Classimg();
			c.setImgId(imgId);
			c.setContent(content);
			int result = csi.update(c);
			if (result > 0) {
				out.print("<script>alert('修改成功!');location.href='ClassimgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('修改失败!');location.href='ClassimgIndex.jsp'</script>");
			}
		}
		out.flush();
		out.close();
	}

}
