<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <!-- Title and other stuffs -->
    <title>即会通</title>
    <!-- Stylesheets -->
    <link href="${staticPath}/style/bootstrap.min.css"
          rel="stylesheet">
    <!-- Font awesome icon -->
    <link rel="stylesheet" href="${staticPath}/style/login.css">
    <script src="${staticPath}/js/jquery-1.12.3.min.js"></script>
    <script src="${staticPath}/js/jquery.validate.min.js"></script>
    <script src="${staticPath}/js/bootstrap.min.js"></script>
    <script src="${staticPath}/js/html5shim.js"></script>
    <script src="${staticPath}/js/custom.js"></script>
    <link rel="shortcut icon" href="${staticPath}/images/favicon.ico" type="image/x-icon" />
    <link rel="icon" href="${staticPath}/images/favicon.ico" type=" image/png" >
</head>
<body class="login-body" onkeydown="BindEnter(event)">

<div class="container">

    <form class="form-signin" action="${contextPath}/system/login" id="loginForm">
        <h2 class="form-signin-heading">育恒教育</h2>
        <div class="login-wrap">
            <label style="color: red;display: none;">错误信息</label>
            <input id="username" name="username" type="text" class="form-control" placeholder="用户名" autofocus>
            <input id="password" name="userpass" type="password" class="form-control" value="" placeholder="密码">
            <label class="checkbox">
                <input id="rembstatus_V" type="checkbox" />
                <input id="rembstatus" name="rembstatus" type="hidden" value="1"/>
                <script type="text/javascript">
                    attachCheckBox(document.getElementById("rembstatus_V"),document.getElementById("rembstatus"));
                </script>
                记住我
            </label>
            <button class="btn btn-lg btn-login btn-block" type="button" id="loginBtnId">登录</button>
        </div>

    </form>

</div>
<script type="text/javascript">

    var validate = $("#loginForm").validate({
        rules: {
            username: "required",
        },
        messages: {
            username: "请输入用户名",
        },

    });

    $(function () {
        $("#loginBtnId").click(function () {
          if(validate.form()){
              var url = fq.contextPath + "/login/verify";
              $.post(url,fq.serializeObject($("#loginForm")),function (result) {
                  if (result == 0) {
                      top.location.href = fq.contextPath + "/manage/main";
                  }else if(result == 4002){
                      alert("用户不存在");
                  }else if(result == 4003){
                      alert("密码错误");
                  }else if(result == 4004){
                      alert("无权登录");
                  }
                  else {
                      alert("登录失败");
                  }
              });
          }
        });
    });

    function BindEnter(obj)
    {
        //使用document.getElementById获取到按钮对象
        var button = document.getElementById('loginBtnId');
        if(obj.keyCode == 13)
        {
            button.click();
            obj.returnValue = false;
        }
    }


</script>
</body>
</html>