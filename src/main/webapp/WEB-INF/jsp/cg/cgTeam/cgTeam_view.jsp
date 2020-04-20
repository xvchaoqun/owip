<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${not empty param.fid}" var="hasFid" />
<div class="modal-body">
    <div class="widget-box transparent" id="${hasFid?'view-header-child':'view-header'}">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success"
                   data-hide-el="${hasFid?'#body-content-view2':'#body-content-view'}"
                   data-load-el="${hasFid?'#body-content-view':'#body-content'}">
                    <i class="ace-icon fa fa-backward"></i> 返回
                </a>
            </h4>
            <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;font-size: large">
                <c:set var="fCgTeamName" value="${fCgTeam.name}-" />
               ${not empty fCgTeam?fCgTeamName:''}${cgTeam.name}
            </span>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:;" class="loadPage" data-load-el="${hasFid?'#tab-content-child':'#tab-content'}"
                           data-url="${ctx}/cg/cgMember?fid=${param.fid}&teamId=${param.teamId}">人员组成</a>
                    </li>
                    <li>
                        <a href="javascript:;" class="loadPage" data-load-el="${hasFid?'#tab-content-child':'#tab-content'}"
                           data-url="${ctx}/cg/cgRule?fid=${param.fid}&teamId=${param.teamId}">参数设置</a>
                    </li>
                    <li>
                        <a href="javascript:;" class="loadPage" data-load-el="${hasFid?'#tab-content-child':'#tab-content'}"
                           data-url="${ctx}/cg/cgUnit?fid=${param.fid}&teamId=${param.teamId}">挂靠单位</a>
                    </li>
                    <li>
                        <a href="javascript:;" class="loadPage" data-load-el="${hasFid?'#tab-content-child':'#tab-content'}"
                           data-url="${ctx}/cg/cgLeader?fid=${param.fid}&teamId=${param.teamId}">办公室主任</a>
                    </li>
                    <c:if test="${!hasFid}">
                        <li>
                            <a href="javascript:;" class="loadPage" data-load-el="${hasFid?'#tab-content-child':'#tab-content'}"
                               data-url="${ctx}/cg/cgTeam?fid=${param.teamId}">分委会/工作组</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="padding-8" id="${hasFid?'tab-content-child':'tab-content'}">
                    <c:import url="${ctx}/cg/cgMember?fid=${param.fid}&teamId=${param.teamId}"/>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var hasFid = ${hasFid};
    var view_id = hasFid?$("#view-header-child .widget-toolbar .nav-tabs li"):$("#view-header .widget-toolbar .nav-tabs li");

view_id.click(function () {
    $(this).siblings().removeClass("active");
    $(this).addClass("active");
    clearJqgridSelected();
})
</script>