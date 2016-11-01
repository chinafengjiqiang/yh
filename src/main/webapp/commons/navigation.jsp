<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div class="sidebar">
			<!--- Sidebar navigation -->
			<ul id="nav">
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/dept/org')"><i
						class="icon-table"></i>学校管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/dept/dept')"><i
						class="icon-table"></i>年级管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/dept/group')"><i
						class="icon-table"></i>分组管理</a></li>
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/user/tearch')"><i
						class="icon-table"></i>教师管理</a></li>


			</ul>
		</div>
<script>
	function menu(url) {
		$("#mainbar").load(url);
	}

</script>