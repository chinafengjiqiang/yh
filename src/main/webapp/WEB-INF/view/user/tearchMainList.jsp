<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@include file="/commons/include.jsp"%>
<div class="mainbar">
    <div class="matter">
        <div class="container">
            <!-- Content -->
            <div class="row">
                <div class="col-md-12">
                    <jsp:include page="../dept/deptTree.jsp">
                        <jsp:param name="config" value="tearch_dept" />
                    </jsp:include>
                    <%--mian--%>
                    <div id="mainbar-list">
                        <!--异步引入页面-->
                        <jsp:include page="${contextPath}/manage/user/goTearchList"></jsp:include>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 弹出窗口的页面 -->
<jsp:include page="edit.jsp"></jsp:include>
<jsp:include page="importTearch.jsp"></jsp:include>
<jsp:include page="selectGroup.jsp"></jsp:include>
<jsp:include page="showUserGroup.jsp"></jsp:include>