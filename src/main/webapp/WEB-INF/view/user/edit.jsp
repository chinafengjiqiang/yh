<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="labelModal" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <div class="modal-body">

                <form role="form" name="labelForm" id="labelForm">
                    <div class="form-group" hidden>
                        <label >用户序号：</label>
                        <input type="text" class="form-control" id="ID" name="ID">
                        <input type="text" class="form-control" id="PK_ORG" name="PK_ORG">
                        <input type="text" class="form-control" id="PK_DEPT" name="PK_DEPT">
                    </div>
                    <div class="form-group">
                        <label>用户名：</label>
                        <input type="text" class="form-control" id="USERNAME" name="USERNAME" placeholder="请输入用户名">
                    </div>
                    <div class="form-group">
                        <label>姓名：</label>
                        <input type="text" class="form-control" id="TRUENAME" name="TRUENAME" placeholder="请输入姓名">
                    </div>
                    <div class="form-group">
                        <label>任教科目：</label>
                        <select id="ROLE" name="ROLE" class="select">
                        </select>
                        <script type="text/javascript">
                            attachSelectBox(document.getElementById("ROLE"),'',fq.contextPath+"/dic?type=TEARCH_ROLE");
                        </script>
                    </div>
                    <div class="form-group">
                        <label>手机号码：</label>
                        <input type="text" class="form-control" id="MPHONE" name="MPHONE">
                    </div>
                    <button type="submit" class="btn btn-success">提交</button>
                    <button data-dismiss="modal" class="btn btn-cancel" type="button">取消</button>
                </form>
            </div>
        </div>
    </div>
</div>