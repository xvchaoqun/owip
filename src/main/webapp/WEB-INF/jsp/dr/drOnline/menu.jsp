<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1&&status!=3&&isDeleted!=1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=1&isDeleted=0"><i class="fa fa-book"></i> 推荐列表</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=2"><i class="fa fa-drivers-license"></i> 岗位列表</a>
    </li>
    <li class="<c:if test="${cls==1&&status==3&&isDeleted!=1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=1&status=3&isDeleted=0"><i class="fa fa-book"></i> 已完成</a>
    </li>
    <li class="<c:if test="${cls==1&&isDeleted==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=1&isDeleted=1"><i class="fa fa-book"></i> 已删除</a>
    </li>
</ul>
