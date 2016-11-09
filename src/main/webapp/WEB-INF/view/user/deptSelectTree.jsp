<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<%
    String dept_tree_config = request.getParameter("dept_tree_config");
%>
<link rel="stylesheet"
      href="${staticPath}/style/metroStyle.css">
<div class="sidebar tree-div" style="margin-top:0;width:220px;height:450px;border: 1px #A9A9A9 solid;background-color: #FFF;">
    <TABLE border=0 height=440px align=left>
        <TR>
            <TD align=left valign=top>
                <ul id="tree_select<%=dept_tree_config%>" class="ztree" style="overflow:auto;"></ul>
            </TD>
        </TR>
    </TABLE>
</div>
<script src="${staticPath}/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript">
    var cfg_tree = '<%=dept_tree_config%>';
    function loadList(url) {
        $("#mainbar-list").load(url);
    }

    var setting_select = {
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },

        async: {
            enable: true,
            url: fq.contextPath+"/manage/dept/getDeptTree",
            dataType: "json",
            autoParam: ["id","pId"]
        },

        callback: {
            onClick : function(event,treeId, treeNode){
               setSelectSource(treeNode);
            },
        }
    };


    function setSelectSource(treeNode){
        var pid;
        var id;
        if(treeNode != null){
            pid = treeNode.pId;
            id = treeNode.id;
            //处理学校,点击学校时把学校ID赋值给orgId并把deptId清零.
            if(pid == 0){
                pid = id;
                id = 0;
            }
        }
        if(cfg_tree == "user_tree"){
            var url = fq.contextPath+"/manage/user/getDeptUserJson";
            $.getJSON(url,{deptId:id,orgId:pid,type:2},function (data) {
                setSelectUserSource(data);
            });
        }else if(cfg_tree == 'group_tree'){
            var url = fq.contextPath+"/manage/dept/getGroupJson";
            $.getJSON(url,{deptId:id,orgId:pid},function (data) {
                setSelectGroupSource(data);
            });
        }else if(cfg_tree == 'role_tree'){
            clearAllSelect();
            if(id <= 0){
                return;
            }
            var url = fq.contextPath+"/dic";
            $.getJSON(url,{type:'TEARCH_ROLE'},function (data) {
                setSelectRoleSource(id,treeNode.name,data);
            });
        }
        else{
            var url = fq.contextPath+"/manage/dept/getGroupJson";
            $.getJSON(url,{deptId:id,orgId:pid},function (data) {
                setSource(data);
            });
        }

    }

    $(function(){
        $.fn.zTree.init($("#tree_select"+cfg_tree), setting_select);
    });



    //-->
</script>