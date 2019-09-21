<%@page import="entity.Page"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="zh-cn">
<head>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="js/pintuer.js"></script>
</head>
<body>

	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder"> 内容列表</strong>
		</div>

		<%
			Page pg = (Page) request.getAttribute("pg");
			if (pg == null) {
				request.getRequestDispatcher("ClassimgServlet?op=page")
						.forward(request, response);
				return;
			}
		%>

		<table class="table table-hover text-center" width="1500px">
			<tr>
				<th>图片</th>
				<th>文本内容</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${pg.list}" var="c">
				<tr>
					<td><img src="${c.imgSrc}" alt="" width="150px" height="80px"
						class="big" id="image" /></td>
					<td>${c.content}</td>

					<td><div class="button-group">
							<a class="button border-main" href="#" onclick="up(${c.imgId})"><span
								class="icon-edit"></span> 修改</a> <a class="button border-red"
								href="javascript:void(0)" onclick="del(${c.imgId})"><span
								class="icon-trash-o"></span> 删除</a>
						</div></td>
				</tr>
			</c:forEach>
			<div id="bigImg">
				<img src="test.jpg" width="600px" height="400px" />
			</div>
			<style>
#bigImg {
	display: none;
	position: fixed;
	left: 350px;
	top: 100px;
	z-index: 99;
}
</style>
			<tr>
				<td colspan="8"><div class="pagelist">
						<a href="ClassimgServlet?op=page&index=1">首页</a> <a
							href="ClassimgServlet?op=page&index=${pg.pageIndex-1}">上一页</a> <a
							href="ClassimgServlet?op=page&index=${pg.pageIndex+1}">下一页</a><a
							href="ClassimgServlet?op=page&index=${pg.allPage}">尾页</a>
					</div></td>
			</tr>
		</table>
	</div>
	<div class="panel admin-panel margin-top" id="add">
		<div class="panel-head">
			<strong><span class="icon-pencil-square-o"></span> 增加内容</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" enctype="multipart/form-data"
				action="ClassimgServlet?op=add">

				<div class="form-group">
					<div class="label">
						<label>上传图片：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50"
							name="name" value="" /> <input name="img" id="url1" type="file"
							onchange="imag.value=this.value" class="file" /> <input
							type="button" class="button bg-blue margin-left" id="image1"
							value="+ 浏览上传" style="float:left;">
					</div>
				</div>
				<div class="form-group" id="z">
					<div class="label">
						<label>文本内容：</label>
					</div>
					<div class="field" id="z">
						<input type="text" class="input w50" name="content" id="content" />
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

#z {
	
}
</style>

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<input type="submit" value="添加"
							class="button bg-main icon-check-square-o">
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
<script src="jquery-1.12.4.js"></script>
	<script type="text/javascript">
		function del(imgId) {
			if (confirm("您确定要删除吗?")) {
				location.href = "ClassimgServlet?op=delete&imgId=" + imgId;
			}
		}
		function up(imgId) {
			var content = prompt("请输入被修改的文本内容!");
			if (content != null) {
				location.href = "ClassimgServlet?op=update&imgId=" + imgId
						+ "&content=" + content;
			} else {
				alert("取消修改!");
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
		$(function(){
		$("form").submit(function(){
		var imag=$("#imag").val();
		
		var content=$("#content").val();
		if(imag==""){
		alert("图片不能为空!");
		return false;
		}
		if(content==""){
		alert("内容不能为空!");
		return false;
		}
		return true;
		})
		})
	</script>