<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1&&status!=3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=1"><i class="fa fa-book"></i> 按批次管理</a>
    </li>
    <li class="<c:if test="${cls==1&&status==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=1&status=3"><i class="fa fa-book"></i> 已完成批次</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnline?cls=2"><i class="fa fa-drivers-license"></i> 按岗位管理</a>
    </li>
</ul>
