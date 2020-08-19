<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_PR%>" var="PCS_POLL_CANDIDATE_PR"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_DW%>" var="PCS_POLL_CANDIDATE_DW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_JW%>" var="PCS_POLL_CANDIDATE_JW"/>
<ul class="jqgrid-vertical-offset nav nav-tabs padding-12 tab-color-blue background-blue">
    <c:if test="${stage!=PCS_POLL_CANDIDATE_JW}">
        <li class="<c:if test="${empty cls&&param.type==PCS_POLL_CANDIDATE_PR}">active</c:if>">
            <a href="javascript:;" onclick="_changePage(${PCS_POLL_CANDIDATE_PR})">
                <i class="fa fa-list"></i> 代表（${cm:trimToZero(hasCountMap.get(PCS_POLL_CANDIDATE_PR))}）</a>
        </li>
    </c:if>
    <li class="<c:if test="${empty cls&&param.type==PCS_POLL_CANDIDATE_DW}">active</c:if>">
        <a href="javascript:;" onclick="_changePage(${PCS_POLL_CANDIDATE_DW})">
            <i class="fa fa-list"></i> 党委委员（${cm:trimToZero(hasCountMap.get(PCS_POLL_CANDIDATE_DW))}）</a>
    </li>
    <li class="<c:if test="${empty cls&&param.type==PCS_POLL_CANDIDATE_JW}">active</c:if>">
        <a href="javascript:;" onclick="_changePage(${PCS_POLL_CANDIDATE_JW})">
            <i class="fa fa-list"></i> 纪委委员（${cm:trimToZero(hasCountMap.get(PCS_POLL_CANDIDATE_JW))}）</a>
    </li>
    <li class="<c:if test="${cls==4}">active</c:if>">
        <a href="javascript:;" onclick="_reportPage()">
            <i class="fa fa-list"></i> 报送候选人</a>
    </li>
</ul>
<script>
    function _changePage(type) {
        $("#body-content-view").loadPage("${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&type="+ type)
    }
    function _reportPage() {
        $("#body-content-view").loadPage("${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&cls=4&_type=${_type}")
    }
</script>