<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.cadreId}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cadrePositionReport:edit">
                    <button class="openView btn btn-info btn-sm"
                            data-url="${ctx}/cadrePositionReport_au?edit=true&admin=${param.admin}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cadrePositionReport_au?edit=true&admin=${param.admin}"
                       data-grid-id="#jqGrid" data-open-by="page"><i class="fa fa-edit"></i>
                        修改</button>
                   <%-- <a class="jqLinkItemBtn btn btn-success btn-sm"
                       data-url="${ctx}/cadrePositionReport_export"
                       data-grid-id="#jqGrid"
                       data-open-by="page"><i class="fa fa-download"></i>
                        导出WORD</a>--%>
                </shiro:hasPermission>

                <shiro:hasPermission name="cadrePositionReport:adminMenu">
                    <button data-url="${ctx}/cadrePositionReport_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
               <%-- <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cadrePositionReport_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
            <shiro:hasPermission name="cadrePositionReport:adminMenu">
              <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4>
                    <span class="widget-note">${note_searchbar}</span>
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
                            <label>年度</label>

                            <input required class="date-picker"
                                   name="year" type="text"
                                   data-date-start-view="2"
                                   data-date-min-view-mode="2"
                                   data-date-max-view-mode="2"
                                   data-date-format="yyyy"
                                   style="width: 150px"
                                   value="${param.year}"/>
                        </div>
                        <div class="form-group">
                            <label>干部</label>
                            <select required data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/cadre_selects?types=${CADRE_STATUS_CJ},${CADRE_STATUS_CJ_LEAVE}"
                                    name="cadreId" data-width="270" data-placeholder="请输入账号或姓名或学工号">
                                <option value="${param.cadreId}">${cadre.user.realname}-${cadre.user.code}</option>
                            </select>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/cadrePositionReport?admin=${param.admin}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cadrePositionReport?admin=${param.admin}"
                                            data-target="#page-content">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            </shiro:hasPermission>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cadrePositionReport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '详情',name: 'detail', formatter: function (cellvalue, options, rowObject) {

                    return '<button class="openView btn btn-success btn-xs" data-width="700" data-callback="_reload"' +
                        'data-url="${ctx}/cadrePositionReport_au?id={0}&edit=false"><i class="fa fa-search"></i> 详情</button>'
                            .format(rowObject.id)
                   }
                },
                { label: '年度',name: 'year'},
                { label: '工作证号',name: 'cadre.user.code',width: 110},
                { label: '姓名',name: 'cadre.user.realname'},
               /* {
                    label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions: {grid:'#jqGrid',url: '${ctx}/cadrePositionReport_changeOrder'}, frozen: true
                },*/
                { label: '所在单位及职务',name: 'title',align:'left',width: 350},
                { label: '述职报告',name: 'report', formatter: function (cellvalue, options, rowObject) {

                        return ('<button class="downloadBtn btn btn-primary btn-xs" ' +
                            'data-url="${ctx}/cadrePositionReport_export?id={0}"><i class="fa fa-download"></i> 导出</button>').format(rowObject.id);
                        return '--'
                    }
                },
                { label: '上传时间',name: 'createTime',width: 200}

        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>