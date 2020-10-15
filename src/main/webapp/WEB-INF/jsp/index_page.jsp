<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent" style="margin-top: -41px">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            &nbsp;
        </h4>
        <div class="widget-toolbar no-border">
            <ul class="nav nav-tabs" data-target="#index-content">
                <li class="${to=='user_base'?'active':''}">
                    <a href="javascript:;" data-url="${ctx}/user_base">个人信息</a>
                </li>
                <shiro:hasPermission name="suspend:page">
                    <li class="${to=='suspend_page'?'active':''}">
                        <a href="javascript:;" data-url="${ctx}/suspend_page">待办事项</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="stat:ow">
                    <li class="${to=='stat_ow_page'?'active':''}">
                        <a href="javascript:;" data-url="${ctx}/stat_ow_page">党建信息统计</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="stat:party">
                    <li class="${to=='stat_party_page'?'active':''}">
                        <a href="javascript:;" data-url="${ctx}/stat_party_page">${_p_partyName}信息统计</a>
                    </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="stat:sys">
                    <li>
                        <a href="javascript:;" data-url="${ctx}/stat_sys_page">系统信息统计</a>
                    </li>
                </shiro:hasPermission>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8" id="index-content">
                <c:import url="/${to}"/>
            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div>
<!-- /.widget-box -->
<div style="clear: both"></div>
