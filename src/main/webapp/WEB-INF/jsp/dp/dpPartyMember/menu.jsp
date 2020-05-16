<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" data-target="#dp-pm-content">
                <li class="active">
                    <a href="javascript:;"
                       data-url="${ctx}/dp/dpPartyMember?cls=1&groupId=${groupId}">现任委员</a>
                </li>
                <li>
                    <a href="javascript:;"
                       data-url="${ctx}/dp/dpPartyMember?cls=0&groupId=${groupId}">已移除</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8" id="dp-pm-content">
    <c:import url="/dp/dpPartyMember?${cm:encodeQueryString(pageContext.request.queryString)}"/>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid").setSelection('${param.groupId}', true);
    function _delAdminCallback(target) {
        $("ul[data-target=\"#dp-pm-content\"] li.active a").click();
    };
</script>