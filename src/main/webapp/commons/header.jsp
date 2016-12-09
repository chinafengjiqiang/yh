<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="com.yh.model.LoginInfo"%>
<%@ page import="com.yh.utils.WebUtil"%>
<%
	LoginInfo loginInfo = WebUtil.getManagerLoginInfo(request);
	String name = "";
	if(loginInfo != null){
		name = loginInfo.getTruename();
	}
%>
<script type="text/javascript">
	function logout() {
		var deferred = $.Deferred();
		Confirm({
			msg: '确定要退出系统？',
			onOk: function(){
				top.location.href = fq.contextPath+"/login/logout";
			},
			onCancel: function(){
				deferred.reject();
			}
		})
	}
</script>
<header>
	<div class="container">
		<div class="row">
			<!-- Logo section -->
			<div class="col-md-4">
				<!-- Logo. -->
				<div class="logo">
					<h1>
						<a class="logoImg" href="#" style="white-space:nowrap;"><span class="logoText">育恒教育</span></a>
					</h1>
				</div>
				<!-- Logo ends -->
			</div>
			<!-- Button section -->
			<div class="col-md-6">
			</div>
			<!-- Data section -->
			<div class="col-md-1">
				<div class="header-data">
					<!-- revenue data -->
					<div class="hdata">
						<div class="mcol-right">
							<!-- Icon with green background -->
							<label><%=name%></label>
							<label style="margin-left: 20px;cursor: pointer;">
								<img src="../static/images/power_exit.png"  title="注销登出" onclick="logout();">
							</label>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</header>