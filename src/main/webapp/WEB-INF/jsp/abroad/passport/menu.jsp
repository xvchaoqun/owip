<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<li  class="<c:if test="${status==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passport?status=1"><i class="fa fa-circle-o"></i> 集中管理证件(${cm:trimToZero(passport_initCount)})</a>
</li>
<li class="dropdown <c:if test="${status==2||status==4}">active</c:if>" >
    <a data-toggle="dropdown" class="dropdown-toggle" href="javascript:;">
        <i class="fa fa-recycle"></i> 取消集中管理证件
        <c:if test="${status!=2&&status!=4}">
            (${cm:trimToZero(passport_cancelCount)}/${cm:trimToZero(passport_confirmCount)})
        </c:if>
        ${status==2?"(未确认) ":(status==4)?"(已确认) ":" "}
        <i class="ace-icon fa fa-caret-down bigger-110 width-auto"></i>
    </a>
    <ul class="dropdown-menu dropdown-info" style="min-width: 230px">
        <li>
            <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passport?status=2">未确认(${cm:trimToZero(passport_cancelCount)})</a>
        </li>
        <li>
            <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passport?status=4">已确认(${cm:trimToZero(passport_confirmCount)})</a>
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
<a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passport?status=3"><i class="fa fa-times"></i> 丢失的证件(${cm:trimToZero(passport_lostCount)})</a>
</li>

<li  class="<c:if test="${status==5}">active</c:if>">
<a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passport?status=5"><i class="fa fa-inbox"></i> 保险柜管理</a>
</li>
<li  class="<c:if test="${status==0}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/abroad/passport?status=0"><i class="fa fa-bar-chart"></i> 证件信息统计</a>
</li>
