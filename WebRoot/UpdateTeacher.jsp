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
			<strong><span class="icon-pencil-square-o"></span>修改教师信息</strong>
		</div>
		<div class="body-content">

			<form method="post" class="form-x" action="TeacherServlet?op=update" enctype="multipart/form-data">
				<input type="hidden" name="teacherId" value="${t.teacherId}" />
				<div class="form-group">
					<div class="label">
						<label>教师姓名:</label>
					</div>
					<div class="field">
						<input type="text" class="input w50" name="teacherName" id="teacherName"
						data-validate="required:请输入教师姓名" 	value="${t.teacherName}">
						<div class="tips" id="teacherName_prompt"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>所属课程：</label>
					</div>
					<div class="field">
						<select name="subjectId" class="input w50" id="subjectId" 	data-validate="required:请输入所属课程"  >
							<option value="-1">请选择</option>
							<c:forEach items="${slist}" var="s">
								<c:if test="${t.subjectId==s.subjectId}">
									<option value="${s.subjectId}" selected>${s.subjectName}</option>
								</c:if>
								<c:if test="${t.subjectId!=s.subjectId}">
									<option value="${s.subjectId}">${s.subjectName}</option>
								</c:if>
							</c:forEach>
						</select>
						<div class="tips" id="checksubject_prompt"></div>
					</div>
				</div>

				<div class="form-group" >
					<div class="label">
						<label>教师短介：</label>
					</div>
					<div class="field" id="shortIntroduce" >
						<input type="text" name="shortIntroduce" id="shortIntroduce" class="input" 
						data-validate="required:请输入师资短介"		value="${t.shortIntroduce}">
						<div class="tips" id="shortIntroducet_prompt"></div>
					</div>
				</div>

				<div class="form-group">
					<div class="label">
						<label>教师介绍：</label>
					</div>
					<input type="hidden" name="teacherImg" value="${t.teacherImg}" >
					<div class="field">
						<textarea type="text" class="input" name="introduce" id="container">${t.introduce}</textarea>
						<div class="tips" ></div>
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
<script>
	var ue = UE.getEditor('container');
</script> 
<script type="text/javascript"src="jquery-1.12.4.js"></script>
<!-- <script>
	$("#teacherName").blur(checkName);
	function checkName(){
		var teacherName=$("#teacherName").val();
		if(teacherName==""){
			$("#teacherName_prompt").html("名字不能为空");
			return false;
		}
		return true;
	}
	$("#teacherName").focus(function(){
		$("#teacherName_prompt").html("");
	});
	
	$("#subjectId").blur(checksubject);
	function checksubject(){
		var subjectId=$("#subjectId").val();
		if(subjectId==-1){
			$("#checksubject_prompt").html("请选择老师类型");
			return false;
		}
		return true;
	}
	$("#subjectId").focus(function(){
		$("#checksubject_prompt").html("");
	});
	
	$("#shortIntroduce").blur(checkshortIntroduce);
	function checkshortIntroduce(){
		var shortIntroduce=$("#shortIntroduce").val();
		if(shortIntroduce==""){
			$("#shortIntroduce_prompt").html("请选择老师类型");
			return false;
		}
		return true;
	}
	$("#shortIntroduce").focus(function(){
		$("#shortIntroduce_prompt").html("");
	});
</script> -->