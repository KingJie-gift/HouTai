<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title></title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.12.4.js"></script>
<script src="js/pintuer.js"></script>
<script type="text/javascript" src="ueditor.config.js"></script>
<script type="text/javascript" src="ueditor.all.js"></script>
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong><span class="icon-pencil-square-o"></span> 单页信息</strong>
		</div>
		<div class="body-content">

			<form method="post" class="form-x" action="ExperienceServlet?op=add"
				enctype="multipart/form-data">

				<div class="form-group">
					<div class="label">
						<label>标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="title" id="title" />
						<div class="tips" id=""></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label>所属课程：</label>
					</div>
					<div class="field" >
						<select name="subjectId" id="subject" class="input w50"  data-validate="required:请输入所属课程">
							<option value="-1">请选择分类</option>
							<c:forEach items="${ slist }" var="su">
								<option value="${ su.subjectId }">${ su.subjectName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>内容：</label>
					</div>
					<div class="field">
						<!-- <input type="text" class="input" name="content" id="content" /> -->
						<textarea type="text" class="input" name="content"
							id="container"  style="height:100px;"></textarea>
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label>短介：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="smallContent"
							id="smallContent" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>心得图片：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50" name="name" value="" /> 
						<input name="images" id="images" type="file" onchange="imag.value=this.value" class="file" /> 
						<input type="button" class="button bg-blue margin-left" id="image1" value="+ 浏览上传" style="float:left;">
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
						<button class="button bg-main icon-check-square-o" type="submit">
							提交</button>
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
		$("form").submit(function() {
			var title = $("#title").val();
			if (title == "") {
				alert("标题不能为空！");
				return false;
			}
			var subject = $("#subject").val();
			if (subject == -1) {
				alert("请选择类型！");
				return false;
			}
			/* var container = $("#container").val();
			if (container == "") {
				alert("内容不能为空");
				return false;
			} */
			var smallContent = $("#smallContent").val();
			if (smallContent == "") {
				alert("短介不能为空！");
				return false;
			}
			var images = $("#images").val();
			if (images == "") {
				alert("图片不能为空！");
				return false;
			}
			return true;
		});

	});
</script>

<script>
	var ue = UE.getEditor('container');
</script>
