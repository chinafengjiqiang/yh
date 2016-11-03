<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div class="col-md-12">
    <div class="widget">
        <div class="widget-head">
            <div class="pull-left">分组管理</div>
            <div class="clearfix"></div>
        </div>
        <div class="widget-content panel-body">
            <!-- Table -->
            <table class="table table-striped table-bordered table-hover"
                   id="table">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-4">
                            <button href="#editModal" data-toggle="modal" type="button" id="add"
                                    class="btn btn-success">&nbsp;&nbsp;添&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;</button>
                            <button type="button" class="btn btn-danger" onclick="delBatch('tbl_group','ID',table)">批量删除</button>
                        </div>
                        <div class="bread-crumb pull-right">
                            <form action="" class="">
                                <input id="listSearch" type="search" class="search" placeholder="Search">
                                <button type="button" class="btn btn-default btn-search" onclick="search()">查询</button>
                            </form>
                        </div>
                    </div>
                </div>
                <thead>
                <tr>
                    <th>分组ID</th>
                    <th>名称</th>
                    <th>用户</th>
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
    var orgId = ${orgId};
    var deptId = ${deptId};
    var table;
    var formValidate;
    var columns = [
        {'data':'ID'},
        {'data':'NAME'},
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button href=\"#gUsers\" data-toggle=\"modal\" class=\"btn btn-xs btn-info edit\" onclick=\"showUserList("+data.ID+")\"><i class=\"icon-list\"></i></button>";
                return btn;
            }
        },
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button  href=\"#editModal\" data-toggle=\"modal\" class=\"btn btn-xs btn-success edit\"><i class=\"icon-pencil\"></i></button></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "<button id=\"del\" class=\"btn btn-xs btn-danger\" onclick=\"delData("+data.ID+")\"><i class=\"icon-remove\"></i> </button>"
                return btn;
            }
        }
    ];
    $(function(){
        var req = [{"name":"orgId","value":orgId},{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/dept/getGroupList',req,columns,0);
    });




    function delData(id){
        del('tbl_group','ID',table,id);
    }


    formValidate = $("#mForm").validate({
        rules : {
            NAME : "required",

        },
        messages : {
            NAME : "请输入分组名称",
        },
        submitHandler:function(form){
            submitForm('mForm','manage/dept/saveGroup',table,$('#editModal'));
        }
    });



    $(document).ready(function(){
        $('#table tbody').on('click', '.edit', function () {
            $("#ID").val(getTbodyValue(this,0));
            $("#NAME").val(getTbodyValue(this,1));
            $("#PK_ORG").val(orgId);
            $("#PK_DEPT").val(deptId);
        } );

        $("#add").click(function(){
            if(orgId == 0 && deptId <= 0){
                Alert("请选择相应的学校或年级！");
                return false;
            }
            $("#mForm :input").val("");
            $("#PK_ORG").val(orgId);
            $("#PK_DEPT").val(deptId);
        })
    });


    function search(){
        var search = $("#listSearch").val();
        var req = [{"name":"search","value":search},{"name":"orgId","value":orgId},{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/dept/getGroupList',req,columns,0);
    }



</script>