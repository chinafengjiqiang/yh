<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="groups" role="dialog" tabindex="-1" id="groups" class="modal fade in">
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
                        <th>组名称</th>
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
    $.ajaxSettings.async = false;
    $.ajaxSettings.cache = false;

    function showGroupList(uId){
        $.getJSON(fq.contextPath+"/manage/dept/getUsergroupJson",{uId:uId},function (data) {
            var tableStr = "";
            for(var i=0 ;i<data.length ; i++){
                var gId = data[i].iacMap.ID;
                tableStr +=  "<tr><td>"+ data[i].iacMap.NAME +"</td>"+
                        "<td><button class=\"btn btn-xs btn-danger\" onclick=\"delGroup("+gId+","+uId+")\"><i class=\"icon-remove\"></i> </button></td></tr>";
            }
            $("#user_body").html(tableStr);
        });
    }

    function delGroup(gId,uId){
        var deferred = $.Deferred();
        Confirm({
            msg: '确定要删除此分组？',
            onOk: function(){
                $.post(
                        fq.contextPath + "/manage/user/deleteUserGroup",
                        {"gId":gId,"uId":uId},
                        function(){
                            showGroupList(uId);
                        },"JSON"
                );
            },
            onCancel: function(){
                deferred.reject();
            }
        })

    }


</script>
