<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div class="col-md-12">
    <div class="widget">
        <div class="widget-head">
            <div class="pull-left">课程表</div>
            <div class="clearfix"></div>
        </div>
        <div class="widget-content panel-body">
            <!-- Table -->
            <table class="table table-striped table-bordered table-hover"
                   id="table">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-8">
                            <button type="button" id="exportTmp" class="btn btn-success" onclick="exportTemplate();">课程表模板</button>
                            <button href="#importModal" data-toggle="modal" type="button" id="add_batch"
                                    class="btn btn-success">导入</button>
                            <button type="button" class="btn btn-danger" onclick="delLesson()">批量删除</button>
                            <button type="button" class="btn btn-success" onclick="sendLesson()">课程表推送</button>
                            <button href="#planModal" data-toggle="modal" type="button" id="import_plan"
                                    class="btn btn-success">导入进度</button>
                            <button type="button" class="btn btn-danger" onclick="delLessonPlan()">删除进度</button>
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
                    <th>ID</th>
                    <th>名称</th>
                    <th>学期</th>
                    <th>状态</th>
                    <th>课程表及计划</th>
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
    var deptId = ${deptId};
    var table;
    var formValidate;
    var columns = [
        {'data':'ID'},
        {'data':'NAME'},
        {'data':'TERM'},
        {
            'data':'STATUS',
            'render':function(data,type,full){
                if(data == 0){
                    return '未推送';
                }else if(data == 1){
                    return '已推送';
                }
            }
        },
        {
            'data':null,
            'render':function(data,type,full){
                var btn = "<button href=\"#lessonDetail\" data-toggle=\"modal\" class=\"btn btn-xs btn-info edit\" onclick=\"showDetailList("+data.ID+")\"><i class=\"icon-list\"></i></button>";
                return btn;
            }
        }
    ];
    $(function(){
        var req = [{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/lesson/getLessonList',req,columns,0);
    });


    formValidate = $("#mForm").validate({
        /*rules : {
            NAME : "required",

        },
        messages : {
            NAME : "请输入分组名称",
        },*/
        submitHandler:function(form){
            submitForm('mForm','manage/lesson/saveLesson',table,$('#editModal'));
        }
    });



    $(document).ready(function(){

        $("#planModal").on("hidden.bs.modal", function() {
            $(this).removeData("bs.modal");
        });

        $("#add_batch").click(function(){
            if(deptId <= 0){
                Alert("请选择相应的年级！");
                return false;
            }
            $("#importForm :input").val("");
            $("#PK_DEPT_IMP").val(deptId);
        });

        $("#import_plan").click(function () {
            var selData = table.rows('.selected').data();
            if(selData.length <= 0){
                Alert("请选择课程表！");
                return false;
            }
            if(selData.length > 1){
                Alert("只能选择一个课程表！");
                return false;
            }
            var id = selData[0].ID;
            $("#planForm :input").val("");
            $("#LESSON_ID").val(id);
        });
    });


    function search(){
        var search = $("#listSearch").val();
        var req = [{"name":"search","value":search},{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/lesson/getLessonList',req,columns,0);
    }
    function exportTemplate(){
        window.open(fq.contextPath+"/manage/lesson/exportLessonTmp");
    }


    $("#importForm").validate({
        rules : {
            file : "required",
        },
        messages : {
            file : "请选择要导入的文件",
        },
        submitHandler:function(form){
            ajaxSubmit('importForm','manage/lesson/importLesson',table,$('#importModal'));
        }
    });

    function delLesson(){
        var url = fq.contextPath+"/manage/lesson/del";
        delBatchWUrl(table,"ID",url);
    }
    
    function sendLesson() {
        var selData = table.rows('.selected').data();
        if(selData.length <= 0){
            Alert("请选择要推送的课程表！");
            return false;
        }
        var deferred = $.Deferred();
        Confirm({
            msg: '确定要对课程表进行推送？',
            onOk: function(){
                var ids = [];
                $.each(selData,function (i,obj) {
                    ids.push(obj["ID"])
                });
                var url = fq.contextPath+"/manage/lesson/sendLesson";
                url += "?ids="+ids;
                $.post(url, function (data) {
                    deferred.reject();
                    if(data == 1){
                        alert("推送完成");
                    }else {
                        alert("推送失败");
                    }
                }, 'json');
            },
            onCancel: function(){
                deferred.reject();
            }
        })
    }


    $("#planForm").validate({
        rules : {
            startTime:"required",
            endTime:"required",
            file_plan : "required",
        },
        messages : {
            startTime:"请输入开始时间",
            endTime:"请输入结束时间",
            file_plan : "请选择要导入的文件",
        },
        submitHandler:function(form){
            ajaxSubmit('planForm','manage/lesson/importPlan',table,$('#planModal'));
        }
    });

    function delLessonPlan(){
        var selData = table.rows('.selected').data();
        if(selData.length <= 0){
            Alert("请选择相应的课程表！");
            return false;
        }
        var deferred = $.Deferred();
        Confirm({
            msg: '确定要删除此课程表中的进度？',
            onOk: function(){
                var ids = [];
                $.each(selData,function (i,obj) {
                    ids.push(obj["ID"])
                });
                var url = fq.contextPath+"/manage/lesson/deletePlans";
                url += "?ids="+ids;
                $.post(url, function (result) {
                    deferred.reject();
                    if(result.success){
                        alert("操作成功");
                    }else {
                        alert("操作失败");
                    }
                }, 'json');
            },
            onCancel: function(){
                deferred.reject();
            }
        })
    }
</script>