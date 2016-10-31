<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div class="col-md-12">
                    <div class="widget">
                        <div class="widget-head">
                            <div class="pull-left">年级管理</div>
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
                                            <button type="button" class="btn btn-danger" onclick="delBatch('tbl_department','ID',table)">批量删除</button>
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
                                    <th>年级ID</th>
                                    <th>名称</th>
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
    var table;
    var formValidate;
    var columns = [
        {'data':'ID'},
        {'data':'NAME'},
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
        var req = [{"name":"orgId","value":orgId}];
        table = DataTablePack.serverTable($('#table'),'manage/dept/getDeptList',req,columns,0);
    });




    function delData(id){
        del('tbl_department','ID',table,id);
    }


    formValidate = $("#mForm").validate({
        rules : {
            NAME : "required",

        },
        messages : {
            NAME : "请输入年级名称",
        },
        submitHandler:function(form){
            submitForm('mForm','manage/dept/saveDept',table,$('#editModal'));
        }
    });



    $(document).ready(function(){
        $('#table tbody').on('click', '.edit', function () {
            $("#ID").val(getTbodyValue(this,0));
            $("#NAME").val(getTbodyValue(this,1));
            $("#PK_ORG").val(orgId);
        } );

        $("#add").click(function(){
            if(orgId == 0){
                Alert("请选择相应的学校！");
                return false;
            }
            $("#mForm :input").val("");
            $("#PK_ORG").val(orgId);
        })
    });


    function search(){
        var search = $("#listSearch").val();
        var req = [{"name":"search","value":search},{"name":"orgId","value":orgId}];
        table = DataTablePack.serverTable($('#table'),'manage/dept/getDeptList',req,columns,0);
    }



</script>