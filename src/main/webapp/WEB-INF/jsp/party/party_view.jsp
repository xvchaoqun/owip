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
            ${party.name}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" data-target="#party-content">
                <li class="active">
                    <a href="javascript:;" data-url="${ctx}/party_base?id=${param.id}">基本信息</a>
                </li>
                <shiro:hasPermission name="partyReward:list">
                <li>
                    <a href="javascript:;" data-url="${ctx}/party/partyReward?partyId=${param.id}&type=1">党内奖励</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="partyPunish:list">
                <li>
                    <a href="javascript:;" data-url="${ctx}/party/partyPunish?partyId=${param.id}&type=1">党内处分</a>
                </li>
                </shiro:hasPermission>
                <li>
                    <a href="javascript:;" data-url="${ctx}/partyMemberGroup_view?partyId=${param.id}">${_p_partyName}领导班子</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8" id="party-content">
                <c:import url="/party_base"/>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->

<script>
    $("#jqGrid").setSelection('${param.id}', true);
</script>