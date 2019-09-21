<%@page import="entity.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			request.getRequestDispatcher("ExperienceServlet?op=page")
					.forward(request, response);
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
					onclick="window.location.href='ExperienceServlet?op=toadd&id=${t.id}'">
					<span class="icon-plus-square-o"></span> 添加内容
				</button>
				<form method="post" action="ExperienceServlet?op=page">
					<label id="h1">所属课程：</label> <select id="h" name="subjectId">
						<option value="-1">请选择</option>
						<c:forEach items="${slist}" var="s">
							<option value="${s.subjectId}">${s.subjectName}</option>
						</c:forEach>
					</select><input type="submit" value="查询"
						class="button bg-main icon-check-square-o" id="e" />
				</form>
			</div>
			<style type="text/css">
#h {
	font-size: 17.5px;
	right: -40px;
	position: relative;
	width: 200px;
	height: 35px;
}

#h1 {
	font-size: 17.5px;
	right: -40px;
	position: relative;
	width: 200px;
	height: 35px;
}

#e {
	font-size: 17.5px;
	right: -60px;
	position: relative;
	width: 100px;
	height: 40px;
}
</style>
		</div>
		<table class="table table-hover text-center">

			<tr>
				<th>日期</th>
				<th>标题</th>
				<th>图片</th>
				<th>科目类型</th>
				<th>操作</th>
			</tr>

			<c:forEach items="${pg.list}" var="t">
				<tr>
					<td>${t.date}</td>
					<td>${t.title}</td>
					<td><img class="big" src="${t.img}" heigth="300px"
						width="100px" onclick="show()" id="img" /></td>
					<td>${t.subject.subjectName}</td>
					<td>
						<div class="button-group">
							<a class="button border-main"
								href="ExperienceServlet?op=toupdate&id=${t.id}"> <span
								class="icon-edit"></span>修改
							</a> <a class="button border-red" href='#' onclick="del(${t.id})">
								<span class="icon-trash-o"></span>删除
							</a>
						</div>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="8"><div class="pagelist">
						<a href="ExperienceServlet?op=page&index=1">首页</a> <a
							href="ExperienceServlet?op=page&index=${pg.pageIndex-1}">上一页</a>
						<a href="ExperienceServlet?op=page&index=${pg.pageIndex+1}">下一页</a>
						<a href="ExperienceServlet?op=page&index=${pg.allPage}">尾页</a>
					</div></td>
			</tr>
		</table>
		</div>
		<div id="bigImg">
			<img src="test.jpg" height="300px" width="450px"/>
		</div>
		<style>
#bigImg {
	display: none;
	position: fixed;
	left: 300px;
	top: 150px;
	z-index: 999
}
</style>
		<script>
			$(".big").on("click", function big() {
				var src = $(this).attr("src");
				$("#bigImg").css("display", "block");
				$("#bigImg img").attr("src", src);
			});

			$("#bigImg").on("click", function close() {
				$("#bigImg").css("display", "none");
			});
		</script>
	</form>
	<script src="jquery-1.12.4.js"></script>

	<script>
		function del(id) {
			if (confirm("确定要删除吗")) {
				location.href = "ExperienceServlet?op=delete&id=" + id;
			}
		}
	</script>
	<script>
		
	</script>


</body>
</html>