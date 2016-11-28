<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="lessonDetail" role="dialog" tabindex="-1" id="lessonDetail" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">课程表</h4>
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
        $.getJSON(fq.contextPath+"/manage/lesson/getLessonDetailJson",{lId:lId},function (data) {
            var tableStr = new StringBuffer();
            for(var i=0 ;i<data.length ; i++){
                tableStr.append("<tr>");
                tableStr.append("<td>").append(data[i].LESSON_NUM).append("</td>");
                tableStr.append("<td>").append(data[i].LESSON_TIME).append("</td>");
                tableStr.append("<td style='cursor: pointer'>").append(data[i].WEEK_ONE_LESSON).append("</td>");
                tableStr.append("<td style='cursor: pointer'>").append(data[i].WEEK_TWO_LESSON).append("</td>");
                tableStr.append("<td style='cursor: pointer'>").append(data[i].WEEK_THREE_LESSON).append("</td>");
                tableStr.append("<td style='cursor: pointer'>").append(data[i].WEEK_FOUR_LESSON).append("</td>");
                tableStr.append("<td style='cursor: pointer'>").append(data[i].WEEK_FIVE_LESSON).append("</td>");
                tableStr.append("</tr>");
            }
            $("#ld_body").html(tableStr.toString());
        });
    }



</script>
