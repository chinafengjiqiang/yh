<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>

                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-head">
                            <div class="pull-left">教师管理</div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="widget-content panel-body">
                            <!-- Table -->
                            <table class="table table-striped table-bordered table-hover"
                                   id="table">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-8">
                                            <button href="#labelModal" data-toggle="modal" type="button" id="add"
                                                    class="btn btn-success">&nbsp;&nbsp;添&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;</button>
                                            <button type="button" id="exportTmp" class="btn btn-success" onclick="exportTemplate();">下载模板</button>
                                            <button href="#importModal" data-toggle="modal" type="button" id="add_batch"
                                                    class="btn btn-success">批量导入</button>
                                            <button type="button" class="btn btn-danger" onclick="delBatch('tbl_user','ID',table)">批量删除</button>
                                            <button href="#selectModal" data-toggle="modal" type="button" id="user_group"
                                                    class="btn btn-success">设置分组</button>
                                        </div>
                                        <div class="bread-crumb pull-right">
                                            <form action="" class="">
                                                <input id="labelSearch" type="search" class="search" placeholder="Search">
                                                <button type="button" class="btn btn-default btn-search" onclick="search()">查询</button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <thead>
                                <tr>
                                    <th>教师ID</th>
                                    <th>用户名</th>
                                    <th>姓名</th>
                                    <th>任教科目</th>
                                    <th>手机号码</th>
                                    <th>分组</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                            <!-- Table ends -->
                        </div>
                    </div>
                </div>




<script type="text/javascript">
    var table;
    var formValidate;
    var dic;
    var deptId = ${deptId};
    var orgId = ${orgId};
    var columns = [
        {'data':'ID'},
        {'data':'USERNAME'},
        {'data':'TRUENAME'},
        {
            'data':'ROLE',
            'render':function(data,type,full){
                var name = getDicText(dic,data);
                return name;
            }
        },

        {'data':'MPHONE'},
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button href=\"#groups\" data-toggle=\"modal\" class=\"btn btn-xs btn-info edit\" onclick=\"showGroupList("+data.ID+")\"><i class=\"icon-list\"></i></button>";
                return btn;
            }
        },
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button  href=\"#labelModal\" data-toggle=\"modal\" class=\"btn btn-xs btn-success edit\"><i class=\"icon-pencil\"></i></button></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "<button id=\"del\" class=\"btn btn-xs btn-danger\" onclick=\"delTearch("+data.ID+")\"><i class=\"icon-remove\"></i> </button>"
                return btn;
            }
        }
    ];
    $(function(){
        dic = getDicList("TEARCH_ROLE");
        var req = [{"name":"orgId","value":orgId},{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/user/getTearchList',req,columns,0);
    });




    function delTearch(id){
        del('tbl_user','ID',table,id);
    }


    formValidate = $("#labelForm").validate({
        rules : {
            USERNAME : "required",
            TRUENAME : "required",
            ROLE : "required",
        },
        messages : {
            USERNAME : "请输入用户名",
            TRUENAME : "请输入姓名",
            ROLE : "请选择任教科目",
        },
        submitHandler:function(form){
            submitForm('labelForm','manage/user/saveTearch',table,$('#labelModal'));
        }
    });

    $("#importForm").validate({
        rules : {
            file : "required",
        },
        messages : {
            file : "请选择要导入的文件",
        },
        submitHandler:function(form){
            ajaxSubmit('importForm','manage/user/importTeacher',table,$('#importModal'));
        }
    });

    $(document).ready(function(){
        $('#table tbody').on('click', '.edit', function () {
            $("#ID").val(getTbodyValue(this,0));
            $("#USERNAME").val(getTbodyValue(this,1));
            $("#TRUENAME").val(getTbodyValue(this,2));
            var roleText = getTbodyValue(this,3);
            var role = getDicValue(dic,roleText);
            $("#ROLE").val(role);
            $("#MPHONE").val(getTbodyValue(this,4));
            $("#PK_ORG").val(orgId);
            $("#PK_DEPT").val(deptId);

        } );

        $("#add").click(function(){
            if(orgId == 0 && deptId <= 0){
                Alert("请选择相应的学校或年级！");
                return false;
            }
            $("#labelForm :input").val("");
            $("#PK_ORG").val(orgId);
            $("#PK_DEPT").val(deptId);
        });


        $("#add_batch").click(function(){
            if(orgId == 0 && deptId <= 0){
                Alert("请选择相应的学校或年级！");
                return false;
            }
            $("#importForm :input").val("");
            $("#PK_ORG_IMP").val(orgId);
            $("#PK_DEPT_IMP").val(deptId);
        });
        
        $("#user_group").click(function () {
            var selData = table.rows('.selected').data();
            if(selData.length <= 0){
                Alert("请选择教师！");
                return false;
            }

            var tIds = [];
            $.each(selData,function (i,obj) {
                tIds.push(obj['ID']);
            });
            $("#tIds").val(tIds.join(","));
        });

    });


    function search(){
        var search = $("#labelSearch").val();
        var req = [{"name":"search","value":search},{"name":"orgId","value":orgId},{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/user/getTearchList',req,columns,0);
    }

    function exportTemplate(){
        window.open(fq.contextPath+"/manage/user/exportTearchTmp");
    }

</script>