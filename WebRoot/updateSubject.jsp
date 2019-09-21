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
</head>
<body>
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong><span class="icon-pencil-square-o"></span> 单页信息</strong>
		</div>
		<div class="body-content">

			<form method="post" class="form-x" action="SubjectServlet?op=update">
				<input type="hidden" name="id" value="${po.subjectId}">

				<div class="form-group">
					<div class="label">
						<label>课程名称：</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" name="subjectName"
							value="${po.subjectName}" />
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label>课程标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="subjectTitle"
							value="${po.subjectTitle}" />
						<div class="tips"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>内容：</label>
					</div>
					<div class="field">
								<textarea id="container" name="subjectContent">${po.subjectContent}</textarea>
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
<script type="text/javascript" src="ueditor.config.js">
</script>
<script type="text/javascript" src="ueditor.all.js"></script>
<script type="text/javascript">
	var ue = UE.getEditor('container');
</script>