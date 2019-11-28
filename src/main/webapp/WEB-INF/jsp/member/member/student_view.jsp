<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="modal-body">

        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                   <%-- <i class="ace-icon fa fa-user"></i>学生党员个人信息--%>
                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/member_base?userId=${param.userId}">基本信息</a>
                        </li>
                        <shiro:hasPermission name="partyPost:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/party/partyPost?userId=${param.userId}">党内任职经历</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="partyReward:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/party/partyReward?userId=${param.userId}&type=3">党内奖励</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="partyPunish:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/party/partyPunish?userId=${param.userId}&type=3">党内惩罚</a>
                        </li>
                        </shiro:hasPermission>
                        <li>
                            <a href="javascript:;"
                               data-url="${ctx}/memberInfoForm_page?userId=${param.userId}">党员信息采集表</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/memberOutflow_view?userId=${param.userId}">党员流出</a>
                        </li>
                        <shiro:hasPermission name="memberOut:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/memberOut_view?userId=${param.userId}">组织关系转出</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="memberTransfer:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/memberTransfer_view?userId=${param.userId}">校内组织关系转接</a>
                        </li>
                        </shiro:hasPermission>
                        <c:forEach items="<%=MemberConstants.MEMBER_STAY_TYPE_MAP%>" var="entry">
                            <li>
                                <a href="javascript:;" data-url="${ctx}/memberStay_view?type=${entry.key}&userId=${param.userId}">${entry.value}申请组织关系暂留</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8" id="partyMemberViewContent">
                    <c:import url="/member_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>
