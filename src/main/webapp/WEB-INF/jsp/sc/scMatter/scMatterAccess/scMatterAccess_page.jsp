<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/sc/scMatterAccess"
             data-url-export="${ctx}/sc/scMatterAccess_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.isCopy || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="scMatterAccess:edit">
                                <a class="openView btn btn-info btn-sm" data-url="${ctx}/sc/scMatterAccess_au"><i
                                        class="fa fa-plus"></i> 添加</a>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/sc/scMatterAccess_au"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                                <a class="jqOpenViewBtn btn btn-success btn-sm"
                                   data-url="${ctx}/sc/scMatterAccess_query"
                                   data-grid-id="#jqGrid"
                                   data-open-by="page"
                                   data-querystr="&"><i class="fa fa-search"></i>
                                    办理调阅</a>
                                <button id="backBtn" class="jqOpenViewBtn btn btn-warning btn-sm"
                                        data-url="${ctx}/sc/scMatterAccess_back"
                                        data-grid-id="#jqGrid"
                                        data-width="400"
                                        data-querystr="&"><i class="fa fa-reply"></i>
                                    归还
                                </button>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="scMatterAccess:del">
                                <button data-url="${ctx}/sc/scMatterAccess_batchDel"
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
                                            <label>调阅类型</label>
                                            <select required data-rel="select2"
                                                    name="isCopy" data-placeholder="请选择" data-width="140">
                                                <option></option>
                                                <option value="0">原件</option>
                                                <option value="1">复印件</option>
                                            </select>
                                            <script>
                                                <c:if test="${not empty param.isCopy}">
                                                $("#searchForm select[name=isCopy]").val('${param.isCopy}')
                                                </c:if>
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
        <div id="item-content"></div>
    </div>
</div>
<jsp:include page="scMatterAccess_colModel.jsp?cls=${param.cls}"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/sc/scMatterAccess_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:colModel,
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length > 1) {
            $("#backBtn").prop("disabled", true);
        } else if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            var isCopy = (rowData.isCopy == "true");

            $("#backBtn").prop("disabled", isCopy);
        }
    }

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });
</script>