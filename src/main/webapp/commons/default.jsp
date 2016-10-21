<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div class="mainbar">
    <div class="matter">
        <div class="container">
            <!-- Content -->
            <div class="row">
                <div class="col-md-12">
                    <div class="widget">
                        <div class="widget-head">
                            <div class="pull-left">育恒教育管理平台</div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="widget-content panel-body" style="height: 70%">
                            <div style="text-align: center;"><img src="${staticPath}/images/welcome.png" height="100%"/></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="cueModal" class="modal fade in">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 500px;height: 270px">
            <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
                <h4 class="modal-title">温馨提示</h4>
            </div>
            <div class="modal-body cue-body">
                <div>1.首次登陆需点击“系统设置”，设置管理员相关信息</div>
                <div>2.点击“数据同步”，同步机构和用户信息</div>
                <button id="cueBtn" class="btn btn-success cueBtn pull-right" type="button" onclick="cueClose()">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    function cueClose(){
        $("#cueModal").modal('hide');
    }
</script>
