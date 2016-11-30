<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <!-- Title and other stuffs -->
    <title>育恒教育</title>

    <!-- Stylesheets -->
    <link href="${staticPath}/style/bootstrap.min.css"
          rel="stylesheet">
    <!-- Font awesome icon -->
    <link rel="stylesheet"
          href="${staticPath}/style/font-awesome.css">
    <!-- Main stylesheet -->
    <link href="${staticPath}/style/style.css"
          rel="stylesheet">

    <link rel="stylesheet"
          href="${staticPath}/style/jquery.dataTables.min.css">

    <link rel="shortcut icon" href="${staticPath}/images/favicon.ico" type="image/x-icon" />
    <link rel="icon" href="${staticPath}/images/favicon.ico" type=" image/png" >
</head>

<body>
<jsp:include page="/commons/header.jsp"></jsp:include>

<!-- Main content starts -->

<div class="content">

    <!-- Sidebar -->
    <jsp:include page="/commons/navigation.jsp"></jsp:include>
    <!-- Sidebar ends -->

    <!-- Main bar -->
    <div id="mainbar">
        <!--异步引入页面-->
        <jsp:include page="/commons/default.jsp"></jsp:include>
    </div>

</div>

<!-- Content ends -->
<jsp:include page="/commons/footer.jsp"></jsp:include>

<!-- JS -->
<script src="${staticPath}/js/jquery-1.12.3.min.js"></script>
<script src="${staticPath}/js/bootstrap.min.js"></script>
<script src="${staticPath}/js/jquery.dataTables.min.js"></script>
<script src="${staticPath}/js/datatables-pack.js"></script>
<script src="${staticPath}/js/html5shim.js"></script>
<script src="${staticPath}/js/jquery.validate.min.js"></script>
<script src="${staticPath}/js/jquery.form.min.js"></script>
<script src="${staticPath}/js/dialog.js"></script>
<script src="${staticPath}/js/custom.js"></script>
<script src="${staticPath}/js/datePicker/WdatePicker.js"></script>
<script type="text/javascript">

</script>
</body>
</html>
