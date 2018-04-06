<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatter"
             data-url-export="${ctx}/sc/scMatter_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scMatter:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/sc/scMatter_au?type=0"><i
                                        class="fa fa-plus"></i> 集中填报</a>
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/sc/scMatter_au?type=1"><i
                                        class="fa fa-plus"></i> 个别填报</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scMatter_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改信息</a>


                            </shiro:hasPermission>
                            <shiro:hasPermission name="scMatter:del">
                                <button data-url="${ctx}/sc/scMatter_batchDel"
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
                                            <label>年度</label>

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
                                            <label>填报类型</label>
                                            <select data-rel="select2" name="type" data-placeholder="请选择">
                                                <option></option>
                                                <option value="0"> 年度集中填报</option>
                                                <option value="1">个别填报</option>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}')
                                            </script>
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
        url: '${ctx}/sc/scMatter_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'year', width: 80},
            {
                label: '填报类型', name: 'type', width: 130, formatter: function (cellvalue, options, rowObject) {
                return (rowObject.type) ? '个别填报' : '年度集中填报';
            }
            },
            {
                label: '领表时间',
                name: 'drawTime',
                width: 150,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },
            {
                label: '应交回时间',
                name: 'handTime',
                width: 150,
                formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y-m-d H:i'}
            },

            {
                label: '填报对象', name: 'type', width: 130, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-primary btn-xs" ' +
                'data-url="${ctx}/sc/scMatterItem?cls=-1&matterId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }
            },
            {label: '备注', name: 'remark', width: 430}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    $.register.date($('.date-picker'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>