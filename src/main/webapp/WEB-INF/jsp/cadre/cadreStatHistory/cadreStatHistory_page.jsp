<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CADRE_STAT_HISTORY_TYPE_MAP" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_MAP%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_STAT_CADRE_KJ" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CADRE_KJ%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_STAT_CPC_KJ" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_KJ%>"/>
<c:set var="CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT_KJ" value="<%=CadreConstants.CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT_KJ%>"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content"
             class="myTableDiv"
             data-url-page="${ctx}/cadreStatHistory"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type || not empty param._statDate}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cadreStatHistory:edit">
                    <select data-rel="select2" name="type" for="search" data-placeholder="请选择统计类型">
                        <option></option>
                        <c:forEach var="_type" items="${CADRE_STAT_HISTORY_TYPE_MAP}">
                            <c:if test="${_p_hasKjCadre ||
                            !(_type.key==CADRE_STAT_HISTORY_TYPE_STAT_CADRE_KJ ||
                            _type.key==CADRE_STAT_HISTORY_TYPE_STAT_CPC_KJ ||
                            _type.key==CADRE_STAT_HISTORY_TYPE_STAT_CPC_STAT_KJ)}">
                                <option value="${_type.key}">${_type.value}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <button class="btn btn-primary btn-sm"
                            onclick="statNow(this)"
                            data-url="#"
                            data-title="统计"
                            data-msg="确定现在统计？"
                            data-loading="#gview_jqGrid"
                            data-callback="_reload">
                        <i class="fa fa-hourglass-half"></i> 立即统计
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm"
                    data-grid-id="#jqGrid"
                    data-search-form-id="#searchForm"
                    data-url="${ctx}/cadreStatHistory_data"><i class="fa fa-download"></i>
                    打包下载</button>
                <shiro:hasPermission name="cadreStatHistory:del">
                    <a class="jqBatchBtn btn btn-danger btn-sm"
                       data-url="${ctx}/cadreStatHistory_batchDel" data-title="删除"
                       data-msg="确定删除这{0}条记录吗？"><i class="fa fa-trash"></i> 删除</a>
                </shiro:hasPermission>
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
                                <label>类型</label>
                                <select data-rel="select2" name="type" data-placeholder="请选择类型">
                                    <option></option>
                                    <c:forEach var="_type" items="${CADRE_STAT_HISTORY_TYPE_MAP}">
                                        <option value="${_type.key}">${_type.value}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=type]").val('${param.type}');
                                </script>
                            </div>
                            <div class="form-group">
                                <label>统计日期</label>

                                <div class="input-group tooltip-success" data-rel="tooltip" title="统计日期范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                    <input placeholder="请选择统计日期范围" data-rel="date-range-picker"
                                           class="form-control date-range-picker"
                                           type="text" name="_statDate" value="${param._statDate}"/>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <%--<div class="space-4"></div>--%>
            <table id="jqGrid" class="jqGrid"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    function _reload() {
        $("#modal").modal('hide');
        $(window).resize();
    }

    function statNow(btn){

        var type = $("select[name=type][for=search]").val();
        if($.trim(type)==''){
            SysMsg.info("请选择统计类型");
            return;
        }
        $(btn).data("msg", "确定现在统计【{0}】？".format($("select[name=type][for=search] option:checked").text()))
        $(btn).data("url", "${ctx}/cadreStatHistory?type=" + type);
        $.confirm(btn);
    }
    $("#jqGrid").jqGrid({
        url: '${ctx}/cadreStatHistory_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '类型',
                name: 'type',
                width: 250,
                frozen: true,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                    return _cMap.CADRE_STAT_HISTORY_TYPE_MAP[cellvalue];
                }
            },
            {label: '统计日期', name: 'statDate', frozen: true, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '统计耗时(ms)', name: 'duration', frozen: true},
            {label: '统计时间', name: 'createTime', width: 150},
            {label: '下载', name: '_downloadCount', formatter: function (cellvalue, options, rowObject) {

                var filename = "${_school}" + _cMap.CADRE_STAT_HISTORY_TYPE_MAP[rowObject.type] +
                        "（"+rowObject.statDate.substr(0, 10)+"）"
                        + ".xlsx";
                return '<a href="${ctx}/attach_download?path={0}&filename={1}">下载</a>'
                        .format(encodeURI(rowObject.savePath), encodeURI(filename));
            }}
        ]
    }).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('[data-rel="select2"]').select2();
</script>