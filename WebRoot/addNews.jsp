<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
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
			<strong><span class="icon-pencil-square-o"></span> 单页信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" enctype="multipart/form-data"
				action="NewsServlet?op=add">

				<div class="form-group">
					<div class="label">
						<label>新闻标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="title" value="" id="title" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>新闻照片：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50"
							name="name" value="" /> <input name="images" id="image1"
							type="file" onchange="imag.value=this.value" class="file" /> <input
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
						<label>新闻简介：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="smallContent" value=""
							id="smallContent" />
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>所属课程：</label>
					</div>
					<div class="field">
						<select name="subjectId" class="input w50" id="subjectId">
							<option value="-1">请选择分类</option>
							<c:forEach items="${ slist }" var="s">
								<option value="${ s.subjectId }">${ s.subjectName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>新闻内容：</label>
					</div>
					<div class="field">
						<textarea name="content" id="container"></textarea>
						<div class="tips"></div>
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
			</form>
		</div>
	</div>
</body>
</html>
<script type="text/javascript" src="ueditor.config.js"></script>
<script type="text/javascript" src="ueditor.all.js"></script>
<script src="jquery-1.12.4.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('container');
	$(function() {
		$("form").submit(function() {
			var title = $("#title").val();
			var image1 = $("#image1").val();
			var smallContent = $("#smallContent").val();
			var subjectId = $("#subjectId").val();
			if (title == "") {
				alert("标题不能为空!");
				return false;
			}
			if (image1 == "") {
				alert("图片不能为空!");
				return false;
			}
			if (smallContent == "") {
				alert("简介不能为空!");
				return false;
			}
			if (subjectId == -1) {
				alert("课程不能为空!");
				return false;
			}
			return true;
		})
	})
</script>