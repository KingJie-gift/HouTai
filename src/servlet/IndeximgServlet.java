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
			// �����ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			// ��ʼ����������Ϣ
			List items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			// ������������Ϣ�����ж�
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				String fileName = "";
				FileItem item = (FileItem) iter.next();
				// ��ϢΪ��ͨ�ĸ�ʽ
				if (item.isFormField()) {
					/*
					 * String fieldName = item.getFieldName(); String value =
					 * item.getString(); request.setAttribute(fieldName, value);
					 */
				}
				// ��ϢΪ�ļ���ʽ
				else {
					fileName = item.getName();
					int index = fileName.lastIndexOf("\\");
					fileName = fileName.substring(index + 1);
					// �ļ��Ĵ洢·��
					// /�����ļ�Ŀ¼ /img�����ļ�Ŀ¼�µ�img�ļ���
					String basePath = getServletContext().getRealPath(
							"/picture");
					// ����Ҫ�洢���ļ����� ����(�洢·�����ļ���)
					File file = new File(basePath, fileName);
					try {
						// �ļ��洢����
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// �������������������ݿ�
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
				out.print("<script>alert('ɾ���ɹ�!');location.href='IndeximgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('ɾ��ʧ��!');location.href='IndeximgIndex.jsp'</script>");
			}
		}else if(op.equals("update")){
			int imgId = Integer.parseInt(request.getParameter("imgId"));
			String imgSrc="picture/"+request.getParameter("imgSrc");
			Indeximg i=new Indeximg();
			i.setImgId(imgId);
			i.setImgSrc(imgSrc);
			int result=isi.update(i);
			if (result > 0) {
				out.print("<script>alert('�޸ĳɹ�!');location.href='IndeximgIndex.jsp'</script>");
			} else {
				out.print("<script>alert('�޸�ʧ��!');location.href='IndeximgIndex.jsp'</script>");
			}
		}
		out.flush();
		out.close();
	}
}