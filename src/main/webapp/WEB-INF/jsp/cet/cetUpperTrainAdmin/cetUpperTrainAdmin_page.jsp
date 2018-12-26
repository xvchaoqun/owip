<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_UPPER_TRAIN_UPPER" value="<%=CetConstants.CET_UPPER_TRAIN_UPPER%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type ||not empty param.unitId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="active">
                    <a href="javascript:;" class="loadPage"
                       data-url="${ctx}/cet/cetUpperTrainAdmin?upperType=${upperType}"><i
                            class="fa fa-users"></i> ${upperType==CET_UPPER_TRAIN_UPPER?'上级':'二级'}单位管理员</a>
                </li>
                <li>
                    <a href="javascript:;" class="loadPage"
                       data-load-el="#detail-content" data-callback="$.menu.liSelected"
                       data-url="${ctx}/metaClass_type_list?cls=${upperType==CET_UPPER_TRAIN_UPPER?'mc_cet_upper_train_organizer,':''}mc_cet_upper_train_type${upperType==CET_UPPER_TRAIN_UPPER?'':'2'},mc_cet_upper_train_special${upperType==CET_UPPER_TRAIN_UPPER?'':'2'}"><i
                            class="fa fa-info-circle"></i> ${upperType==CET_UPPER_TRAIN_UPPER?'调训':'培训'}专项信息</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <div id="detail-content">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetUpperTrainAdmin:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetUpperTrainAdmin_au?upperType=${upperType}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetUpperTrainAdmin_au?upperType=${upperType}"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetUpperTrainAdmin:del">
                    <button data-url="${ctx}/cet/cetUpperTrainAdmin_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetUpperTrainAdmin_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>

                    <div class="widget-toolbar">
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <div class="form-group">
                            <div class="form-group">
                                <label>类型</label>
                                <select required data-rel="select2"
                                        name="type" data-placeholder="请选择">
                                    <option value="0">单位管理员</option>
                                    <option value="1">校领导管理员</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=type]").val('${param.type}')
                                </script>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>所属单位</label>
                            <select required data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                    name="unitId" data-placeholder="请选择">
                                <option value="${unit.id}" title="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>管理员</label>
                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                    name="userId" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                            </select>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cet/cetUpperTrainAdmin?upperType=${upperType}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetUpperTrainAdmin?upperType=${upperType}"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetUpperTrainAdmin_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '类型',name: 'type', width:150, formatter: $.jgrid.formatter.TRUEFALSE, formatoptions:{on:'校领导管理员',off:'单位管理员'}},
                { label: '所属单位',name: 'unit.name', width:250, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.type) {
                        return '-'
                    }
                    return $.trim(cellvalue)
                }},
                { label: '所属校领导',name: 'leaderUser.realname', width:150, formatter: function (cellvalue, options, rowObject) {
                    if (!rowObject.type) {
                        return '-'
                    }
                    return $.trim(cellvalue)
                }},
                { label: '管理员姓名',name: 'user.realname', width:150 },
                { label: '管理员工号',name: 'user.code', width:150 }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.del_select($('#searchForm select[name=unitId]'));
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>