<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmd/pmdOw?cls=1">
            <i class="fa fa-envelope-open"></i> 党费收缴详情</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmd/pmdOw?cls=2">
            <i class="fa fa-area-chart"></i> 党费管理账簿</a>
    </li>
</ul>