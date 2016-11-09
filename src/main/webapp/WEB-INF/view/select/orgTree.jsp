<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<%
    String org_tree_config = request.getParameter("org_tree_config");
%>
<link rel="stylesheet"
      href="${staticPath}/style/metroStyle.css">
<div class="sidebar widget tree-div" style="margin-top:0;width:220px;height:450px;border: 1px #A9A9A9 solid;background-color: #FFF;">
    <TABLE border=0 height=440px align=left>
        <TR>
            <TD width=260px align=left valign=top>
                <ul id="tree" class="ztree" style="overflow:auto;"></ul>
            </TD>
        </TR>
    </TABLE>
</div>
<script src="${staticPath}/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript">
    function loadList(url) {
        $("#mainbar-list").load(url);
    }

    var cfg_tree = '<%=org_tree_config%>';
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
                setOnclickSource(treeNode);
            },
            onDblClick : function (event,treeId, treeNode) {
                setDbclickSource(treeNode);
            }
        }
    };

    function setOnclickSource(treeNode){
        var id = treeNode.id;
        if(cfg_tree == "dept_tree"){
            var url = fq.contextPath+"/manage/dept/getDeptJson";
            $.getJSON(url,{orgId:id},function (data) {
                setSelectDeptSource(data);
            });
        }
        if(cfg_tree == 'org_tree'){
            var id = treeNode.id;
            var name = treeNode.name;
            setSelectOrgSource(id,name);
        }
    }

    function setDbclickSource(treeNode) {
        if(cfg_tree == 'org_tree'){
            var id = treeNode.id;
            var name = treeNode.name;
            setDbSelectOrgSource(id,name);
        }
    }

    $(function(){
        $.fn.zTree.init($("#tree"), setting);
    });

    //-->
</script>