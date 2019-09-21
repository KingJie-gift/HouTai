package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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

import service.impl.SubjectServiceImpl;
import service.impl.TeacherServiceImpl;
import entity.Page;
import entity.Subject;
import entity.Teacher;

public class SubjectServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SubjectServiceImpl peservice = new SubjectServiceImpl();
		TeacherServiceImpl tsi = new TeacherServiceImpl();
		String op = request.getParameter("op");
		if (op == null || op.equals("page")) {
			String subjectName = request.getParameter("subjectName");
			subjectName = subjectName == null ? "" : subjectName;
			Page pg = new Page();
			pg.setPageSize(6);
			pg.setAllCount(peservice.getAllcount(subjectName));
			String temp = request.getParameter("index");
			int index = temp == null ? 1 : Integer.parseInt(temp);
			pg.setPageIndex(index);
			peservice.get4page(pg, subjectName);
			List<Subject> list = peservice.getAll();
			request.setAttribute("list", list);
			request.setAttribute("pg", pg);
			request.getRequestDispatcher("indexSubject.jsp").forward(request,
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

			String subjectTitle = "";
			String smallImg = "";
			String subjectContent = "";
			String subjectName = "";
			for (Map.Entry<String, String> entry : params.entrySet()) {

				if (entry.getKey().equals("subjectTitle")) {
					subjectTitle = entry.getValue();
				} else if (entry.getKey().equals("smallImg")) {
					smallImg = entry.getValue();
				} else if (entry.getKey().equals("subjectContent")) {
					subjectContent = entry.getValue();
				} else if (entry.getKey().equals("subjectName")) {
					subjectName = entry.getValue();
				}
			}
			Subject n = new Subject();
			n.setSubjectName(subjectName);
			n.setSubjectTitle(subjectTitle);
			n.setSmallImg("images/" + fileName);
			n.setBackgroundImg("images/2-1G124191G4626.jpg");
			n.setSubjectContent(subjectContent);
			int result = peservice.add(n);
			if (result > 0) {
				request.getSession().setAttribute("msg", "�����ɹ�");
				response.sendRedirect("indexSubject.jsp");
				request.getSession().setAttribute("msg", "����ʧ��");
			} else {
				response.sendRedirect("indexSubject.jsp");
			}
		} else if (op.equals("toupdate")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Subject po = peservice.getById(id);
			request.setAttribute("po", po);
			request.getRequestDispatcher("updateSubject.jsp").forward(request,
					response);
		} else if (op.equals("update")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String subjectTitle = request.getParameter("subjectTitle");
			String smallImg = request.getParameter("smallImg");
			String subjectContent = request.getParameter("subjectContent");
			String subjectName = request.getParameter("subjectName");
			Subject s = new Subject();
			s.setSubjectId(id);
			s.setSubjectTitle(subjectTitle);
			s.setSmallImg(smallImg);
			s.setSubjectContent(subjectContent);
			s.setSubjectName(subjectName);
			int result = peservice.update(s);
			if (result > 0) {
				out.print("<script>alert('�޸ĳɹ�');location.href='indexSubject.jsp';</script>");
			} else {
				out.print("<script>alert('�޸�ʧ��');location.href='indexSubject.jsp';</script>");
			}
		} else if (op.equals("delete")) {
			List<Teacher> getTea = tsi.getAll(0);
			int id = Integer.parseInt(request.getParameter("id"));
			boolean isTrue=false;
			for (Teacher t : getTea) {
				if (t.getSubjectId() == id) {
					out.print("<script>alert('�ÿγ̻�����ʦ�ν̣�����ɾ����');location.href='indexSubject.jsp';</script>");
					return;
				}else{
					isTrue=true;
				}
			}
			if(isTrue){
				int result = peservice.delete(id);
				if (result > 0) {
					out.print("<script>alert('ɾ���ɹ�');location.href='indexSubject.jsp';</script>");
				} else {
					out.print("<script>alert('ɾ��ʧ��');location.href='indexSubject.jsp';</script>");
				}
			}
		}

		out.flush();
		out.close();
	}

}
