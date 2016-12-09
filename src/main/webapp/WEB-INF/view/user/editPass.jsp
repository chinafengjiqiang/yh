<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div class="mainbar">
    <div class="matter">
        <div class="container">
            <!-- Content -->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget-content panel-body" style="width: 80%;">
                        <div class="manage-div">
                            <div class="modal-header manage-header">
                                <h4 class="modal-title">修改密码</h4>
                            </div>
                            <div class="modal-body">

                                <form role="form" name="mangerForm" id="mangerForm">
                                    <div class="form-group">
                                        <span>新密码：</span>
                                        <input type="password" class="form-control" id="NEW_PASS" name="NEW_PASS" value="" placeholder="请输入新密码">
                                    </div>
                                    <div class="form-group">
                                        <span>确认密码：</span>
                                        <input type="password" class="form-control" id="NEW_PASS_AGIN" name="NEW_PASS_AGIN" value="" placeholder="请再次输入新密码">
                                    </div>
                                    <button type="submit" class="btn btn-success">提交</button>
                                    <button class="btn btn-cancel" type="button" onclick="mangerClean()">重置</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $().ready(function(){
        $("#mangerForm").validate({
            rules : {
                NEW_PASS:{
                    required: true,
                    rangelength:[6,16]
                },
                NEW_PASS_AGIN: {
                    required: true,
                    rangelength:[6,16],
                    equalTo: "#NEW_PASS"
                },
            },
            messages : {
                NEW_PASS : {
                    required: "请输入新密码",
                    rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字母或数字")
                },
                NEW_PASS_AGIN: {
                    required: "请再次输入新密码",
                    rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字母或数字"),
                    equalTo: "两次密码输入不一致"
                },
            },
            submitHandler:function(form){
                $.post(
                        fq.contextPath+"/manage/user/doEditPass",
                        $("#mangerForm").serialize(),
                        function(res){
                            if(res == 0){
                                Alert("修改密码成功！");
                            }else if(res == 4003){
                                Alert("只能输入长度在6到16之间的字母或数字");
                            }else if(res == 4002){
                                Alert("用户不存在");
                            }
                            else{
                                Alert("修改密码失败！");
                            }
                        },"JSON"
                );

            }
        });
    });


    function mangerClean(){
        $("#mangerForm input").val("");
        $("#mangerForm label").hide();
    }

</script>