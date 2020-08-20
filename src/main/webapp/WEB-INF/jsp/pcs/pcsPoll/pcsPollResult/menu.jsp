<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_STAGE_THIRD%>" var="PCS_STAGE_THIRD"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_PR%>" var="PCS_USER_TYPE_PR"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_DW%>" var="PCS_USER_TYPE_DW"/>
<c:set value="<%=PcsConstants.PCS_USER_TYPE_JW%>" var="PCS_USER_TYPE_JW"/>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">

    <li class="${cls==1?'active':''}">
        <a href="javascript:;" class="openView" data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=1&type=${PCS_USER_TYPE_DW}">
            <i class="fa fa-list"></i> 党委委员（${cm:trimToZero(hasCountMap.get(PCS_USER_TYPE_DW))}）</a>
    </li>
    <li class="${cls==2?'active':''}">
        <a href="javascript:;" class="openView" data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=2&type=${PCS_USER_TYPE_JW}">
            <i class="fa fa-list"></i> 纪委委员（${cm:trimToZero(hasCountMap.get(PCS_USER_TYPE_JW))}）</a>
    </li>
    <c:if test="${stage!=PCS_STAGE_THIRD}">
        <li class="${cls==3?'active':''}">
            <a href="javascript:;" class="openView" data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=3&type=${PCS_USER_TYPE_PR}">
                <i class="fa fa-list"></i> 代表（${cm:trimToZero(hasCountMap.get(PCS_USER_TYPE_PR))}）</a>
        </li>
    </c:if>
    <li class="${cls==4?'active':''}">
        <a href="javascript:;" class="openView" data-url="${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=4">
            <i class="fa fa-list"></i> 候选人推荐人选名单</a>
    </li>
</ul>