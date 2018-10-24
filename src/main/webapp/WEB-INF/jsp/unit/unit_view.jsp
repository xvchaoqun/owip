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
                </h4>
                <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${unit.name}
                </span>
                <div class="widget-toolbar no-border">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="javascript:;" data-url="${ctx}/unit_base?id=${param.id}">基本信息</a>
                        </li>
                        <shiro:hasPermission name="unitTransfer:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitTransfer?unitId=${param.id}">单位历程相关文件</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="unitPost:*">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitPostList?unitId=${param.id}">干部配置</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="unitAdminGroup:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitAdminGroup?unitId=${param.id}">行政班子</a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="unitCadreTransferGroup:list">
                        <li>
                            <a href="javascript:;" data-url="${ctx}/unitCadreTransferGroup?unitId=${param.id}">干部任免信息</a>
                        </li>
                        </shiro:hasPermission>
                    </ul>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-main padding-4">
                    <div class="tab-content padding-8" id="tab-content">
                        <c:import url="/unit_base"/>
                    </div>
                </div><!-- /.widget-main -->
            </div><!-- /.widget-body -->
        </div><!-- /.widget-box -->
    </div>