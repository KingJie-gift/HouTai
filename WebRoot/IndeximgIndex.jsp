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
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder"> 内容列表</strong>
		</div>
		<%
			Page pg = (Page) request.getAttribute("pg");
			if (pg == null) {
				request.getRequestDispatcher("IndeximgServlet?op=page")
						.forward(request, response);
				return;
			}
		%>

		<table class="table table-hover text-center">
			<tr>
				<th colspan="2">图片展示</th>
			</tr>

			<c:forEach items="${pg.list}" var="i">
				<form action="control.jsp?imgId=${i.imgId}" method="post"
					enctype="multipart/form-data">
					<td style="width: 50%;float: left"><img class="img" id="img"
						src="${i.imgSrc}" width="600" height="250" /> <img id="err"
						onclick="del(${i.imgId})"
						style="position: relative; top: -220px;left: -40px;"
						src="images/aaa.jpg" width="20px" height="20px"> <a href="">替换
							<input type="file" name="file" id="file" class="file">
					</a> <input type="submit" value="确定"
						class="button bg-main icon-check-square-o" id="${i.imgId}"
						onclick="ck(${i.imgId})"></td>

				</form>
			</c:forEach>



			<tr>
				<td colspan="8"><div class="pagelist">
						<a href="IndeximgServlet?op=page&index=1">首页</a> <a
							href="IndeximgServlet?op=page&index=${pg.pageIndex-1}">上一页</a> <a
							href="IndeximgServlet?op=page&index=${pg.pageIndex+1}">下一页</a><a
							href="IndeximgServlet?op=page&index=${pg.allPage}">尾页</a>
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
				action="IndeximgServlet?op=add">


				<div class="form-group">
					<div class="label">
						<label>图片上传：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50"
							name="name" value="" /> <input name="img" id="url1" type="file"
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

				<div class="form-group">
					<div class="label">
						<label></label>
					</div>
					<div class="field">
						<input type="submit" value="提交"
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
			location.href = "IndeximgServlet?op=delete&imgId=" + imgId;
		}
	}
	
	function ck(e) {
		$("form").submit(function() {
			if ($("#" + e).prev().find(":input").val() == "") {
				alert("请选择图片");
				location.reload();
				return false;
			}
			return true;
		})
	}
</script>