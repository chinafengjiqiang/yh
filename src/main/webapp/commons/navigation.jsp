<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div class="sidebar">
			<!--- Sidebar navigation -->
			<ul id="nav">
				<li><a href="#" class="has_sub" onclick="menu('${contextPath}/manage/user/tearch')"><i
						class="icon-table"></i>教师管理</a></li>


			</ul>
		</div>
<script>
	function menu(url) {
		$("#mainbar").load(url);
	}

</script>