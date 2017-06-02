<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<li  class="<c:if test="${status==1}">active</c:if>">
    <a href="javascript:;" class="renderBtn" data-url="${ctx}/passport?status=1"><i class="fa fa-circle-o"></i> 集中管理证件</a>
</li>
<li class="dropdown <c:if test="${status==2||status==4}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
        <i class="fa fa-recycle"></i> 取消集中管理证件${status==2?"(未确认) ":(status==4)?"(已确认) ":" "}
        <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 230px">
        <li>
            <a href="javascript:;" class="renderBtn" data-url="${ctx}/passport?status=2">未确认</a>
        </li>
        <li>
            <a href="javascript:;" class="renderBtn" data-url="${ctx}/passport?status=4">已确认</a>
        </li>
    </ul>
</li>
<%--<li  class="<c:if test="${status==2}">active</c:if>">
<a href="?status=2"><i class="fa fa-recycle"></i> 取消集中管理证件（未确认）</a>
</li>
<li  class="<c:if test="${status==4}">active</c:if>">
    <a href="?status=4"><i class="fa fa-recycle"></i> 取消集中管理证件（已确认）</a>
</li>--%>
<li  class="<c:if test="${status==3}">active</c:if>">
<a href="javascript:;" class="renderBtn" data-url="${ctx}/passport?status=3"><i class="fa fa-times"></i> 丢失的证件</a>
</li>

<li  class="<c:if test="${status==5}">active</c:if>">
<a href="javascript:;" class="renderBtn" data-url="${ctx}/passport?status=5"><i class="fa fa-inbox"></i> 保险柜管理</a>
</li>
<li  class="<c:if test="${status==0}">active</c:if>">
    <a href="javascript:;" class="renderBtn" data-url="${ctx}/passport?status=0"><i class="fa fa-bar-chart"></i> 证件信息统计</a>
</li>
