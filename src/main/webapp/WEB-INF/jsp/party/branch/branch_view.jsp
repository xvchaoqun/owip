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
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
            ${cm:displayParty(branch.partyId, branch.id)}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" data-target="#branch-content">
                <li class="active">
                    <a href="javascript:;" data-url="${ctx}/branch_base?id=${param.id}">基本信息</a>
                </li>
                <shiro:hasPermission name="partyReward:list">
                <li>
                    <a href="javascript:;" data-url="${ctx}/party/partyReward?branchId=${param.id}&type=2">党内奖励</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyPunish:list">
                <li>
                    <a href="javascript:;" data-url="${ctx}/party/partyPunish?branchId=${param.id}&type=2">党内处分</a>
                </li>
                </shiro:hasPermission>
                <c:if test="${cm:isPresentBranchAdmin(_user.id, branch.partyId, branch.id)}">
                <li>
                    <a href="javascript:;" data-url="${ctx}/branchMemberGroup_view?branchId=${param.id}">支部委员会</a>
                </li>
                </c:if>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8" id="branch-content">
                <c:import url="/branch_base"/>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->

<script>
    $("#jqGrid").setSelection('${param.id}', true);
</script>