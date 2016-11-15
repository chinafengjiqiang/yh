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
                                            <button href="#sendModal" data-toggle="modal" type="button" id="send"
                                                    class="btn btn-success">发送消息</button>
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
                                    <th>发送记录</th>
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
<jsp:include page="send.jsp"></jsp:include>
<jsp:include page="showMsgRule.jsp"></jsp:include>


<script type="text/javascript">
    var table;
    var formValidate;
    var dic;
    var columns = [
        {'data':'ID'},
        {'data':'TITLE'},
        {'data':'CONTENT'},
        {'data':'CREATE_TIME'},
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button href=\"#msgRules\" data-toggle=\"modal\" class=\"btn btn-xs btn-info edit\" onclick=\"showRuleList("+data.ID+")\"><i class=\"icon-list\"></i></button>";
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
        dic = getDicList("MSG_SEND_TYPE");
        table = DataTablePack.serverTable($('#table'),'manage/msg/getMsgList',null,columns,0);
    });




    function delData(id){
        del('tbl_message','ID',table,id);
    }


    formValidate = $("#mForm").validate({
        rules : {
            TITLE : "required",
            CONTENT : "required",
        },
        messages : {
            TITLE : "请输入消息标题",
            CONTENT : "请输入消息内容",
        },
        submitHandler:function(form){
            submitForm('mForm','manage/msg/saveMsg',table,$('#editModal'));
        }
    });

    $("#sendForm").validate({
        rules : {
            REC_OBJ : "required",
        },
        messages : {
            REC_OBJ : "请选择对象",
        },
        submitHandler:function(form){
            submitForm('sendForm','manage/msg/saveSend',table,$('#sendModal'));
        }
    });

    $(document).ready(function(){
        $('#table tbody').on('click', '.edit', function () {
            $("#ID").val(getTbodyValue(this,0));
            $("#TITLE").val(getTbodyValue(this,1));
            $("#CONTENT").val(getTbodyValue(this,2));
            var msgTypeText = getTbodyValue(this,4);
            var msgType = getDicValue(dic,msgTypeText);
            $("#SEND_TYPE").val(msgType);
        } );

        $("#add").click(function(){
            $("#mForm :input").val("");
        })

        $("#send").click(function () {
            var selData = table.rows('.selected').data();
            if(selData.length <= 0){
                Alert("请选择要发送的消息！");
                return false;
            }

            var mIds = [];
            $.each(selData,function (i,obj) {
                mIds.push(obj['ID']);
            });
            $("#mIds").val(mIds.join(","));

            var recObjList = document.getElementById("REC_OBJ");
            recObjList.length = 0;
        });
    });


    function search(){
        var search = $("#listSearch").val();
        var req = [{"name":"search","value":search}];
        table = DataTablePack.serverTable($('#table'),'manage/msg/getMsgList',req,columns,0);
    }

</script>