<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="selectModal" class="modal fade in">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">

            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">选择分组</h4>
            </div>
            <div class="modal-body">

                <div class="row">

                    <div class="col-md-4" style="height: 500px;">
                        选择学校
                        <jsp:include page="deptSelectTree.jsp"></jsp:include>
                    </div>
                    <div class="col-md-4" style="height: 500px;">
                        分组(双击可选中)
                        <select size="20" style="width: 100%;height: 90%;font-size: 14px;overflow: auto;" id="sourceList" ondblclick="addSelect();">
                        </select>

                    </div>
                    <div class="col-md-1" style="height: 500px;text-align: center;">
                        <div style="text-align: center;width: 100%;margin-top: 220px;cursor: pointer;">
                            <img src="${staticPath}/images/rightbtn2.gif" onclick="addSelect();">
                        </div>
                        <div style="text-align: center;width: 100%;margin-top: 30px;cursor: pointer;">
                            <img src="${staticPath}/images/leftbtn2.gif" onclick="delDestList();">
                        </div>
                    </div>
                    <div class="col-md-3" style="height: 500px;">
                        已选择分组(双击删除)
                        <select size="20" style="width: 100%;height: 90%;font-size: 14px;overflow: auto;" id="destList" ondblclick="delDestList();">
                        </select>
                        <input type="hidden" id="tIds">
                    </div>
                </div>

                <div style="text-align: center;">
                    <button type="button" class="btn btn-success" onclick="submitSelect();">确定</button>
                    <button data-dismiss="modal" class="btn btn-cancel" type="button">取消</button>
                </div>
            </div>

        </div>
    </div>
</div>

<script type="text/javascript" src="${staticPath}/js/selectobject.js"></script>
<script type="text/javascript">

    function setSource(result) {
        if(result != null){
            var sourceList = document.getElementById("sourceList");
            sourceList.length = 0;
            for(var i=0;i < result.length;i++){
                var id = result[i].iacMap.ID;
                var name = result[i].iacMap.NAME;
                addElement(sourceList,id, name);
            }
        }
    }

    function addSelect(){
        var sourceList = document.getElementById("sourceList");
        if (sourceList != null && sourceList.selectedIndex >= 0) {
            var op = sourceList.options[sourceList.selectedIndex];
            var destList = document.getElementById("destList");
            addElement(destList, op.value, op.text);
        }else
        {
            Alert("没有选中分组！");
        }
    }


    function delDestList(){
        var destList = document.getElementById("destList");
        if(destList.selectedIndex>=0){
            destList.remove(destList.selectedIndex);
        }
    }

    function submitSelect() {
         var destList = document.getElementById("destList");
         if(destList.length < 1){
         Alert("请选择要设置的分组！");
         return false;
         }
         var ids = [];
         for(var i=0;i<destList.length;i++) {
             var op=destList.options[i];
             ids.push(op.value);
         }

         var tIds = $("#tIds").val();
         $.post(fq.contextPath+"/manage/user/setTearchGroup", {"gIds":ids.join(","),"tIds":tIds}, function (result) {
         if(result.success){
            $("#selectModal").modal('hide');
            Alert("设置成功！");
         }else{
            Alert("设置失败！");
         }
         }, 'json');
     }
</script>