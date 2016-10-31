<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<link rel="stylesheet"
      href="${staticPath}/style/metroStyle.css">
<div class="sidebar widget tree-div">
    <div class="widget-head">
        <div class="pull-left">学校列表</div>
        <div class="clearfix"></div>
    </div>
    <TABLE border=0 height=600px align=left>
        <TR>
            <TD width=260px align=left valign=top>
                <ul id="tree" class="ztree" style="width:260px; overflow:auto;"></ul>
            </TD>
        </TR>
    </TABLE>
</div>
<script src="${staticPath}/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript">
    function loadList(url) {
        $("#mainbar-list").load(url);
    }

    var setting = {
        view: {
            dblClickExpand: false,
            showLine: true,
            selectedMulti: false
        },

        async: {
            enable: true,
            url: fq.contextPath+"/manage/dept/getOrgTree",
            dataType: "json",
            autoParam: ["id"]
        },

        callback: {
            onClick : function(event,treeId, treeNode){
                var id = treeNode.id;
                var url = fq.contextPath+"/manage/dept/goDeptList?id="+treeNode.id;
                loadList(url);
            }
        }
    };


    $(function(){
        $.fn.zTree.init($("#tree"), setting);
        loadList(fq.contextPath+"/manage/dept/goDeptList");
    });

    //-->
</script>