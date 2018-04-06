<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scLetter"
             data-url-export="${ctx}/sc/scLetter_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.type ||not empty param.num || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scLetter:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scLetter_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scLetter_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-url="${ctx}/sc/scLetterReply_au"
                                   data-id-name="letterId"
                                   data-open-by="page"><i class="fa fa-comment"></i> 添加纪委回复情况</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scLetter:del">
                                <button data-url="${ctx}/sc/scLetter_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <%--<a class="jqExportBtn btn btn-success btn-sm tooltip-success"
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
                                            <div class="input-group">
                                                <input required class="form-control date-picker" placeholder="请选择年份"
                                                       name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>函询类型</label>
                                            <div class="input-group">
                                                <select data-rel="select2" name="type" data-placeholder="请选择" data-width="100">
                                                    <option></option>
                                                    <c:import url="/metaTypes?__code=mc_sc_letter_type"/>
                                                </select>
                                                <script type="text/javascript">
                                                    $("#searchForm select[name=type]").val(${param.type});
                                                </script>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>函询编号</label>
                                            <input class="form-control search-query num" name="num" type="text"
                                                   value="${param.num}" style="width: 50px"
                                                   placeholder="请输入函询编号">
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
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
        url: '${ctx}/sc/scLetter_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80},
            {
                label: '函询编号', name: 'num', width: 180, formatter: function (cellvalue, options, rowObject) {
                var _num = _cMap.metaTypeMap[rowObject.type].name+"[{0}]{1}号".format(rowObject.year, rowObject.num)
                return $.swfPreview(rowObject.filePath, rowObject.fileName, _num, _num);
            }, frozen: true
            },
            {label: '函询日期', name: 'queryDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {
                label: '函询对象数', name: 'itemCount', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="popupBtn btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scLetter_items?letterId={0}">{1}</button>')
                        .format(rowObject.id, rowObject.itemCount);
            }
            },
            {
                label: '纪委回复情况', name: 'itemCount', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="loadPage btn btn-link btn-xs" ' +
                'data-url="${ctx}/sc/scLetter?cls=2&letterYear={0}&letterNum={1}">查看({2})</button>')
                        .format( rowObject.year, rowObject.num, rowObject.replyCount);
            }
            },
            {label: '备注', name: 'remark'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>