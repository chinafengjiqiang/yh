<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="importModal" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">导入备课表</h4>
            </div>
            <div class="modal-body">

                <form name="importForm" id="importForm" enctype="multipart/form-data">
                    <div class="form-group" hidden>
                        <label >用户序号：</label>
                        <input type="text" class="form-control" id="PK_DEPT_IMP" name="PK_DEPT">
                    </div>
                    <div class="form-group">
                        <label>开始时间：</label>
                        <input type="text" name="startTime" id="startTime" class="form-control"
                               onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"  placeholder="请输入开始时间">
                    </div>
                    <div class="form-group">
                        <label>结束时间：</label>
                        <input  type="text" name="endTime" id="endTime" class="form-control"
                                onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"  placeholder="请输入结束时间">
                    </div>
                    <div class="form-group">
                        <label>选择文件：</label>
                        <input type="file" class="form-control" id="file" name="file">
                    </div>
                    <button type="submit" class="btn btn-success">提交</button>
                    <button data-dismiss="modal" class="btn btn-cancel" type="button">取消</button>
                </form>
            </div>
        </div>
    </div>
</div>