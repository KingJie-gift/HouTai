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

			<form method="post" class="form-x" action="CommunicateServlet?op=update">
				<input type="hidden" name="id" value="${po.id}">
				<div class="form-group">
					<div class="label">
						<label>标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="title" value="${po.title}" />
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label>所属课程：</label>
					</div>
					<div class="field">
						<select name="subject" class="input w50">
							<option value="-1">请选择</option>
							<c:forEach items="${glist}" var="s">
								<c:if test="${po.subjectId==s.subjectId}">
									<option value="${s.subjectId}" selected>${s.subjectName}</option>
								</c:if>
								<c:if test="${po.subjectId!=s.subjectId}">
									<option value="${s.subjectId}">${s.subjectName}</option>
								</c:if>
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
					<textarea id="container" name=content>${po.content}</textarea>
						<div class="tips"></div>
					</div>
				</div>
				
                <div class="form-group">
					<div class="label">
						<label>封面简介：</label>
					</div>
					<div class="field">
					<input id="container" type="text" name="smallContent" class="input"  value="${po.smallContent }" >
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