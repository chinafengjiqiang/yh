<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="msgRules" role="dialog" tabindex="-1" id="msgRules" class="modal fade in">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">发送记录</h4>
            </div>
            <div class="modal-body" id="gUserTable">
                <table class="table table-striped table-bordered table-hover" id="userTable" width="100%">
                    <thead>
                    <tr>
                        <th>对象类型</th>
                        <th>接收对象</th>
                        <th>创建时间</th>
                        <th>发送方式</th>
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

    function showRuleList(msgId){
        $.getJSON(fq.contextPath+"/manage/msg/getMsgRuleJson",{msgId:msgId},function (data) {
            var tableStr = "";
            for(var i=0 ;i<data.length ; i++){
                tableStr +=  "<tr><td>"+ data[i].iacMap.SRC_TYPE_NAME +"</td>"+
                                "<td>"+data[i].iacMap.SRC_NAME+"</td>"+
                        "<td>"+data[i].iacMap.CREATE_TIME+"</td>"+
                        "<td>"+data[i].iacMap.OBJ_RANGE_NAME+"</td></TR>";
            }
            $("#user_body").html(tableStr);
        });
    }




</script>
