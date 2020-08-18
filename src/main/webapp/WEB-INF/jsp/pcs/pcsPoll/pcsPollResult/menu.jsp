<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_PR%>" var="PCS_POLL_CANDIDATE_PR"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_DW%>" var="PCS_POLL_CANDIDATE_DW"/>
<c:set value="<%=PcsConstants.PCS_POLL_CANDIDATE_JW%>" var="PCS_POLL_CANDIDATE_JW"/>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4" id="pcsPollResult-content">
                <c:import url="/pcs/pcsPollResult?pollId=${param.pollId}&type=${param.type}&${cm:encodeQueryString(pageContext.request.queryString)}"/>
        </div>
    </div>
</div>
<script>
    function _changePage(type) {
       $("#pcsPollResult-content").loadPage("${ctx}/pcs/pcsPollResult?pollId=${param.pollId}&type="+ type)
    }
</script>