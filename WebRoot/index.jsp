<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
<title>后台管理中心</title>
<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery-1.12.4.js"></script>
</head>
<%
	String name=(String)session.getAttribute("name");
	if(name==null){
		response.sendRedirect("login.jsp");
	}
 %>
<body style="background-color:#f2f9fd;">
	<div class="header bg-main">
		<div class="logo margin-big-left fadein-top">
			<h1>
				<img src="images/y.jpg" class="radius-circle rotate-hover"
					height="50" alt="" />美婴国际教育
			</h1>
			<span></span>
		</div>
		<div class="head-l">
			<a class="button button-little bg-green" href="http://localhost:8080/Teacher/ShouYei.jsp" target="_blank"><span
				class="icon-home"></span> 前台首页</a> &nbsp;&nbsp; <a
				class="button button-little bg-red" href="login.jsp"><span
				class="icon-power-off"></span> 退出登录</a>
		</div>
	</div>
	<div class="leftnav">
		<div class="leftnav-title">
			<strong><span class="icon-list"></span>菜单列表</strong>
		</div>
		<h2>
			<span class="icon-user"></span>栏目管理
		</h2>
		<ul style="display:block">

			<li><a href="indexExperience.jsp" target="right" class="on"><span
					class="icon-caret-right"></span>学员心得管理</a></li>

			<li><a href="column.jsp" target="right"><span
					class="icon-caret-right"></span>教师管理</a></li>

			<li><a href="indexCommunicate.jsp" target="right"><span
					class="icon-caret-right"></span>研习交流管理</a></li>

			<li><a href="indexSubject.jsp" target="right"><span
					class="icon-caret-right"></span>课程管理</a></li>

			<li><a href="newsIndex.jsp" target="right"><span
					class="icon-caret-right"></span>新闻管理</a></li>

			<li><a href="ClassimgIndex.jsp" target="right"><span
					class="icon-caret-right"></span>研修班照片管理</a></li>

			<li><a href="IndeximgIndex.jsp" target="right"><span
					class="icon-caret-right"></span>首页背景管理</a></li>

		</ul>
	</div>
	<script type="text/javascript">
		$(function() {
			$(".leftnav h2").click(function() {
				$(this).next().slideToggle(200);
				$(this).toggleClass("on");
			})
			$(".leftnav ul li a").click(function() {
				$("#a_leader_txt").text($(this).text());
				$(".leftnav ul li a").removeClass("on");
				$(this).addClass("on");
			})
		});
	</script>

	<div class="admin">
		<iframe scrolling="auto" rameborder="0" src="indexExperience.jsp"
			name="right" width="100%" height="100%"></iframe>
	</div>
	<div style="text-align:center;"></div>
</body>

</html>
