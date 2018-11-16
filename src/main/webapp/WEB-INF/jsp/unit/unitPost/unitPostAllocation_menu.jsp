<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${module==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=1"><i class="fa fa-table"></i> 内设机构干部配备详情</a>
    </li>
    <li class="<c:if test="${module==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=2"><i class="fa fa-sliders"></i> 空缺岗位详情</a>
    </li>
    <li class="<c:if test="${module==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/unitPostAllocation?module=3"><i class="fa fa-bar-chart"></i> 内设机构干部配备统计</a>
    </li>
</ul>