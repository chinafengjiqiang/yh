<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false"%>
    <%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>错误提示</title>
<style type="text/css">
.error{margin: 0 auto;width: 639px;margin-top: 8%;position: relative;}
.tip{position: absolute;bottom: 104px;left: 176px;font-size: 30px;}
body{background-color: #FDFDFD}
</style>
</head>
<body>
	<div class="error">
		<img  src="${pageContext.request.contextPath}/images/error.jpg">
		<p class="tip">
		404你懂得！
		</p>
	</div>
</body>
</html>