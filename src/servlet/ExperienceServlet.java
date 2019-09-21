package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entity.Experience;
import entity.Page;
import entity.Subject;
import entity.Teacher;
import service.impl.ExperienceServiceImpl;
import service.impl.SubjectServiceImpl;

public class ExperienceServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String op = request.getParameter("op");
		ExperienceServiceImpl esi = new ExperienceServiceImpl();
		SubjectServiceImpl ssi = new SubjectServiceImpl();
		if (op == null || op.equals("page")) {
			Page pg = new Page();
			pg.setPageSize(6);
			pg.setAllCount(esi.getAllCount());
			String temp = request.getParameter("index");
			int index = temp == null ? 1 : Integer.parseInt(temp);
			
			String subjectIdtemp=request.getParameter("subjectId");
		      int subjectId=subjectIdtemp==null?-1:Integer.parseInt(subjectIdtemp);
		      List<Subject>slist=ssi.getAll();
			pg.setPageIndex(index);
			esi.get4Page(pg,subjectId);
			request.setAttribute("slist", slist);
			request.setAttribute("pg", pg);
			request.getRequestDispatcher("indexExperience.jsp").forward(
					request, response);
		} else if (op.equals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			int result = esi.delete(id);
			if (result > 0) {
				out.print("<script>alert('ɾ���ɹ�');location.href='indexExperience.jsp'</script>");
			} else {
				out.print("<script>alert('ɾ��ʧ��');location.href='indexExperience.jsp'</script>");
			}
		} else if (op.equals("toadd")) {
			List<Subject> slist = ssi.getAll();
			request.setAttribute("slist", slist);
			request.getRequestDispatcher("addExpection.jsp").forward(
					request, response);
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
			String subjectId = "";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (entry.getKey().equals("title")) {
					title = entry.getValue();
				} else if (entry.getKey().equals("content")) {
					content = entry.getValue();
				} else if (entry.getKey().equals("smallContent")) {
					smallContent = entry.getValue();
				} else if (entry.getKey().equals("subjectId")) {
					subjectId = entry.getValue();
				}
			}
			int subject = Integer.parseInt(subjectId);
			Experience e = new Experience();
			e.setContent(content);
			e.setSmallContent(smallContent);
			Date now = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
			String date = df.format(now);
			e.setDate(date);
			e.setTitle(title);
			e.setImg("images/" + fileName);
			e.setSubjectId(subject);
			int result = esi.add(e);
			if (result > 0) {
				request.getSession().setAttribute("msg", "�����ɹ�");
				response.sendRedirect("indexExperience.jsp");
			} else {
				request.getSession().setAttribute("msg", "����ʧ��");
				response.sendRedirect("indexExperience.jsp");
			}
		} else if (op.equals("toupdate")) {
			List<Subject> slist = ssi.getAll();
			request.setAttribute("slist", slist);
			int id = Integer.parseInt(request.getParameter("id"));
			Experience ei = esi.getById(id);
			request.setAttribute("ei", ei);
			request.getRequestDispatcher("updateExpection.jsp").forward(
					request, response);
		} else if (op.equals("update")) {
//			int id = Integer.parseInt(request.getParameter("id"));
//			String title = request.getParameter("title");
//			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
//			String content = request.getParameter("content");
//			String smallContent = request.getParameter("smallContent");
//			Experience e = new Experience();
//			e.setId(id);
//			e.setContent(content);
//			e.setSmallContent(smallContent);
//			e.setTitle(title);
//			e.setSubjectId(subjectId);
//			int result = esi.update(e);
//			if (result > 0) {
//				out.print("<script>alert('�޸ĳɹ�');location.href='indexExperience.jsp'</script>");
//			} else {
//				out.print("<script>alert('�޸�ʧ��');location.href='indexExperience.jsp'</script>");
//			}
//			
			boolean isMultipart=ServletFileUpload.isMultipartContent(request);
			Map<String, String> params = new HashMap();
			String path="";
			String fileName="";
			if(isMultipart){
				//�Ǵ����ļ��ϴ�������
				FileItemFactory factory=new DiskFileItemFactory();
				ServletFileUpload upload=new ServletFileUpload(factory);
				
				upload.setSizeMax(1024*2024);
				try{
					
				List<FileItem> items=upload.parseRequest(request);
				for(FileItem fi : items){
					if(fi.isFormField()){//�ж��Ƿ�ʱ��ͨ��Ԫ��
						String name = fi.getFieldName(); 
						String value = new String(fi.getString().getBytes("ISO-8859-1"), "utf-8"); 
						params.put(name, value);						
						
					}else{//���ļ��ϴ���Ԫ��
						//��ȡ�ϴ��ļ����ļ���
						fileName=fi.getName();
						//��ȡ�ļ����ͣ���.��
						String type=fileName.substring(fileName.lastIndexOf("."));
						List<String> typeList=Arrays.asList(".jpg",".bmp",".png");
						if(!typeList.contains(type)){
							out.print("<h1>�ļ����Ͳ��Ϸ���</h1>");
							return;
						}
						//�ٴ��ж��ϴ����ļ��Ƿ����ļ���
						if(fileName!=null&&!fileName.equals("")){
							//��ȡ�ļ��ϴ��ķ�����·����ַ
							path=request.getSession().getServletContext().getRealPath("picture/");
							//����һ���ļ�
							File file=new File(path,fileName);
							//�ϴ��ļ�
							fi.write(file);
						}
					}
				}
				}catch(FileUploadBase.SizeLimitExceededException ex){
					out.print("�ļ���С���������ƣ�");
				}catch (StringIndexOutOfBoundsException e) {
					out.print("");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				out.print("û���ҵ�Ҫ�ϴ����ļ�");
			}
			String id="";
			String title="";
			String subjectId ="";
			String content ="";
			String smallContent ="";
			for(Map.Entry<String, String> entry:params.entrySet()){
				if(entry.getKey().equals("id")){
					id=entry.getValue();
				}else if(entry.getKey().equals("title")){
					title=entry.getValue();
				}else if(entry.getKey().equals("subjectId")){
					subjectId=entry.getValue();
				}else if(entry.getKey().equals("content")){
					content=entry.getValue();
				}else if(entry.getKey().equals("smallContent")){
					smallContent=entry.getValue();
				}
			}
			int id1=Integer.parseInt(id);
			int subjectId1=Integer.parseInt(subjectId);
			String img="";
			String path1="";
				if(fileName==""){
					for(Map.Entry<String, String> entry:params.entrySet()){
						if(entry.getKey().equals("img")){
							img=entry.getValue();
						}
					}
					fileName=img;
				}
				if(fileName.substring(0, 7).equals("picture")){
					path1=fileName;
				}else{
					path1="picture//"+fileName;	
				}
				Experience e = new Experience();
				e.setId(id1);
				e.setContent(content);
				e.setSmallContent(smallContent);
				e.setTitle(title);
				//pd.setTeacherImg(path1);
				e.setImg(path1);
				e.setSubjectId(subjectId1);
				int result=esi.update(e);
				if(result>0){
					out.print("<script>alert('�޸ĳɹ�');location.href='indexExperience.jsp';</script>");
				}else{
					out.print("<script>alert('�޸�ʧ��');location.href='indexExperience.jsp';</script>");
				}
	}
		out.flush();
		out.close();
	}

}
