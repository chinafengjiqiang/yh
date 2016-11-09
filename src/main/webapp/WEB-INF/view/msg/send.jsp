<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div aria-hidden="true" aria-labelledby="sendModal" role="dialog" tabindex="-1" id="sendModal" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <div class="modal-body">

                <form role="form" name="sendForm" id="sendForm" onsubmit="selectObj()">
                    <div class="form-group" hidden>
                        <label >序号：</label>
                        <input type="text" class="form-control" id="mIds" name="mIds">
                        <input type="text" class="form-control" id="SRC_RANGE" name="SRC_RANGE">
                    </div>
                    <div class="form-group">
                        <label>对象类型：</label>
                        <select id="REC_TYPE" name="REC_TYPE" class="select">
                        </select>
                        <script type="text/javascript">
                            attachSelectBox(document.getElementById("REC_TYPE"),'',fq.contextPath+"/dic?type=REC_MSG_TYPE");
                        </script>
                    </div>
                    <div class="form-group">
                        <label>对象：</label>
                        <select id="REC_OBJ" name="REC_OBJ" multiple="multiple" class="select" style="width: 250px;height: 200px;" size="5">
                        </select>
                        <button href="#" data-toggle="modal" type="button" id="select" onclick="selectSendObj()"
                                class="btn btn-success" style="margin-top: -180px;width: 60px;left: 380px;">选择</button>
                        <button href="#"  type="button" id="removeSelect"  onclick="removeSendObj()"
                                class="btn btn-success" style="margin-top: -180px;width: 60px;left: 380px;">取消</button>
                    </div>
                    <div class="form-group">
                        <label>发送方式：</label>
                        <select id="SEND_TYPE" name="SEND_TYPE" class="select">
                        </select>
                        <script type="text/javascript">
                            attachSelectBox(document.getElementById("SEND_TYPE"),'',fq.contextPath+"/dic?type=MSG_SEND_TYPE");
                        </script>
                    </div>
                    <button type="submit" class="btn btn-success">提交</button>
                    <button data-dismiss="modal" class="btn btn-cancel" type="button">取消</button>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../select/select.jsp"></jsp:include>
<script>
    var REC_TYPE_MODAL = ['selectUser','selectRole','selectDept','selectGroup',
    'selectOrg'];
    function selectSendObj() {
        var recType = $("#REC_TYPE").val();
        var modalVal = REC_TYPE_MODAL[recType-1];
        var select_url = fq.contextPath+"/select?ret="+modalVal;
        $("#selectModal").modal({
            remote: select_url,
        });
    }

    function removeSendObj() {
        var destList = document.getElementById("REC_OBJ");
        if(destList.selectedIndex>=0){
            destList.remove(destList.selectedIndex);
        }else{
            Alert("请选择要取消的项！");
        }
    }

    function hideSelectModal() {
        $("#selectModal").modal('hide');
    }

    $(function () {
        $("#selectModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });
        
        $("#REC_TYPE").change(function () {
            clearSelectObj();
        });
    });

    function clearSelectObj() {
        var destList = document.getElementById("REC_OBJ");
        destList.length = 0;
    }

    function selectObj(){
        var objList = document.getElementById("REC_OBJ");
        for(var i=0;i<objList.length;i++){
            objList.options[i].selected = true;
        }
    }
</script>