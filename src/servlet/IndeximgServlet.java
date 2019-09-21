package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

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
import entity.Indeximg;
import entity.Page;
import service.impl.IndeximgServiceimpl;

public class IndeximgServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		IndeximgServiceimpl isi = new IndeximgServiceimpl();
		String op = request.getParameter("op");
		if (op == null || op.equals("page")) {
			Page pg = new Page();
			pg.setPageSize(6);
			pg.setAllCount(isi.getAllCount());
			String temp = request.getParameter("index");
			int index = temp == null ? 1 : Integer.parseInt(temp);
			pg.setPageIndex(index);
			isi.get4Page(pg);
			request.setAttribute("pg", pg);
			request.getRequestDispatcher("IndeximgIndex.jsp").forward(request,
					response);
		} else if (op.equals("add")) {
			FileItemFactory factory = new DiskFileItemFactory();
			// 创建文件上传处理器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 开始解析请求信息
			List items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			// 对所有请求信息进行判断
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				String fileName = "";
				FileItem item = (FileItem) iter.next();
				// 信息为普通的格式
				if (item.isFormField()) {
					/*
					 * String fieldName = item.getFieldName(); String value =
					 * item.getString(); request.setAttribute(fieldName, value);
					 */
				}
				// 信息为文件格式
				else {
					fileName = item.getName();
					int index = fileName.lastIndexOf("\\");
					fileName = fileName.substring(index + 1);
					// 文件的存储路径
					// /代表文件目录 /img代表文件目录下的img文件夹
					String basePath = getServletContext().getRealPath(
							"/picture");
					// 创建要存储的文件对象 参数(存储路径，文件名)
					File file = new File(basePath, fileName);
					try {
						// 文件存储方法
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// 创建对象，新增进入数据库
				Indeximg i = new Indeximg();
				i.setImgSrc("picture/" + fileName);
				int result = isi.add(i);
				if (result > 0) {
					getServletContext().getRequestDispatcher(
							"/IndeximgIndex.jsp").forward(request, response);
				}
			}
		} else if (op.equals("delete")) {
			int imgId = Integer.parseInt(request.getParameter("imgId"));
			int result = isi.delete(imgId);
			if (result > 0) {
				out.print("<script>alert('删除成功!');location.href='IndeximgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('删除失败!');location.href='IndeximgIndex.jsp'</script>");
			}
		}else if(op.equals("update")){
			int imgId = Integer.parseInt(request.getParameter("imgId"));
			String imgSrc="picture/"+request.getParameter("imgSrc");
			Indeximg i=new Indeximg();
			i.setImgId(imgId);
			i.setImgSrc(imgSrc);
			int result=isi.update(i);
			if (result > 0) {
				out.print("<script>alert('修改成功!');location.href='IndeximgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('修改失败!');location.href='IndeximgIndex.jsp'</script>");
			}
		}
		out.flush();
		out.close();
	}
}