package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import service.impl.SubjectServiceImpl;
import service.impl.TeacherServiceImpl;
import entity.Page;
import entity.Subject;
import entity.Teacher;


public class TeacherServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		TeacherServiceImpl tsi = new TeacherServiceImpl();
		SubjectServiceImpl ssi = new SubjectServiceImpl();

		String op = request.getParameter("op");
		if (op == null || op.equals("page")) {
//			// ��ʦ�ֲ�ͼ
//			List<Teacher> teacherlist = tsi.getAll();
//			List<Subject> subjectlist = ssi.getAll();
//			request.setAttribute("teacherlist", teacherlist);
			Page pg=new Page();
			pg.setPageSize(6);
			pg.setAllCount(tsi.getAllCount());
			String temp=request.getParameter("index");
			int index=temp==null?1:Integer.parseInt(temp);
			String teacherName=request.getParameter("teacherName");
			String subjectIdtemp=request.getParameter("subjectIds");
		    int  subjectIds=subjectIdtemp==null?-1:Integer.parseInt(subjectIdtemp);
		    List<Subject> su=ssi.getAll();
			pg.setPageIndex(index);
			tsi.get4Page(pg,teacherName,subjectIds);
			request.setAttribute("su", su);
			request.setAttribute("pg", pg);
			List<Subject> slist = ssi.getAll();
			request.setAttribute("slist", slist);
			request.getRequestDispatcher("column.jsp").forward(request,
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
									out.print("<h1>�ļ����Ͳ��Ϸ���</h1>");
									return;
								}
								// �ٴ��ж��ϴ����ļ��Ƿ����ļ���
								if (fileName != null && !fileName.equals("")) {
									// ��ȡ�ļ��ϴ��ķ�����·����ַ
									path = request.getSession()
											.getServletContext()
											.getRealPath("picture/");
									// ����һ���ļ�
									File file = new File(path, fileName);
									// �ϴ��ļ�
									try {
										fi.write(file);
									} catch (Exception e) {
										e.printStackTrace();
									}
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
			String teacherName = "";
			String introduce = "";
			String shortIntroduce = "";
			String subjectId = "";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (entry.getKey().equals("teacherName")) {
					teacherName = entry.getValue();
				} else if (entry.getKey().equals("introduce")) {
					introduce = entry.getValue();
				} else if (entry.getKey().equals("shortIntroduce")) {
					shortIntroduce = entry.getValue();
				} else if (entry.getKey().equals("subjectId")) {
					subjectId = entry.getValue();
				}
			}
			Teacher t = new Teacher();
			t.setTeacherName(teacherName);
			t.setShortIntroduce(shortIntroduce);
			t.setSubjectId(Integer.parseInt(subjectId));
			t.setIntroduce(introduce);
			t.setTeacherImg("picture/" + fileName);
			int result = tsi.add(t);
			if (result > 0) {
				out.print("<script>alert('�����ɹ�');</script>");
			} else {
				out.print("<script>alert('�����ɹ�');</script>");
			}
			response.sendRedirect("column.jsp");
		} else if (op.equals("toupdate")) {
			List<Subject> slist = ssi.getAll();
			request.setAttribute("slist", slist);
			int teacherId = Integer.parseInt(request.getParameter("teacherId"));
			Teacher t = tsi.getById(teacherId);
			request.setAttribute("t", t);
			request.getRequestDispatcher("UpdateTeacher.jsp").forward(request,
					response);
		} else if (op.equals("update")) {
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
			String teacherId="";
			String teacherName="";
			String subjectId ="";
			String introduce ="";
			String shortIntroduce ="";
			for(Map.Entry<String, String> entry:params.entrySet()){
				if(entry.getKey().equals("teacherId")){
					teacherId=entry.getValue();
				}else if(entry.getKey().equals("teacherName")){
					teacherName=entry.getValue();
				}else if(entry.getKey().equals("subjectId")){
					subjectId=entry.getValue();
				}else if(entry.getKey().equals("introduce")){
					introduce=entry.getValue();
				}else if(entry.getKey().equals("shortIntroduce")){
					shortIntroduce=entry.getValue();
				}
			}
			int subjectId1=Integer.parseInt(subjectId);
			String teacherImg="";
			String path1="";
				if(fileName==""){
					for(Map.Entry<String, String> entry:params.entrySet()){
						if(entry.getKey().equals("teacherImg")){
							teacherImg=entry.getValue();
						}
					}
					fileName=teacherImg;
				}
				if(fileName.substring(0, 7).equals("picture")){
					path1=fileName;
				}else{
					path1="picture\\"+fileName;	
				}
				Teacher pd=new Teacher();
				pd.setIntroduce(introduce);
				pd.setShortIntroduce(shortIntroduce);
				pd.setSubjectId(subjectId1);
				pd.setTeacherId(Integer.parseInt(teacherId));
				pd.setTeacherImg(path1);
				pd.setTeacherName(teacherName);
				int result=tsi.update(pd);
				if(result>0){
					out.print("<script>alert('�޸ĳɹ�');location.href='column.jsp';</script>");
				}else{
					out.print("<script>alert('�޸�ʧ��');location.href='column.jsp';</script>");
				}
		}else if(op.equals("deleteUpdate")){
			int teacherId=Integer.parseInt(request.getParameter("teacherId"));
			int result=tsi.delUpdate(teacherId);
			if(result>0){
				out.print("<script>alert('ɾ���ɹ�');location.href='column.jsp';</script>");
			}else{
				out.print("<script>alert('ɾ��ʧ��');location.href='column.jsp';</script>");
			}
		}else if(op.equals("checkname")){
			String teacherName=request.getParameter("teacherName");
			teacherName=new String(teacherName.getBytes("iso-8859-1"),"utf-8");
			int count=tsi.checkname(teacherName);
			out.print(count);
		}
			out.flush();
			out.close();
		
	}
}
