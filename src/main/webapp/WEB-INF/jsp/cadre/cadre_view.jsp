<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="modal-body">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <c:if test="${param._auth!='self'}">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:" class="closeView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                </c:if>
                <div class="jqgrid-vertical-offset widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="${to=='cadre_base'?'active':''}">
                            <a href="javascript:" data-url="${ctx}/cadre_base?cadreId=${param.cadreId}&_auth=${param._auth}">基本信息</a>
                        </li>
                        <shiro:hasPermission name="cadreEdu:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreEdu_page?cadreId=${param.cadreId}&_auth=${param._auth}">学习经历</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreWork:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreWork_page?cadreId=${param.cadreId}&_auth=${param._auth}">工作经历</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadrePost:*">
                        <li class="${to=='cadrePost_page'?'active':''}">
                            <a href="javascript:" data-url="${ctx}/cadrePost_page?cadreId=${param.cadreId}&_auth=${param._auth}">任职情况</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadrePostInfo:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadrePostInfo_page?cadreId=${param.cadreId}&_auth=${param._auth}">岗位过程信息</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreParttime:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreParttime_page?cadreId=${param.cadreId}&_auth=${param._auth}">社会或学术兼职</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreTrain:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreTrain_page?cadreId=${param.cadreId}&_auth=${param._auth}">培训情况</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreCourse:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreCourse_page?cadreId=${param.cadreId}&_auth=${param._auth}">教学经历</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreResearch:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreResearch_page?cadreId=${param.cadreId}&_auth=${param._auth}">科研情况</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreReward:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreReward_page?rewardType=${CADRE_REWARD_TYPE_OTHER}&cadreId=${param.cadreId}&_auth=${param._auth}">其他奖励情况</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreFamliy:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreFamliy_page?cadreId=${param.cadreId}&_auth=${param._auth}">家庭成员信息</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreCompany:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreCompany_page?cadreId=${param.cadreId}&_auth=${param._auth}">企业、社团兼职</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreConcat:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreConcat_page?cadreId=${param.cadreId}&_auth=${param._auth}">联系方式</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreInspect:*">
                        <li>
                            <a href="javascript:" data-url="">考察记录</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreAdform:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreAdform_page?cadreId=${param.cadreId}&_auth=${param._auth}">干部任免审批表</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="cadreInfoForm:*">
                        <li>
                            <a href="javascript:" data-url="${ctx}/cadreInfoForm_page?cadreId=${param.cadreId}&_auth=${param._auth}">干部信息采集表</a>
                        </li>
                        </shiro:hasPermission>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                        <c:import url="/${to}"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>