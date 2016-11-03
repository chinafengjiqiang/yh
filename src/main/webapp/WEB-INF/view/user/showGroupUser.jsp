<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="gUsers" role="dialog" tabindex="-1" id="gUsers" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">分组用户</h4>
            </div>
            <div class="modal-body" id="gUserTable">
                <table class="table table-striped table-bordered table-hover" id="userTable" width="100%">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>姓名</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody id="group_body">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $.ajaxSettings.async = false;
    $.ajaxSettings.cache = false;
    function showUserList(gId){
        $.getJSON(fq.contextPath+"/manage/user/getGroupUserJson",{gId:gId},function (data) {
            if(data != null){
                var tableStr = "";
                for(var i=0 ;i<data.length ; i++){
                    var uid = data[i].iacMap.ID;
                    tableStr += "<tr><td>"+ data[i].iacMap.USERNAME +"</td>"+
                            "<td>"+ data[i].iacMap.TRUENAME +"</td>"+
                            "<td><button class=\"btn btn-xs btn-danger\" onclick=\"delGroupUser("+gId+","+uid+")\"><i class=\"icon-remove\"></i> </button></td></tr>";
                }
                $("#group_body").html(tableStr);
            }
        });
    }


    function delGroupUser(gId,uId){
        var deferred = $.Deferred();
        Confirm({
            msg: '确定要删除此用户？',
            onOk: function(){
                $.post(
                        fq.contextPath + "/manage/user/deleteUserGroup",
                        {"gId":gId,"uId":uId},
                        function(){
                            showUserList(gId);
                        },"JSON"
                );
            },
            onCancel: function(){
                deferred.reject();
            }
        })

    }

</script>
