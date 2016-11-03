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
                            <div class="pull-left">教学通知</div>
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
                                            <button type="button" class="btn btn-danger" onclick="delBatch('tbl_org','ID',table)">批量删除</button>
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
                                    <th>通知ID</th>
                                    <th>标题</th>
                                    <th>内容</th>
                                    <th>创建时间</th>
                                    <th>推送方式</th>
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
    var columns = [
        {'data':'ID'},
        {'data':'TITLE'},
        {'data':'CONTENT'},
        {'data':'CREATE_TIME'},
        {'data':'SEND_TYPE'},
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
        table = DataTablePack.serverTable($('#table'),'manage/msg/getMsgList',null,columns,0);
    });




    function delData(id){
        del('tbl_message','ID',table,id);
    }


    formValidate = $("#mForm").validate({
        rules : {
            TITLE : "required",
            CONTENT : "required",
            SEND_TYPE:"required"
        },
        messages : {
            TITLE : "请输入消息标题",
            CONTENT : "请输入消息内容",
            SEND_TYPE:"请选择推送方式",
        },
        submitHandler:function(form){
            submitForm('mForm','manage/msg/saveMsg',table,$('#editModal'));
        }
    });



    $(document).ready(function(){
        $('#table tbody').on('click', '.edit', function () {
            $("#ID").val(getTbodyValue(this,0));
            $("#NAME").val(getTbodyValue(this,1));
        } );

        $("#add").click(function(){
            $("#mForm :input").val("");
        })
    });


    function search(){
        var search = $("#listSearch").val();
        var req = [{"name":"search","value":search}];
        table = DataTablePack.serverTable($('#table'),'manage/msg/getMsgList',req,columns,0);
    }



</script>