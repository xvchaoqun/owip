<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
    <div class="modal-body">
        <!-- PAGE CONTENT BEGINS -->
        <div class="widget-box transparent" id="view-box">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="closeView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/branch_base?id=${param.id}">基本信息</a>
                        </li>
                        <li>
                            <a href="javascript:;" data-url="${ctx}/branchMemberGroup_view?branchId=${param.id}">支部委员会</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8">
                        <c:import url="/branch_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>
<div class="footer-margin"/>
<script>
    $("#jqGrid").setSelection('${param.id}', true);
</script>