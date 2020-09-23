<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==0}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/oa/oaGridParty?cls=0">
            <i class="fa fa-list"></i> 待报送（${notReportNum}）</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/oa/oaGridParty?cls=2"><i class="fa fa-random"></i> ${OA_GRID_PARTY_STATUS_MAP.get(OA_GRID_PARTY_REPORT)}（${reportNum}）</a>
    </li>
</ul>
