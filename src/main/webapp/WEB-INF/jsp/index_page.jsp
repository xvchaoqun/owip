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
                <shiro:hasPermission name="stat:cadre">
                    <li class="${to=='stat_cadre_index_page'?'active':''}">
                        <a href="javascript:;" data-url="${ctx}/stat_cadre_index_page">干部信息统计</a>
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
                <c:if test="${showBranch}">
                    <shiro:hasPermission name="stat:branch">
                        <li class="${to=='stat_branch_page'?'active':''}">
                            <a href="javascript:;" data-url="${ctx}/stat_branch_page">党支部信息统计</a>
                        </li>
                    </shiro:hasPermission>
                </c:if>
                <shiro:hasPermission name="stat:sys">
                    <li>
                        <a href="javascript:;" data-url="${ctx}/stat_sys_page">系统信息统计</a>
                    </li>
                </shiro:hasPermission>
            </ul>
        </div>
    </div>
    <shiro:hasPermission name="sysMsg:list">
        <c:if test="${sysMsgCount>0}">
        <div class="alert alert-block alert-success" id="sysMsg">
            <button type="button" class="close" data-dismiss="alert">
                <i class="ace-icon fa fa-times"></i>
            </button>
            <i class="ace-icon fa fa-envelope<c:if test="${sysMsgCount==0}">-open-o</c:if>"></i>
            <a href="${ctx}/#/sys/sysMsg?cls=2">您有${sysMsgCount}条未确认的系统提醒</a>。
        </div>
        </c:if>
    </shiro:hasPermission>
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
<style>
    /*#sysMsg a{
        color: orange;
    }*/
    #sysMsg{
        height: 40px;
        padding:7px 15px;
        margin-bottom: 5px;
        /*color: orange;*/
    }
</style>