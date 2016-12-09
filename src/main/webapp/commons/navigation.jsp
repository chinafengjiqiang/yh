<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div class="sidebar">
			<!--- Sidebar navigation -->
			<ul id="nav">
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/dept/org')"><i
						class="icon-table"></i>学校管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/dept/dept')"><i
						class="icon-user-md"></i>年级管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/dept/group')"><i
						class="icon-group"></i>分组管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/user/tearch')"><i
						class="icon-user"></i>教师管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/msg')"><i
						class="icon-mobile-phone"></i>教学通知</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/lesson')"><i
						class="icon-book"></i>课程及进度</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/lesson/preLesson')"><i
						class="icon-building"></i>备课管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/user/editPass')"><i
						class="icon-edit"></i>密码修改</a></li>
			</ul>
		</div>
<script>
	function menu(url) {
		$("#mainbar").load(url);
	}

</script>