<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>

<div class="modal-header">
    <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
    <h4 class="modal-title">选择学校</h4>
</div>
<div class="modal-body">

    <div class="row">

        <div class="col-md-5" style="height: 500px;">
            选择学校(双击选中)
            <jsp:include page="orgTree.jsp">
                <jsp:param name="org_tree_config" value="org_tree"></jsp:param>
            </jsp:include>
        </div>

        <div class="col-md-1" style="height: 500px;text-align: center;">
            <div style="text-align: center;width: 100%;margin-top: 220px;cursor: pointer;">
                <img src="${staticPath}/images/rightbtn2.gif" onclick="addSelect();">
            </div>
            <div style="text-align: center;width: 100%;margin-top: 30px;cursor: pointer;">
                <img src="${staticPath}/images/leftbtn2.gif" onclick="delDestList();">
            </div>
        </div>
        <div class="col-md-5" style="height: 500px;">
            已选择学校(双击删除)
            <select size="20" style="width: 100%;height: 90%;font-size: 14px;overflow: auto;" id="destList_org" ondblclick="delDestList();">
            </select>
        </div>
    </div>

    <div style="text-align: center;">
        <button type="button" class="btn btn-success" onclick="submitSelect();">确定</button>
        <button data-dismiss="modal" class="btn btn-cancel" type="button">取消</button>
    </div>
</div>
<script type="text/javascript" src="${staticPath}/js/selectobject.js"></script>
<script type="text/javascript">
    var sOrgVal = 0;
    var sOrgName='';
    function setSelectOrgSource(id,name) {
        sOrgVal = id;
        sOrgName = name;
    }

    function setDbSelectOrgSource(id,name) {
        if (id > 0) {
            var destList = document.getElementById("destList_org");
            addElement(destList, id, name);
        }
    }
    function addSelect(){
        if (sOrgVal > 0) {
            var destList = document.getElementById("destList_org");
            addElement(destList, sOrgVal, sOrgName);
        }else
        {
            Alert("没有选中学校！");
        }
    }


    function delDestList(){
        var destList = document.getElementById("destList_org");
        if(destList.selectedIndex>=0){
            destList.remove(destList.selectedIndex);
        }
    }

    function submitSelect() {
        var destList = document.getElementById("destList_org");
        if(destList.length < 1){
            Alert("请选择学校！");
            return false;
        }

        var recObjList = document.getElementById("REC_OBJ");
        for(var i=0;i<destList.length;i++) {
            var op=destList.options[i];
            addElement(recObjList, op.value, op.text);
        }

        hideSelectModal();
    }


</script>