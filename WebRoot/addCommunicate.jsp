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
<body>
	<c:if test="${empty list}">
		<script>
			loction.href = "CommunicateServlet?op=toadd";
		</script>
	</c:if>
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong><span class="icon-pencil-square-o"></span> 单页信息</strong>
		</div>
		<div class="body-content">
			<form method="post" class="form-x" action="CommunicateServlet?op=add"
				enctype="multipart/form-data">
				
				
				<div class="form-group">
					<div class="label">
						<label>标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" name="title" value="" required="" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>内容：</label>
					</div>
					<div class="field">
						
							<textarea id="container" name="subjectContent"> </textarea>
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>短介：</label>
					</div>
					<div class="field">
						<input class="input" name="smallContent" required="" />
						<div class="tips"></div>
					</div>
				</div>
				
				 
				<div class="form-group">
					<div class="label">
						<label>所属课程：</label>
					</div>
					<div class="field">
						<select name="subject" id="subject" class="input w50">
							<option value="-1">请选择分类</option>
							<c:forEach items="${ list }" var="p">
								<option value="${ p.subjectId }">${ p.subjectName }</option>
							</c:forEach>
						</select>
						<div class="tips"></div>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="label">
						<label>研习图片：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50" name="name" value="" /> 
						<input name="img" id="url1" type="file" onchange="imag.value=this.value" class="file" /> 
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
						<input type="submit" value="提交"
							class="button bg-main icon-check-square-o" />
					</div>
				</div>
				
				
			</form>
		</div>
	</div>
</body>
</html>

<script src="jquery-1.12.4.js"></script>

<script type="text/javascript" src="ueditor.config.js">
	
</script>
<script type="text/javascript" src="ueditor.all.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('container');
</script>
<script src="jquery-1.12.4.js"></script>
<script>
	$(function() {
		$("form").submit(function() {
			var title = $("#title").val();
			var content = $("#content").val();
			var smallContent = $("#smallContent").val();
			var subject = $("#subject").val();
			var img = $("#img").val();
			if (title == "") {
				alert("标题不能为空");
				return false;
			}
			if (content == "") {
				alert("内容不能为空");
				return false;
			}
			if (smallContent == "") {
				alert("小内容不能为空");
				return false;
			}
			if (subject == -1) {
				alert("请选择类型");
				return false;
			}
			if (img == "") {
				alert("图片不能为空");
				return false;
			}
			return true;
		})
	})
</script>
