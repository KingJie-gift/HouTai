<%@page import="entity.Communicate"%>
<%@page import="entity.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title></title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.12.4.js"></script>
<script src="js/pintuer.js"></script>
</head>
<%
	Page pg = (Page) request.getAttribute("pg");
	if (pg == null) {
		request.getRequestDispatcher("CommunicateServlet?op=page")
				.forward(request, response);
		return;
	}
%>

<body>

	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder"> 内容列表</strong>
		</div>
		<div class="padding border-bottom">
			<button type="button" class="button border-yellow"
				onclick="window.location.href='CommunicateServlet?op=toadd'">
				<span class="icon-plus-square-o"></span> 添加内容
			</button>
				<form style="display: inline;" action="CommunicateServlet?op=page" method="post">
					<label id="z">标题：</label><input type="text" name="title" id="w" />
					<label id="q">类型：</label><select name="subjectId" id="s">
						<option value="-1">请选择</option>
						<c:forEach items="${list }" var="s">
							<option value="${s.subjectId }">${s.subjectName }</option>
						</c:forEach>
					</select> <input type="submit" value="查询"
						class="button bg-main icon-check-square-o" id="e" />
					<style type="text/css">
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

#s {
	font-size: 17.5px;
	right: -100px;
	position: relative;
	width: 200px;
	height: 35px;
}

#q {
	font-size: 17.5px;
	right: -100px;
	position: relative;
	width: 200px;
	height: 35px;
}

#e {
	font-size: 17.5px;
	right: -460px;
	position: relative;
	width: 100px;
	height: 40px;
}
</style>
				</form>
		</div>
		<table class="table table-hover text-center">

			<tr>
				<th>日期</th>
				<th>标题</th>
				<th>类型</th>
				<th>图片</th>
				<th>操作</th>
			</tr>

			<c:forEach items="${pg.list}" var="p">
				<tr>
					<td><strong>${p.date }</strong></td>
					<td><strong>${p.title}</strong></td>
					<td><strong>${p.subject.subjectName}</strong></td>
					<td><img src="${p.img}" width="150" height="90" /></td>
					<td>

						<div class="button-group">
							<a class="button border-main"
								href="CommunicateServlet?op=toupdate&id=${p.id}"> <span
								class="icon-edit"></span>修改
							</a> <a class="button border-red" href='#' onclick="del(${p.id})">
								<span class="icon-trash-o"></span>删除
							</a>
						</div>
				</tr>
			</c:forEach>


			<tr>
				<td colspan="8">
					<div class="pagelist">
						<a href="CommunicateServlet?op=page&index=1&title=${title}">首页</a>
						<a
							href="CommunicateServlet?op=page&index=${pg.pageIndex-1}&title=${title}">上一页</a>
						<a
							href="CommunicateServlet?op=page&index=${pg.pageIndex+1}&title=${title}">下一页</a>
						<a
							href="CommunicateServlet?op=page&index=${pg.allPage}&title=${title}">尾页</a>
					</div>
				</td>
			</tr>

		</table>
	</div>
</body>
</html>
<script>
	function del(id) {
		if (confirm("确定删除吗")) {
			location.href = "CommunicateServlet?op=delete&id=" + id;
		}
	}
</script>
