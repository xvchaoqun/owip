<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/cisInspector"
             data-url-export="${ctx}/cisInspector_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${status==CIS_INSPECTOR_STATUS_NOW?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cisInspector?status=${CIS_INSPECTOR_STATUS_NOW}"><i class="fa fa-th${status==CIS_INSPECTOR_STATUS_NOW?'-large':''}"></i> 现任考察组成员</a>
                    </li>
                    <li class="${status==CIS_INSPECTOR_STATUS_HISTORY?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cisInspector?status=${CIS_INSPECTOR_STATUS_HISTORY}"><i class="fa fa-th${status==CIS_INSPECTOR_STATUS_HISTORY?'-large':''}"></i> 过去考察组成员</a>
                    </li>
                    <li class="${status==CIS_INSPECTOR_STATUS_DELETE?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/cisInspector?status=${CIS_INSPECTOR_STATUS_DELETE}"><i class="fa fa-th${status==CIS_INSPECTOR_STATUS_DELETE?'-large':''}"></i> 已删除</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="cisInspector:edit">
                                <c:if test="${status==CIS_INSPECTOR_STATUS_NOW}">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/cisInspector_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                    <button data-url="${ctx}/cisInspector_abolish"
                                            data-title="撤销"
                                            data-msg="确定将这{0}个成员转移到过去考察组成员？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-primary btn-sm">
                                        <i class="fa fa-times"></i> 撤销
                                    </button>
                                </c:if>
                               <%-- <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/cisInspector_au"
                                   data-grid-id="#jqGrid"

                                   data-width="900"><i class="fa fa-edit"></i>
                                    修改</a>--%>
                            </shiro:hasPermission>
                            <c:if test="${status!=CIS_INSPECTOR_STATUS_NOW}">
                                <button data-url="${ctx}/cisInspector_reuse"
                                        data-title="任用"
                                        data-msg="确定将这{0}个成员转移到现任考察组成员？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-success btn-sm">
                                    <i class="fa fa-reply"></i> 重新任用
                                </button>
                            </c:if>
                            <c:if test="${status!=CIS_INSPECTOR_STATUS_DELETE}">
                            <shiro:hasPermission name="cisInspector:del">
                                <button data-url="${ctx}/cisInspector_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            </c:if>
                           <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4>

                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <div class="form-group">
                                            <label>考察组成员</label>
                                            <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}" data-width="350"
                                                    name="userId" data-placeholder="请输入账号或姓名或工号">
                                                <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm">
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
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $.register.user_select($('#searchForm select[name=userId]'));
    $("#jqGrid").jqGrid({
        url: '${ctx}/cisInspector_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '考察组成员', name: 'realname'},
            {label: '工作证号', name: 'code'},
            {label: '考察干部', name: '_cadres', formatter: function (cellvalue, options, rowObject) {
                    return '<a href="#${ctx}/cisInspectObj?cls=1&inspectorId={0}" target="_blank">查看</a>'
                                    .format(encodeURI(rowObject.id));
            }},
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/cisInspector_changeOrder"}, frozen: true
            }
        ]
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>