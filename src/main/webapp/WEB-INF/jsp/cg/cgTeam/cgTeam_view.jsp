<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body">
    <div class="widget-box transparent" id="view-box">
        <div class="widget-header">
            <h4 class="widget-title lighter smaller">
                <a href="javascript:;" class="hideView btn btn-xs btn-success">
                    <i class="ace-icon fa fa-backward"></i> 返回
                </a>
            </h4>
            <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;font-size: large">
                ${cgTeam.name}
            </span>
            <div class="widget-toolbar no-border">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="javascript:;" data-url="${ctx}/cg/cgMember?teamId=${param.teamId}">人员组成</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-url="${ctx}/cg/cgRule?teamId=${param.teamId}">参数设置</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-url="${ctx}/cg/cgUnit?teamId=${param.teamId}">挂靠单位</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-url="${ctx}/cg/cgLeader?teamId=${param.teamId}">办公室主任</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="widget-body">
            <div class="widget-main padding-4">
                <div class="tab-content padding-8" id="tab-content">
                    <c:import url="${ctx}/cg/cgMember?teamId=${param.teamId}"/>
                </div>
            </div>
        </div>
    </div>
</div>