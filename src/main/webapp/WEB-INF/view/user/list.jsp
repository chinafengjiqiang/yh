<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div class="mainbar">
    <div class="matter">
        <div class="container">
            <!-- Content -->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-head">
                            <div class="pull-left">用户管理</div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="widget-content panel-body">
                            <!-- Table -->
                            <table class="table table-striped table-bordered table-hover"
                                   id="table">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-4">
                                            <button href="#labelModal" data-toggle="modal" type="button" id="add"
                                                    class="btn btn-success">&nbsp;&nbsp;添&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;</button>
                                            <button type="button" id="exportTmp" class="btn btn-success" onclick="exportTemplate();">下载模板</button>
                                            <button href="#labelModal" data-toggle="modal" type="button" id="add_batch"
                                                    class="btn btn-success">批量导入</button>
                                            <button type="button" class="btn btn-danger" onclick="delBatch('TBL_LABEL','ID',table)">批量删除</button>
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
                                    <th>用户ID</th>
                                    <th>用户名</th>
                                    <th>姓名</th>
                                    <th>任教科目</th>
                                    <th>手机号码</th>
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
            </div>
        </div>
    </div>
</div>
<!-- 弹出窗口的页面 -->
<jsp:include page="edit.jsp"></jsp:include>


<script type="text/javascript">
    var table;
    var formValidate;
    var dicMap;
    var columns = [
        {'data':'ID'},
        {'data':'USERNAME'},
        {'data':'TRUENAME'},
        {
            'data':'ROLE',
            'render':function(data,type,full){
                var name = dicMap.get(data);
                return name;
            }
        },
        {'data':'MPHONE'},
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button  href=\"#labelModal\" data-toggle=\"modal\" class=\"btn btn-xs btn-success edit\"><i class=\"icon-pencil\"></i></button></a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "<button id=\"del\" class=\"btn btn-xs btn-danger\" onclick=\"labeldel("+data.ID+")\"><i class=\"icon-remove\"></i> </button>"
                return btn;
            }
        }
    ];
    $(function(){
        dicMap = getDicList("TEARCH_ROLE");
        table = DataTablePack.serverTable($('#table'),'manage/user/getUserList',null,columns,0);
    });




    function labeldel(id){
        del('TBL_LABEL','ID',table,id);
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
            submitForm('labelForm','manage/user/save',table,$('#labelModal'));
        }
    });

    $(document).ready(function(){
        $('#table tbody').on('click', '.edit', function () {
            $("#ID").val(getTbodyValue(this,0));
            $("#USERNAME").val(getTbodyValue(this,1));
            $("#TRUENAME").val(getTbodyValue(this,2));
            var roleText = getTbodyValue(this,3);
            var role = dicMap.getKey(roleText);
            alert(role);
            $("#ROLE").val(role);
            $("#MPHONE").val(getTbodyValue(this,4));
        } );

        $("#add").click(function(){
            $("#labelForm :input").val("");
        })
    });


    function search(){
        var search = $("#labelSearch").val();
        var req = [{"name":"search","value":search}];
        table = DataTablePack.serverTable($('#table'),'manage/user/getUserList',req,columns,0);
    }

    function exportTemplate(){
        window.open(fq.contextPath+"/manage/user/export");
    }

</script>