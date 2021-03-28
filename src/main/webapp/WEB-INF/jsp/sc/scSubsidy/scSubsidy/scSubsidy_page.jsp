<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year||not empty param.hrType ||not empty param.hrNum
                    ||not empty param.feType ||not empty param.feNum
                   ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <button class="popupBtn btn btn-success btn-sm" data-width="800"
                        data-url="${ctx}/sc/scSubsidy_au">
                    <i class="fa fa-files-o"></i> 生成干部津贴变动文件
                </button>
                <button class="jqOpenViewBtn btn btn-primary btn-sm" data-width="800"
                        data-url="${ctx}/sc/scSubsidy_au">
                    <i class="fa fa-edit"></i>  修改
                </button>
                <shiro:hasPermission name="scSubsidy:del">
                    <button data-url="${ctx}/sc/scSubsidy_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sc/scSubsidy_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                                <label>年份</label>
                                <div class="input-group">
                                                    <span class="input-group-addon"> <i
                                                            class="fa fa-calendar bigger-110"></i></span>
                                    <input class="form-control date-picker" style="width: 80px;" autocomplete="off" placeholder="请选择年份"
                                           name="year" type="text"
                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                           value="${param.year}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>发人事处通知文号</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/annualType_selects?module=<%=SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY%>"
                                        name="hrType" data-placeholder="请选择文号" data-width="150">
                                    <option value="${hrAnnualType.id}">${hrAnnualType.name}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>发人事处通知编号</label>
                                <input class="form-control num" type="text" name="hrNum" style="width: 50px" value="${param.hrNum}">
                            </div>
                            <div class="form-group">
                                <label>发财经处通知文号</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/annualType_selects?module=<%=SystemConstants.ANNUAL_TYPE_MODULE_SUBSIDY%>"
                                        name="feType" data-placeholder="请选择文号" data-width="150">
                                    <option value="${feAnnualType.id}">${feAnnualType.name}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>发财经处通知编号</label>
                                <input class="form-control num" type="text" name="feNum" style="width: 50px" value="${param.feNum}">
                            </div>
                            <%--<div class="form-group">
                                <label>文号</label>
                                <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                       placeholder="请输入文号">
                            </div>--%>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scSubsidy"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scSubsidy"
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/sc/scSubsidy_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year',width:80},
            {label: '通知日期', name: 'infoDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                label: '发人事处通知', name: '_hr', width:350, formatter: function (cellvalue, options, rowObject) {
                    if(rowObject.hrCode==undefined) return '--'

                    var hasFeedback = ($.trim(rowObject.hrFilePath)!='');
                return ('{0} <button class="downloadBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/sc/scSubsidy_export?fileType=1&id={1}"><i class="fa fa-download"></i> 生成</button>' +
                ' <button class="openView btn {3} btn-xs" ' +
                'data-url="${ctx}/sc/scSubsidy_upload?fileType=1&id={1}"><i class="fa {4}"></i> {2}</button>')
                        .format(rowObject.hrCode, rowObject.id,
                            hasFeedback?'预览':'反馈', hasFeedback?'btn-success':'btn-primary', hasFeedback?'fa-search':'fa-edit')
            }
            },
            {
                label: '发财经处通知', name: '_fe', width: 350, formatter: function (cellvalue, options, rowObject) {

                    if(rowObject.feCode==undefined) return '--'

                    var hasFeedback = ($.trim(rowObject.feFilePath)!='');
                return ('{0} <button class="downloadBtn btn btn-warning btn-xs" ' +
                'data-url="${ctx}/sc/scSubsidy_export?fileType=2&id={1}"><i class="fa fa-download"></i> 生成</button>' +
                ' <button class="openView btn {3} btn-xs" ' +
                'data-url="${ctx}/sc/scSubsidy_upload?fileType=2&id={1}"><i class="fa {4}"></i> {2}</button>')
                        .format(rowObject.feCode, rowObject.id,
                            hasFeedback?'预览':'反馈', hasFeedback?'btn-success':'btn-primary', hasFeedback?'fa-search':'fa-edit')
            }
            },
            {
                label: '包含干部任免文件',
                name: '_dispatches',
                width: 150,
                formatter: function (cellvalue, options, rowObject) {

                    return ('<button class="loadPage btn btn-primary btn-xs" ' +
                    'data-url="${ctx}/sc/scSubsidy?cls=3&year={0}&hrType={1}&hrNum={2}&feType={3}&feNum={4}"><i class="fa fa-search"></i> 查看</button>')
                            .format(rowObject.year, $.trim(rowObject.hrType), $.trim(rowObject.hrNum),
                            $.trim(rowObject.feType), $.trim(rowObject.feNum));
                }
            },
            {
                label: '包含干部',
                name: '_cadres',
                width: 90,
                formatter: function (cellvalue, options, rowObject) {

                    return ('<button class="loadPage btn btn-primary btn-xs" ' +
                    'data-url="${ctx}/sc/scSubsidy?cls=2&year={0}&hrType={1}&hrNum={2}&feType={3}&feNum={4}"><i class="fa fa-search"></i> 查看</button>')
                            .format(rowObject.year, $.trim(rowObject.hrType), $.trim(rowObject.hrNum),
                            $.trim(rowObject.feType), $.trim(rowObject.feNum));
                }
            },
            {label: '备注', name: 'remark', width: 300, align: 'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
    $.register.dispatchType_select($('#searchForm select[name=hrType]'), $("#searchForm input[name=year]"));
    $.register.dispatchType_select($('#searchForm select[name=feType]'), $("#searchForm input[name=year]"));
</script>