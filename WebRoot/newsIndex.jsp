<%@page import="entity.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh-cn">
<head>
<title></title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>
</head>
<body>

	<form method="post" action="" id="listform">
		<div class="panel admin-panel">
			<div class="panel-head">
				<strong class="icon-reorder"> 内容列表</strong>
			</div>
			<%
				Page pg = (Page) request.getAttribute("pg");
				if (pg == null) {
					request.getRequestDispatcher("NewsServlet?op=page").forward(
							request, response);
					return;
				}
			%>
			<div class="padding border-bottom">
				<button type="button" class="button border-yellow"
					onclick="window.location.href='NewsServlet?op=toadd'">
					<span class="icon-plus-square-o"></span> 添加内容
				</button>
				<form style="display: inline;" action="NewsServlet?op=page"
					method="post">
					<lable id="z">新闻标题：</lable>
					<input type="text" name="title" id="w"> <label id="q">所属课程类型：</label>
					<select name="subjectId" id="s">
						<option value="-1">请选择分类</option>
						<c:forEach items="${su}" var="s">
							<option value="${ s.subjectId }">${ s.subjectName }</option>
						</c:forEach>
					</select> <input type="submit" value="查询"
						class="button bg-main icon-check-square-o" id="e" />
				</form>
				<style type="text/css">
input {
	border: 1px solid green;
}

#image1 {
	margin-top: 2px;
	border: none;
}

.file {
	background: #008000;
	position: relative;
	right: 100px;
	width: 100px;
	height: 40px;
	filter: alpha(opacity = 0);
	-moz-opacity: 0;
	opacity: 0;
}

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
	right: -360px;
	position: relative;
	width: 100px;
	height: 40px;
}
</style>
			</div>
			<table class="table table-hover text-center">
				<tr>
					<th>发布日期</th>
					<th>标题</th>
					<th>图片</th>
					<th>类型</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${pg.list}" var="n">
					<tr>
						<td>${n.date}</td>
						<td>${n.title}</td>
						<td><img src="${n.newsImg}" alt="" width="150px"
							height="80px" id="image" class="big" /></td>
						<td>${n.subject.subjectName}</td>
						<td><div class="button-group">
								<a class="button border-main"
									href="NewsServlet?op=toupdate&newsId=${n.newsId}"><span
									class="icon-edit"></span> 修改</a> <a class="button border-red"
									href="javascript:void(0)" onclick="del(${n.newsId})"><span
									class="icon-trash-o"></span> 删除</a>
							</div></td>
					</tr>
				</c:forEach>
				<div id="bigImg">
					<img src="test.jpg" width="450px" height="300px" />
				</div>
				<style>
#bigImg {
	display: none;
	position: fixed;
	left: 370px;
	top: 100px;
}
</style>


				<tr>
					<td colspan="8"><div class="pagelist">
							<a href="NewsServlet?op=page&index=1">首页</a> <a
								href="NewsServlet?op=page&index=${pg.pageIndex-1}">上一页</a> <a
								href="NewsServlet?op=page&index=${pg.pageIndex+1}">下一页</a><a
								href="NewsServlet?op=page&index=${pg.allPage}">尾页</a>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
	<script src="jquery-1.12.4.js"></script>
	<script type="text/javascript">
		//单个删除
		function del(newsId) {
			if (confirm("您确定要删除吗?")) {
				location.href = "NewsServlet?op=delete&newsId=" + newsId;
			}
		}
		$(".big").on("click", function big() {
			var src = $(this).attr("src");
			$("#bigImg").css("display", "block");
			$("#bigImg img").attr("src", src);
		});

		$("#bigImg").on("click", function close() {
			$("#bigImg").css("display", "none");
		});
	</script>
</body>
</html>