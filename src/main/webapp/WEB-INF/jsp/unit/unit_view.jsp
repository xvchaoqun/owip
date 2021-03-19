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
            ${unit.name}
        </span>
        <div class="widget-toolbar no-border">
            <ul class="menu nav nav-tabs" data-target="#unit-content">
                <shiro:hasPermission name="unit:base">
                <li>
                    <a href="javascript:;" data-url="${ctx}/unit_base?id=${param.id}">基本信息</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="unit:unitTeam">
                <li>
                    <a href="javascript:;" data-url="${ctx}/unitTeam?unitId=${param.id}">行政班子</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="unit:unitPost">
                <li>
                    <a href="javascript:;" data-url="${ctx}/unitPostList?unitId=${param.id}">干部岗位</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="unit:dispatchCadre">
                <li>
                    <a href="javascript:;" data-url="${ctx}/unitCadreTransferGroup?unitId=${param.id}">干部任免信息</a>
                </li>
                </shiro:hasPermission>
                <shiro:hasPermission name="unit:unitEvaResult">
                    <li>
                        <a href="javascript:;"
                           data-url="${ctx}/cadreEvaResult?cadreId=${param.id}&_auth=${param._auth}&type=1">年终考核测评结果</a>
                    </li>
                </shiro:hasPermission>
                 <%--<shiro:hasPermission name="unit:unitTransfer">
                <li>
                    <a href="javascript:;" data-url="${ctx}/unitTransfer?unitId=${param.id}">单位发展历程文件</a>
                </li>
                </shiro:hasPermission>--%>
            </ul>
        </div>
    </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <div class="tab-content padding-8" id="unit-content">

            </div>
        </div><!-- /.widget-main -->
    </div><!-- /.widget-body -->
</div><!-- /.widget-box -->
<script>
    $("ul[data-target=\"#unit-content\"] li:first a").click();
</script>