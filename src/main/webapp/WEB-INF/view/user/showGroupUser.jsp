<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="users" role="dialog" tabindex="-1" id="users" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">用户分组</h4>
            </div>
            <div class="modal-body" id="gUserTable">
                <table class="table table-striped table-bordered table-hover" id="userTable" width="100%">
                    <thead>
                    <tr>
                        <th>姓名</th>
                        <th>删除</th>
                    </tr>
                    </thead>
                    <tbody id="user_body">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function showUserList(gId){
        $.getJSON(fq.contextPath+"/manage/user/getGroupUserJson",{gId:gId},function (data) {
            if(data != null){
                alert(data);
                var tableStr = "";
                for(var i=0 ;i<data.length ; i++){
                    alert(data[i].iacMap.TRUENAME);
                    tableStr = tableStr + "<tr><td>"+ data[i].iacMap.TRUENAME +"</td>"+
                            "<td><button class=\"btn btn-xs btn-danger\" onclick=\"delCategory("+fileId+","+data[i].CATEGORY_ID+")\"><i class=\"icon-remove\"></i> </button></td>";
                }
                alert(tableStr);
                $("#user_body").html(tableStr);
            }
        });
    }
    function delCategory(fileId,categoryId){
        var deferred = $.Deferred();
        Confirm({
            msg: '确定要删除数据？',
            onOk: function(){
                $.post(
                        "file/delFileCategory",
                        {"fileId":fileId,"categoryId":categoryId},
                        function(){
                            fileCategory(fileId);
                        },"JSON"
                );
            },
            onCancel: function(){
                deferred.reject();
            }
        })

    }

</script>
