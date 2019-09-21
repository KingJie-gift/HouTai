package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Admin;
import service.impl.AdminServiceImpl;

public class AdminServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		AdminServiceImpl asi = new AdminServiceImpl();
		String op = request.getParameter("op");
		if (op.equals("check")) {
			Admin a = new Admin();
			String name=request.getParameter("name");
			a.setName(name);
			a.setPwd(request.getParameter("password"));
			int count = asi.check(a);
			if (count > 0) {
				request.getSession().setAttribute("name", name);
				request.getRequestDispatcher("index.jsp").forward(request,
						response);
			} else {
				out.print("<script>alert('用户名或密码输入错误！');history.go(-1);</script>");
			}
		}

		out.flush();
		out.close();
	}

}
