<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    String contextPath = request.getContextPath();
    String staticPath = contextPath+"/static";
%>
<c:set var="staticPath" value="<%=staticPath%>"></c:set>
<c:set var="contextPath" value="<%=contextPath%>"></c:set>
<script type="text/javascript">
    var fq = fq || {};
    fq.contextPath = '<%=contextPath%>';
    fq.basePath = '<%=basePath%>';
</script>


