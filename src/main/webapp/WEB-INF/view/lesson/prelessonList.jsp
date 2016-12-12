<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div class="col-md-12">
    <div class="widget">
        <div class="widget-head">
            <div class="pull-left">备课表</div>
            <div class="clearfix"></div>
        </div>
        <div class="widget-content panel-body">
            <!-- Table -->
            <table class="table table-striped table-bordered table-hover"
                   id="table">
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-8">
                            <button type="button" id="exportTmp" class="btn btn-success" onclick="exportTemplate();">备课表模板</button>
                            <button href="#importModal" data-toggle="modal" type="button" id="add_batch"
                                    class="btn btn-success">导入</button>
                            <button type="button" class="btn btn-danger" onclick="delLesson()">批量删除</button>

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
                    <th>开始时间</th>
                    <th>结束时间</th>
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
    var deptId = ${deptId};
    var table;
    var formValidate;
    var columns = [
        {'data':'ID'},
        {'data':'NAME'},
        {'data':'START_DATE'},
        {'data':'END_DATE'},
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
        table = DataTablePack.serverTable($('#table'),'manage/lesson/getPreLessonList',req,columns,0);
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

    });


    function search(){
        var search = $("#listSearch").val();
        var req = [{"name":"search","value":search},{"name":"deptId","value":deptId}];
        table = DataTablePack.serverTable($('#table'),'manage/lesson/getPreLessonList',req,columns,0);
    }
    function exportTemplate(){
        window.open(fq.contextPath+"/manage/lesson/exportPreLessonTmp");
    }


    $("#importForm").validate({
        rules : {
            file : "required",
            startTime:"required",
            endTime:"required"
        },
        messages : {
            file : "请选择要导入的文件",
            startTime:"请输入开始时间",
            endTime:"请输入结束时间"
        },
        submitHandler:function(form){
            ajaxSubmit('importForm','manage/lesson/importPreLesson',table,$('#importModal'));
        }
    });

    function delLesson(){
        var url = fq.contextPath+"/manage/lesson/delpre";
        delBatchWUrl(table,"ID",url);
    }

</script>