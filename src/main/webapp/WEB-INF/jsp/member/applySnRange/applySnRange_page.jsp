<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box transparent">
                <div class="widget-header">
                    <jsp:include page="/WEB-INF/jsp/member/memberApply/menu.jsp"/>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right" style="padding-top: 5px;">
                        <div class="tab-content padding-4">
                            <div class="multi-row-head-table tab-pane in active">

                                <c:set var="_query"
                                       value="${not empty param.year ||not empty param.startSn ||not empty param.endSn || not empty param.code || not empty param.sort}"/>
                                <div class="jqgrid-vertical-offset buttons">
                                    <shiro:hasPermission name="applySnRange:edit">
                                        <button class="popupBtn btn btn-success btn-sm"
                                                data-url="${ctx}/applySnRange_au">
                                            <i class="fa fa-plus"></i> 添加
                                        </button>
                                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                                data-url="${ctx}/applySnRange_au"
                                                data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                            修改
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="applySnRange:del">
                                        <button data-url="${ctx}/applySnRange_batchDel"
                                                data-title="删除"
                                                data-msg="确定删除这{0}条数据？"
                                                data-grid-id="#jqGrid"
                                                class="jqBatchBtn btn btn-danger btn-sm">
                                            <i class="fa fa-trash"></i> 删除
                                        </button>
                                    </shiro:hasPermission>
                                    <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/applySnRange_data"
                                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                        <i class="fa fa-download"></i> 导出
                                    </button>--%>
                                    <span class="help-block">注：号段排序采取年内排序的方式，最新的年份在前</span>
                                </div>
                                <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                                    <div class="widget-header">
                                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                                                    <label>所属年度</label>
                                                    <div class="input-group" style="width: 120px">
                                                        <input class="form-control date-picker" name="year" type="text"
                                                               data-date-format="yyyy"
                                                                data-date-min-view-mode="2"
                                                               placeholder="选择年份"  value="${param.year}" />
                                                        <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                    </div>
                                                </div>
                                                <div class="clearfix form-actions center">
                                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                                       data-url="${ctx}/applySnRange?cls=${cls}"
                                                       data-target="#page-content"
                                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                    <c:if test="${_query}">&nbsp;
                                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                                data-url="${ctx}/applySnRange?cls=${cls}"
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
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/applySnRange_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '所属年度', name: 'year', frozen:true},
            {label: '编码前缀', name: 'prefix', frozen:true},
            {label: '排序', width: 90, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/applySnRange_changeOrder"}, frozen:true},
            {label: '编码长度', name: 'len'},
            {label: '起始编码', name: 'startSn', width: 180, formatter:function(cellvalue, options, rowObject){
                return $.trim(rowObject.prefix)+ cellvalue.zfill(rowObject.len);
            }},
            {label: '结束编码', name: 'endSn', width: 180, formatter:function(cellvalue, options, rowObject){
                return $.trim(rowObject.prefix) + cellvalue.zfill(rowObject.len);
            }},

            {label: '编码总数', name: '_total', width: 90, formatter:function(cellvalue, options, rowObject){
                  return rowObject.endSn - rowObject.startSn + 1;
            }},
            {label: '已使用<br/>数量', name: 'useCount', width: 90},
            {label: '已作废<br/>数量', name: 'abolishCount', width: 90},
            {label: '剩余数量', name: '_total', width: 90, formatter:function(cellvalue, options, rowObject){
                  return rowObject.endSn - rowObject.startSn + 1 - rowObject.useCount - rowObject.abolishCount;
            }},
            {label: '备注', name: 'remark', align:'left', width: 380}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>