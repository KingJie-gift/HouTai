package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
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

import service.impl.CommunicateServiceImpl;
import service.impl.SubjectServiceImpl;
import entity.Communicate;
import entity.Page;
import entity.Subject;

public class CommunicateServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		CommunicateServiceImpl peservice = new CommunicateServiceImpl();
		SubjectServiceImpl subService = new SubjectServiceImpl();
		String op = request.getParameter("op");
		if (op == null || op.equals("page")) {
			String title=request.getParameter("title");
			title=title==null?"":title;
			String subjectId=request.getParameter("subjectId");
			int sub=subjectId==null?-1:Integer.parseInt(subjectId);
			Page pg = new Page();
			pg.setPageSize(6);
			pg.setAllCount(peservice.getAllcount(title));
			String temp = request.getParameter("index");
			int index = temp == null ? 1 : Integer.parseInt(temp);
			pg.setPageIndex(index);
			peservice.get4page(pg, title, sub);
			List<Subject>list=subService.getAll();
			request.setAttribute("list", list);
			//request.setAttribute("title", title);
			request.setAttribute("pg", pg);
			request.getRequestDispatcher("indexCommunicate.jsp").forward(
					request, response);
		} else if (op.equals("toadd")) {
			List<Subject> list = subService.getAll();
			request.setAttribute("list", list);
			request.getRequestDispatcher("addCommunicate.jsp").forward(request,
					response);
		} else if (op.equals("add")) {

			// �ļ��ϴ�
			// �жϴ������Ƿ�����ϴ��ļ�
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			Map<String, String> params = new HashMap();
			String path = "";
			String fileName = "";
			if (isMultipart) {
				// �Ǵ����ļ��ϴ�������
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
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
										".bmp", ".png");
								if (!typeList.contains(type)) {
									out.print("<h1>�ļ����Ͳ��Ϸ���</h1>");
									return;
								}
								// �ٴ��ж��ϴ����ļ��Ƿ����ļ���
								if (fileName != null && !fileName.equals("")) {
									// ��ȡ�ļ��ϴ��ķ�����·����ַ
									path = request.getSession().getServletContext().getRealPath("images/");
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
			String title = "";
			String content = "";
			String smallContent = "";
			String subject = "";
			for (Map.Entry<String, String> entry : params.entrySet()) {

				if (entry.getKey().equals("title")) {
					title = entry.getValue();
				} else if (entry.getKey().equals("subjectContent")) {
					content = entry.getValue();
				} else if (entry.getKey().equals("smallContent")) {
					smallContent = entry.getValue();
				} else if (entry.getKey().equals("subject")) {
					subject = entry.getValue();
				}
			}
			int subjectId = Integer.parseInt(subject);
			Communicate n = new Communicate();
			Date now = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
			String date = df.format(now);
			n.setDate(date);
			n.setTitle(title);
			n.setContent(content);
			n.setSmallContent(smallContent);
			n.setImg("images/" + fileName);
			n.setSubjectId(subjectId);
			int result = peservice.add(n);
			if (result > 0) {
				request.getSession().setAttribute("msg", "�����ɹ�");
				response.sendRedirect("indexCommunicate.jsp");
			} else {
				request.getSession().setAttribute("msg", "����ʧ��");
				response.sendRedirect("indexCommunicate.jsp");
			}
		} else if (op.equals("toupdate")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Communicate po = peservice.getById(id);
			request.setAttribute("po", po);
			List<Subject> glist = subService.getAll();
			request.setAttribute("glist", glist);
			request.getRequestDispatcher("updateCommunicate.jsp").forward(request,
					response);
		} else if (op.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String smallContent = request.getParameter("smallContent");
			int subjectid = Integer.parseInt(request.getParameter("subject"));
			Communicate c = new Communicate();
			c.setId(id);
			c.setTitle(title);
			c.setContent(content);
			c.setSmallContent(smallContent);
			c.setSubjectId(subjectid);
			int result = peservice.update(c);
			if (result > 0) {
				out.print("<script>alert('�޸ĳɹ�');location.href='indexCommunicate.jsp';</script>");
			} else {
				out.print("<script>alert('�޸�ʧ��');location.href='indexCommunicate.jsp';</script>");
			}
		} else if (op.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = peservice.delete(id);
			if (result > 0) {
				out.print("<script>alert('ɾ���ɹ�');location.href='indexCommunicate.jsp';</script>");
			} else {
				out.print("<script>alert('ɾ��ʧ��');location.href='indexCommunicate.jsp';</script>");
			}
		}
}
}
