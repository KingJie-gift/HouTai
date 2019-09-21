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
			// �ļ��ϴ�
			// �жϴ������Ƿ�����ϴ��ļ�
			String fileName = "";
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			Map<String, String> params = new HashMap();
			String path = "";
			if (isMultipart) {
				// �Ǵ����ļ��ϴ�������
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				upload.setSizeMax(1024 * 1024);
				try {

					List<FileItem> items;
					try {
						items = upload.parseRequest(request);
						for (FileItem fi : items) {
							if (fi.isFormField()) {// �ж��Ƿ�ʱ��ͨ��Ԫ��
								String name = fi.getFieldName();// ��ȡ��Ԫ��name���Ե�ֵ
								String value = new String(fi.getString()
										.getBytes("ISO-8859-1"), "utf-8");
								params.put(name, value);

							} else {// ���ļ��ϴ���Ԫ��
									// ��ȡ�ϴ��ļ����ļ���
								fileName = fi.getName();
								// ��ȡ�ļ����ͣ���.��
								String type = fileName.substring(fileName
										.lastIndexOf("."));
								List<String> typeList = Arrays.asList(".jpg",
										".bmp", ".png", ".PNG");
								if (!typeList.contains(type)) {
									out.print("<h1>�ļ����Ͳ��Ϸ�!</h1>");
									return;
								}
								// �ٴ��ж��ϴ����ļ��Ƿ����ļ���
								if (fileName != null && !fileName.equals("")) {
									// ��ȡ�ļ��ϴ��ķ�����·����ַ
									path = request.getSession()
											.getServletContext()
											.getRealPath("images/");
									// ����һ���ļ�
									File file = new File(path, fileName);
									// �ϴ��ļ�
									try {
										fi.write(file);
									} catch (Exception e) {
										e.printStackTrace();
									}
									out.print("�ϴ��ļ��ɹ����ļ����ǣ�" + fileName);
								}
							}
						}
					} catch (FileUploadException e1) {
						e1.printStackTrace();
					}

				} catch (Exception ex) {
					out.print("�ļ���С���������ƣ�");
				}
			} else {
				out.print("û���ҵ�Ҫ�ϴ����ļ�");
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
				request.getSession().setAttribute("msg", "�����ɹ�");
				response.sendRedirect("ClassimgIndex.jsp");
			} else {
				request.getSession().setAttribute("msg", "����ʧ��");
				response.sendRedirect("ClassimgIndex.jsp");
			}
		} else if (op.equals("delete")) {
			int imgId = Integer.parseInt(request.getParameter("imgId"));
			int result = csi.delete(imgId);
			if (result > 0) {
				out.print("<script>alert('ɾ���ɹ�!');location.href='ClassimgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('ɾ��ʧ��!');location.href='ClassimgIndex.jsp'</script>");
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
				out.print("<script>alert('�޸ĳɹ�!');location.href='ClassimgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('�޸�ʧ��!');location.href='ClassimgIndex.jsp'</script>");
			}
		}
		out.flush();
		out.close();
	}

}
