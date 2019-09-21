<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
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
			<form method="post" class="form-x" action="NewsServlet?op=update"
				enctype="multipart/form-data">
				<input type="hidden" name="newsId" value="${nw.newsId}">
				<div class="form-group">
					<div class="label">
						<label>新闻标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="title" value="${nw.title}" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>新闻类型</label>
					</div>
					<div class="field">
						<select name="subjectId" class="input w50">
							<option class="input" value="-1">请选择</option>
							<c:forEach items="${slist}" var="s">
								<c:if test="${nw.subjectId==s.subjectId}">
									<option class="input" value="${s.subjectId}" selected>${s.subjectName}</option>
								</c:if>
								<c:if test="${nw.subjectId!=s.subjectId}">
									<option class="input" value="${s.subjectId}">${s.subjectName}</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>新闻小标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="smallContent"
							value="${nw.smallContent}" />
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>新闻内容：</label>
					</div>
					<div class="field">
						<textarea name="content" id="container">${nw.content}</textarea>
						<input type="hidden" name="newsImg" value="${nw.newsImg}">
						<div class="tips"></div>
					</div>
				</div>
				
				<div class="form-group">
					<div class="label">
						<label>教师头像：</label>
					</div>
					<div class="field">
						<input disabled="disabled" type="text" id="imag" class="input w50" name="name" value="${nw.newsImg}" /> 
						<input name="newsImg" id="url1" type="file" onchange="imag.value=this.value" class="file" /> 
						<input type="button" class="button bg-blue margin-left" id="image1"
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
#z{

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
<script type="text/javascript" src="ueditor.config.js"></script>
<script type="text/javascript" src="ueditor.all.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('container');
</script>