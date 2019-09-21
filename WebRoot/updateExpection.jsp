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

			<form method="post" class="form-x"
				action="ExperienceServlet?op=update" enctype="multipart/form-data">
				<td><input type="hidden" name="id" value="${ei.id}"></td>
				<div class="form-group">
					<div class="label">
						<label>标题：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="title" value="${ei.title}" />
						<input type="hidden" class="input" name="img" value="${ei.img}" />
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label>所属课程：</label>
					</div>
					<div class="field">
						<select name="subjectId" class="input w50">
							<option value="-1">请选择</option>
							<c:forEach items="${slist}" var="s">
								<c:if test="${ei.subjectId==s.subjectId}">
									<option value="${s.subjectId}" selected>${s.subjectName}</option>
								</c:if>
								<c:if test="${ei.subjectId!=s.subjectId}">
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
						<%-- <input type="text" class="input" name="content"
							value="${ei.content}" /> --%>
						<textarea type="text" class="input" name="content" id="container">${ei.content}</textarea>
						<div class="tips"></div>
					</div>
				</div>


				<div class="form-group">
					<div class="label">
						<label>短介：</label>
					</div>
					<div class="field">
						<input type="text" class="input" name="smallContent"
							value="${ei.smallContent}" />
						<div class="tips"></div>
					</div>
				</div>
				<input type="hidden" name="img" value="${ei.img}" />
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
<script>
	var ue = UE.getEditor('container');
</script>
