<%@ page language="java" contentType="text/html; charset=utf-8"
               pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="editModal" role="dialog" tabindex="-1" id="editModal" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <div class="modal-body">

                <form role="form" name="mForm" id="mForm">
                    <div class="form-group" hidden>
                    <label >序号：</label>
                    <input type="text" class="form-control" id="ID" name="ID">
                </div>
                    <div class="form-group">
                        <label>标题：</label>
                        <input type="text" class="form-control" id="TITLE" name="TITLE" placeholder="请输入标题">
                    </div>
                    <div class="form-group">
                        <label>内容：</label>
                        <input type="text" class="form-control" id="CONTENT" name="CONTENT" placeholder="请输入内容">
                    </div>

                    <button type="submit" class="btn btn-success">提交</button>
                    <button data-dismiss="modal" class="btn btn-cancel" type="button">取消</button>
                </form>
            </div>
        </div>
    </div>
</div>