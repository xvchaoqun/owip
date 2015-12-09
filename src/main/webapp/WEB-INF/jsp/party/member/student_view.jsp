<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="modal-body">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="closeView btn btn-mini btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                   <%-- <i class="ace-icon fa fa-user"></i>学生党员个人信息--%>
                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/memberStudent_base?userId=${param.userId}">基本信息</a>
                        </li>
                        <%--<li>
                            <a href="javascript:;" data-url="${ctx}/memberStudent_member?userId=${param.userId}">党籍信息</a>
                        </li>--%>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                    <c:import url="/memberStudent_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>

    <div class="modal-footer center">
        <a href="#" data-dismiss="modal" class="closeView btn btn-default">返回</a>
    </div>
