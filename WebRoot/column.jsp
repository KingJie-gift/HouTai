	<%@page import="entity.Subject"%>
<%@page import="entity.Page"%>
<%@page import="entity.Teacher"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
<title>网站信息</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.12.4.js"></script>
<script src="js/pintuer.js"></script>
<script type="text/javascript" src="ueditor.config.js"></script>
<script type="text/javascript" src="ueditor.all.js"></script>
</head>

<body>
	<style type="text/css">
#bigImg {
	display: none;
	position: fixed;
	left: 260px;
	right: 1700px;
	top: 100px;
	z-index: 999;
}
</style>


	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder"> 内容列表</strong>
		</div>

		<div class="padding border-bottom">
			<form action="TeacherServlet?op=page" method="post">
				<label id="teacher">教师名称：</label><input type="text"
					name="teacherName" id="a" /> <label id="h2">所属课程：</label> <select
					name="subjectIds" id="h1" id="subjectId">
					<option value="-1">请选择</option>
					<c:forEach items="${su}" var="s">
						<option value="${s.subjectId}">${s.subjectName}</option>
					</c:forEach>
				</select> <input type="submit" value="查询"
					class="button bg-main icon-check-square-o" id="e" />
			</form>
			<style>
#teacher {
	font-size: 17.5px;
	right: -50px;
	position: relative;
	width: 200px;
	height: 35px;
}

#a {
	font-size: 17.5px;
	right: -60px;
	position: relative;
	width: 200px;
	height: 35px;
}

#h1 {
	font-size: 17.5px;
	right: -100px;
	position: relative;
	width: 200px;
	height: 35px;
}

#h2 {
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
		</div>

		<table class="table table-hover text-center" id="hh">
			<tr>
				<th>教师名称</th>
				<th>所教课程</th>
				<th>教师照片</th>
				<th>教师短介</th>
				<th width="250">操作</th>
			</tr>
			<%
				Page pg = (Page) request.getAttribute("pg");
				if (pg == null) {
					request.getRequestDispatcher("TeacherServlet?op=page").forward(
							request, response);
					return;
				}
			%>

			<c:forEach items="${pg.list}" var="t">
				<tr>
					<td>${t.teacherName}</td>
					<td>${t.subject.subjectName}</td>
					<td><img class="big" src="${t.teacherImg}" height="90px"
						width="60px" id="image" /></td>
					<td><strong>${t.shortIntroduce}</strong></td>
					<td>
						<div class="button-group">
							<a type="button" class="button border-main"
								href="TeacherServlet?op=toupdate&teacherId=${t.teacherId}"><span
								class="icon-edit"></span>修改</a> <a class="button border-red"
								href="javascript:void(0)" onclick="del(${t.teacherId})"
								id="isDelete" name="isDelete"><span class="icon-trash-o"></span>
								删除</a>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div id="bigImg">
		<img src="test.jpg" />
	</div>

	</table>
	</div>
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
	<script>
		function del(id) {
			if (confirm("您确定要删除吗?")) {
				location.href = "TeacherServlet?op=deleteUpdate&teacherId="
						+ id;
			}
		}
	</script>

	<div class="panel admin-panel margin-top">
		<div class="panel-head" id="add">
			<strong>
				<tr>
					<td colspan="8"><div class="pagelist">
							<a href="TeacherServlet?op=page&index=1" class="current">首页</a> <a
								href="TeacherServlet?op=page&index=${pg.pageIndex-1}">上一页</a> <a
								href="TeacherServlet?op=page&index=${pg.pageIndex+1}">下一页</a> <a
								href="TeacherServlet?op=page&index=${pg.allPage}">尾页</a>

						</div></td>
				</tr> <span class="icon-pencil-square-o"></span>增加内容
			</strong>
		</div>
		<div class="body-content">

			<form method="post" enctype="multipart/form-data" id="form" class="form-x"
				id="hhh" action="TeacherServlet?op=add">
				<input type="hidden" name="id" value="" />
				<div class="form-group">
					<div class="label">
						<label>教师姓名：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" name="teacherName" id="teacherName" value=""
							/><span></span>
						<div class="tips"></div>
					</div>
				</div>
				<div class="form-group">
					<div class="label">
						<label>教师头像：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50"
							name="name" value="" /> <input name="img" id="images" type="file"
							onchange="imag.value=this.value" class="file" /> <input
							type="button" class="button bg-blue margin-left" id="image1"
							value="+ 浏览上传" style="float:left;">
					</div>
				</div>
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
</style>

				<a href="TeacherServlet?op=toadd&teacherId=${t.teacherId}" /></a>
				<div id="hhh">
					<div class="form-group">
						<div class="label">
							<label>所属课程：</label>
						</div>
						<div class="field">
							<select name="subjectId" class="input w50" id="subjectId">
								<option value="-1">请选择分类</option>
								<c:forEach items="${slist}" var="su">
									<option value="${su.subjectId}">${su.subjectName}</option>
								</c:forEach>
							</select>

							<div class="tips"></div>
						</div>
					</div>

					<div class="form-group">
						<div class="label">
							<label>教师简介：</label>
						</div>
						<div class="field">
							<input type="text" class="input" name="shortIntroduce" id="shortIntroduce" value="" />
						</div>
					</div>

					<div class="form-group">
						<div class="label">
							<label>教师介绍：</label>
						</div>
						<div class="field">
							<textarea type="text" class="input" name="introduce"
								id="container" style="height:100px;"></textarea>
						</div>
					</div>

					<div class="form-group">
						<div class="label">
							<label></label>
						</div>
						<div class="field">
							<button class="button bg-main icon-check-square-o" type="submit">
								提交</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
<script src="jquery-1.12.4.js"></script>
<script>
	$(function() {
		$("#form").submit(function() {
			var teacherName = $("#teacherName").val();
			if (teacherName == "") {
				alert("教师名字不能为空");
				return false;
			}
				var images = $("#images").val();
			if (images == "") {
				alert("图片不能为空");
				return false;
			}
			
			var subjectId = $("#subjectId").val();
			if (subjectId == -1) {
				alert("请选择");
				return false;
			}
			var introduce = $("#introduce").val();
			if (introduce == "") {
				alert("介绍不能为空");
				return false;
			}
			
			var shortIntroduce = $("#shortIntroduce").val();
			if (shortIntroduce == "") {
				alert("简介不能为空");
				return false;
			}
			return true;
		});
	});
</script>

<script>
	var ue = UE.getEditor('container');
</script>
