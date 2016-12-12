<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="lessonDetail" role="dialog" tabindex="-1" id="lessonDetail" class="modal fade in">
    <div class="modal-dialog" style="width: 800px;">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">备课表</h4>
            </div>
            <div class="modal-body" id="gUserTable">
                <table class="table table-striped table-bordered table-hover" id="ldTable" width="100%">
                    <thead>
                    <tr>
                        <th>节数</th>
                        <th>时间</th>
                        <th>星期一</th>
                        <th>星期二</th>
                        <th>星期三</th>
                        <th>星期四</th>
                        <th>星期五</th>
                    </tr>
                    </thead>
                    <tbody id="ld_body">

                    </tbody>
                </table>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $.ajaxSettings.async = false;
    $.ajaxSettings.cache = false;

    function showDetailList(lId){
        $.getJSON(fq.contextPath+"/manage/lesson/getPreLessonDetailJson",{preId:lId},function (data) {
            var tableStr = new StringBuffer();
            for(var i=0 ;i<data.length ; i++){
                var num = data[i].PRE_NUM;
                tableStr.append("<tr>");
                tableStr.append("<td>").append(num).append("</td>");
                tableStr.append("<td>").append(data[i].PRE_TIME).append("</td>");
                var lesssonHref1 = "<a href='#' id='ld_1_"+num+"' data-toggle='popover' data-placement='auto' data-content=''>"+data[i].WEEK_ONE_PRE+"</a>"
                var lesssonHref2 = "<a href='#' id='ld_2_"+num+"' data-toggle='popover' data-placement='auto' data-content=''>"+data[i].WEEK_TWO_PRE+"</a>"
                var lesssonHref3 = "<a href='#' id='ld_3_"+num+"' data-toggle='popover' data-placement='auto' data-content=''>"+data[i].WEEK_THREE_PRE+"</a>"
                var lesssonHref4 = "<a href='#' id='ld_4_"+num+"' data-toggle='popover' data-placement='auto' data-content=''>"+data[i].WEEK_FOUR_PRE+"</a>"
                var lesssonHref5 = "<a href='#' id='ld_5_"+num+"' data-toggle='popover' data-placement='auto' data-content=''>"+data[i].WEEK_FIVE_PRE+"</a>"
                tableStr.append("<td style='cursor: pointer' onclick='showPlan("+lId+","+num+",1)'>").append(lesssonHref1).append("</td>");
                tableStr.append("<td style='cursor: pointer' onclick='showPlan("+lId+","+num+",2)'>").append(lesssonHref2).append("</td>");
                tableStr.append("<td style='cursor: pointer' onclick='showPlan("+lId+","+num+",3)'>").append(lesssonHref3).append("</td>");
                tableStr.append("<td style='cursor: pointer' onclick='showPlan("+lId+","+num+",4)'>").append(lesssonHref4).append("</td>");
                tableStr.append("<td style='cursor: pointer' onclick='showPlan("+lId+","+num+",5)'>").append(lesssonHref5).append("</td>");
                tableStr.append("</tr>");
            }
            $("#ld_body").html(tableStr.toString());
        });
    }

    function showPlan(lId,num,week){

    }

</script>