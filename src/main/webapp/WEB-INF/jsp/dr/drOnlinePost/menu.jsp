<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent" id="view-box">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8 multi-row-head-table">
    <c:import url="/dr/drOnlinePost?onlineId=${onlineId}&${cm:encodeQueryString(pageContext.request.queryString)}"/>
            </div>
        </div>
    </div>
</div>
<script>
</script>