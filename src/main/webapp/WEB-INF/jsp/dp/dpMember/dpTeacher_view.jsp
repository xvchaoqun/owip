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
                    <i class="ace-icon fa fa-user"></i>教职工党派成员个人信息-${uv.realname}
                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/dp/dpMember_base?userId=${param.userId}">基本信息</a>
                        </li>
                        <li>
                            <a href="javascript:;"
                                data-url="${ctx}/dp/dpEdu?userId=${param.userId}">学习经历</a>
                        </li>
                        <li>
                            <a href="javascript:;"
                               data-url="${ctx}/dp/dpWork?userId=${param.userId}">工作经历</a>
                        </li>
                        <li>
                            <a href="javascript:;"
                               data-url="${ctx}/dp/dpFamily?userId=${param.userId}">家庭成员情况</a>
                        </li>
                        <li>
                            <a href="javascript:;"
                               data-url="${ctx}/dp/dpEva?userId=${param.userId}">年度考核</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                    <c:import url="/dp/dpMember_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>