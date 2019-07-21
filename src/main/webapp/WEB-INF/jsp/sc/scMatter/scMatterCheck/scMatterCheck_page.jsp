<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatterCheck"
             data-url-export="${ctx}/sc/scMatterCheck_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year || not empty param.isRandom || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scMatterCheck:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scMatterCheck_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scMatterCheck_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scMatterCheck:del">
                                <button data-url="${ctx}/sc/scMatterCheck_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                           <%-- <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>--%>
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
                                        <input type="hidden" name="cls" value="${cls}">

                                        <div class="form-group">
                                            <label>年份</label>

                                            <div class="input-group" style="width: 150px">
                                                <input class="form-control date-picker" placeholder="请选择年份"
                                                       name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>核查类型</label>
                                            <select data-rel="select2"
                                                    name="isRandom" data-placeholder="请选择" data-width="240">
                                                <option></option>
                                                <option value="1">年度随机抽查</option>
                                                <option value="0">重点抽查</option>
                                            </select>
                                            <script>
                                                <c:if test="${not empty param.isRandom}">
                                                $("#searchForm select[name=isRandom]").val('${param.isRandom}')
                                                </c:if>
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
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
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scMatterCheck_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {
                label: '核查编号', name: 'num', width: 200, formatter: function (cellvalue, options, rowObject) {
                var _num = "个人事项核查〔{0}〕{1}号".format(rowObject.year, rowObject.num)
                return $.swfPreview(rowObject.checkFile, rowObject.checkFileName, _num, _num);
            }, frozen: true
            },
            {label: '核查日期', name: 'checkDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                label: '核查类型', name: 'isRandom', width: 130, formatter: function (cellvalue, options, rowObject) {
                return (rowObject.isRandom) ? '年度随机抽查' : '重点抽查';
            }
            },
            {
                label: '核查对象数', name: 'itemCount', formatter: function (cellvalue, options, rowObject) {
                return ('<a href="javascript:;" class="popupBtn" ' +
                'data-url="${ctx}/sc/scMatterCheck_items?checkId={0}">{1}</a>')
                        .format(rowObject.id, rowObject.itemCount);
            }
            },
            {label: '完成核查人数', name: 'itemCheckCount',width: 110,},
            {label: '未完成核查人数', name: '_itemUncheckCount', width: 120, formatter: function (cellvalue, options, rowObject) {
                return rowObject.itemCount - rowObject.itemCheckCount;
            }
            },
            {
                label: '核查结果及处理', width: 130, formatter: function (cellvalue, options, rowObject) {

                return '<a href="#/sc/scMatterCheck?cls=2&year={0}&num={1}" target="_blank">查看</a>'
                        .format(rowObject.year, rowObject.num);
            }},
            {
                label: '核查文件', width: 80, formatter: function (cellvalue, options, rowObject) {
                    if($.trim(rowObject.files)=='') return '--'

                return ('<button data-url="${ctx}/sc/scMatterCheck_files?id={0}" class="popupBtn btn btn-xs btn-primary">'
                    +'<i class="fa fa-search"></i>  查看</button>')
                        .format(rowObject.id);
            }
            },
            {label: '备注', name: 'remark', width: 280}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>