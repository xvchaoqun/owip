<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsVoteStat?cls=1">
            <i class="fa fa-envelope-open"></i> 分发和回收选票情况</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsVoteStat?cls=2&type=${PCS_USER_TYPE_DW}">
            <i class="fa fa-area-chart"></i> 党委委员选举统计</a>
    </li>
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsVoteStat?cls=3&type=${PCS_USER_TYPE_JW}">
            <i class="fa fa-line-chart"></i> 纪委委员选举统计</a>
    </li>
    <li class="<c:if test="${cls==4}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsVoteStat?cls=4&type=${PCS_USER_TYPE_DW}">
            <i class="fa fa-list"></i> 两委当选名单
        </a>
    </li>
</ul>