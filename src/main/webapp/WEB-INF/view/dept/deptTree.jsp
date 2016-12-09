<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<%
    String cfg = request.getParameter("config");
%>
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
            url: fq.contextPath+"/manage/dept/getDeptTree",
            dataType: "json",
            autoParam: ["id","pId"]
        },

        callback: {
            onClick : function(event,treeId, treeNode){
                var url = getTreeClickUrl(treeNode);
                loadList(url);
            }
        }
    };


    $(function(){
        $.fn.zTree.init($("#tree"), setting);
        var url = getTreeClickUrl();
        loadList(url);
    });


    function getTreeClickUrl(treeNode){
        var config= '<%=cfg%>';
        var url = "";

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

        if(config == "group_dept"){//组管理
            url = fq.contextPath+"/manage/dept/goGroupList?deptId="+id+"&orgId="+pid;
        }else if(config == "tearch_dept"){//教师管理
            url = fq.contextPath+"/manage/user/goTearchList?deptId="+id+"&orgId="+pid;
        }else if(config == "lesson_dept"){//课程表
            url = fq.contextPath+"/manage/lesson/goLessonList?deptId="+id;
        }else if(config == "pre_lesson_dept"){//备课表
            url = fq.contextPath+"/manage/lesson/goPreLessonList?deptId="+id;
        }
        return  url;
    }
    //-->
</script>