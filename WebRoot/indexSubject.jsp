<%@page import="entity.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html lang="zh-cn">
<head>
<title></title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.12.4.js"></script>
<script src="js/pintuer.js"></script>
</head>
<body>
	<%
		Page pg = (Page) request.getAttribute("pg");
		if (pg == null) {
			request.getRequestDispatcher("SubjectServlet?op=page").forward(
					request, response);
			return;
		}
	%>

	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 内容列表</strong>
			</div>
			<div class="padding border-bottom">
				<button type="button" class="button border-yellow"
					onclick="window.location.href='addSubject.jsp'">
					<span class="icon-plus-square-o"></span> 添加内容
				</button>

				<form action="SubjectServlet" method="post">
					<label id="z">课程名称：</label><input type="text" name="subjectName"
						id="w" /> <input class="button bg-main icon-check-square-o"
						type="submit" value="查询" id="e" />
				</form>
				<style>
#z {
	font-size: 17.5px;
	right: -50px;
	position: relative;
	width: 200px;
	height: 35px;
}

#w {
	font-size: 17.5px;
	right: -60px;
	position: relative;
	width: 200px;
	height: 35px;
}

#e {
	font-size: 17.5px;
	right: -160px;
	position: relative;
	width: 100px;
	height: 40px;
}
</style>
			</div>
			<table class="table table-hover text-center">

				<tr>
					<th style="width: 189px; ">课程名称</th>
					<th style="width: 303px; ">标题</th>
					<th>课程描述</th>
					<th style="width: 187px; ">操作</th>
				</tr>

				<c:forEach items="${pg.list}" var="p">
					<tr>
						<td>${p.subjectName}</td>
						<td>${p.subjectTitle}</td>
						<c:if test="${ fn:trim(fn:length(p.subjectContent)) >105 }">
							<td>${ fn:trim(fn:substring(p.subjectContent,0,105)) }</td>
						</c:if>
						<c:if test="${ fn:trim(fn:length(p.subjectContent)) <=105 }">
							<td>${ fn:trim(p.subjectContent) }</td>
						</c:if>

						<td>
							<div class="button-group">
								<a class="button border-main"
									href="SubjectServlet?op=toupdate&id=${p.subjectId}"> <span
									class="icon-edit"></span>修改
								</a> <a class="button border-red" href='#'
									onclick="del(${p.subjectId})"> <span class="icon-trash-o"></span>删除
								</a>
							</div>
						</td>
					</tr>
				</c:forEach>

				<tr>
					<td colspan="8"><div class="pagelist">
							<a href="SubjectServlet?op=page&index=1">首页</a> <a
								href="SubjectServlet?op=page&index=${pg.pageIndex-1}">上一页</a> <a
								href="SubjectServlet?op=page&index=${pg.pageIndex+1}">下一页</a> <a
								href="SubjectServlet?op=page&index=${pg.allPage}">尾页</a>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
	<script src="jquery-1.12.4.js"></script>
	<script>
		function del(id) {
			if (confirm("确定要删除吗")) {
				location.href = "SubjectServlet?op=delete&id=" + id;
			}
		}
	</script>

</body>
</html>