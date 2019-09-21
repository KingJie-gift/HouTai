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

import service.impl.NewsServiceimpl;
import service.impl.SubjectServiceImpl;
import entity.News;
import entity.Page;
import entity.Subject;

public class NewsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		SubjectServiceImpl ssi = new SubjectServiceImpl();
		NewsServiceimpl nsi = new NewsServiceimpl();
		String op = request.getParameter("op");
		if (op == null || op.equals("page")) {
			Page pg = new Page();
			pg.setPageSize(6);
			pg.setAllCount(nsi.getAllCount());
			String title=request.getParameter("title");
			String temp = request.getParameter("index");
			int index = temp == null ? 1 : Integer.parseInt(temp);
			String subject=request.getParameter("subjectId");
			int subjectId=subject==null?-1:Integer.parseInt(subject);
			List<Subject>su=ssi.getAll();
			request.setAttribute("su", su);
			pg.setPageIndex(index);
			nsi.get4Page(pg, title, subjectId);
			request.setAttribute("pg", pg);
			request.getRequestDispatcher("newsIndex.jsp").forward(request,
					response);
		} else if (op.equals("toadd")) {
			List<Subject> slist = ssi.getAll();
			request.setAttribute("slist", slist);
			request.getRequestDispatcher("addNews.jsp").forward(request,
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
			News n = new News();
			Date now = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// �������ڸ�ʽ
			String date = df.format(now);
			n.setDate(date);
			n.setTitle(title);
			n.setContent(content);
			n.setSmallContent(smallContent);
			n.setSubjectId(Integer.parseInt(subjectId));
			n.setNewsImg("images/" + fileName);
			int result = nsi.add(n);
			if (result > 0) {
				request.getSession().setAttribute("msg", "�����ɹ�");
				response.sendRedirect("newsIndex.jsp");
			} else {
				request.getSession().setAttribute("msg", "����ʧ��");
				response.sendRedirect("newsIndex.jsp");
			}
		} else if (op.equals("delete")) {
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			int result = nsi.delete(newsId);
			if (result > 0) {
				out.print("<script>alert('ɾ���ɹ�!');location.href='newsIndex.jsp'</script>");
			} else {
				out.print("<script>alert('ɾ��ʧ��!');location.href='newsIndex.jsp'</script>");
			}
		} else if (op.equals("toupdate")) {
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			News nw = nsi.getById(newsId);
			request.setAttribute("nw", nw);
			List<Subject> slist = ssi.getAll();
			request.setAttribute("slist", slist);
			request.getRequestDispatcher("updateNews.jsp").forward(request,
					response);
		} else if (op.equals("update")) {
//			int newsId = Integer.parseInt(request.getParameter("newsId"));
//			String title = request.getParameter("title");
//			String content = request.getParameter("content");
//			String smallContent = request.getParameter("smallContent");
//			int subjectId = Integer.parseInt(request.getParameter("subjectId"));
//			News n = new News();
//			n.setNewsId(newsId);
//			n.setTitle(title);
//			n.setContent(content);
//			n.setSmallContent(smallContent);
//			n.setSubjectId(subjectId);
//			int result = nsi.update(n);
//			if (result > 0) {
//				out.print("<script>alert('�޸ĳɹ�!');location.href='newsIndex.jsp'</script>");
//			} else {
//				out.print("<script>alert('�޸�ʧ��!');location.href='newsIndex.jsp'</script>");
//			}
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
			String newsId="";
			String title="";
			String subjectId ="";
			String content ="";
			String smallContent ="";
			for(Map.Entry<String, String> entry:params.entrySet()){
				if(entry.getKey().equals("newsId")){
					newsId=entry.getValue();
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
			int newsId1=Integer.parseInt(newsId);
			int subjectId1=Integer.parseInt(subjectId);
			String newsImg="";
			String path1="";
				if(fileName==""){
					for(Map.Entry<String, String> entry:params.entrySet()){
						if(entry.getKey().equals("newsImg")){
							newsImg=entry.getValue();
						}
					}
					fileName=newsImg;
				}
				if(fileName.subSequence(0, 7).equals("picture")){
					path1=fileName;
				}else{
					path1="picture/"+fileName;
				}
				News n = new News();
			   n.setNewsId(newsId1);
			   n.setTitle(title);
			   n.setSubjectId(subjectId1);
			   n.setContent(content);
			   n.setSmallContent(smallContent);
			   n.setNewsImg(path1);
				int result=nsi.update(n);
				if(result>0){
					out.print("<script>alert('�޸ĳɹ�');location.href='newsIndex.jsp';</script>");
				}else{
					out.print("<script>alert('�޸�ʧ��');location.href='newsIndex.jsp';</script>");
				}
		}
		out.flush();
		out.close();
	}

}
