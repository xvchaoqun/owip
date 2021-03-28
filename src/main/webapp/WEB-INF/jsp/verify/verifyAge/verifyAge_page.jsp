<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/verifyAge"
             data-url-export="${ctx}/verifyAge_data"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type || not empty param.cadreId || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="/WEB-INF/jsp/verify/verify_menu.jsp"/>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="verifyAge:edit">
                                <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/verifyAge_au"><i
                                        class="fa fa-plus"></i> 添加认定</a>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="verifyAge:del">
                                <button data-url="${ctx}/verifyAge_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <button class="jqOpenViewBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/verifyAgeLog"
                                    data-open-by="page">
                                <i class="fa fa-search"></i> 认定记录
                            </button>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
                        </div>
                        <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                            <div class="widget-header">
                                <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                                <div class="widget-toolbar">
                                    <a href="javascript:;" data-action="collapse">
                                        <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                                    </a>
                                </div>
                            </div>
                            <div class="widget-body">
                                <div class="widget-main no-padding">
                                    <form class="form-inline search-form" id="searchForm">
                                        <input type="hidden" name="cls" value="${cls}">
                                        <div class="form-group">
                                            <label>选择干部</label>
                                            <select data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/cadre_selects"
                                                    name="cadreId" data-placeholder="请输入账号或姓名或学工号"  data-width="270">
                                                <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                            </select>
                                            <label>认定类别</label>
                                            <select name="type" data-rel="select2" data-placeholder="请选择" data-width="270">
                                                <option></option>
                                                <c:forEach items="<%=VerifyConstants.VERIFY_AGE_TYPE_MAP%>" var="entity">
                                                    <option value="${entity.key}">${entity.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script>
                                                $("#searchForm select[name=type]").val('${param.type}');
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="cls=${cls}">
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
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $("#jqGrid").jqGrid({
        url: '${ctx}/verifyAge_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
            {
                label: '姓名', name: 'cadre.realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadre.id, cellvalue);
            }, frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
            {label: '认定类别', name: 'type', width: 200, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--';
                return _cMap.VERIFY_AGE_TYPE_MAP[cellvalue]
            }},
            {label: '认定前日期', name: 'oldBirth',formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m.d'}},
            {label: '认定后日期', name: 'verifyBirth',formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m.d'}},
            {label: '认定', name: '_verify', formatter: function (cellvalue, options, rowObject) {

                var hasVerify = ($.trim(rowObject.verifyBirth)!='');
                return '<button class="openView btn {1} btn-xs" data-url="${ctx}/verifyAge_verify?id={0}"><i class="fa {2}"></i> {3}</button>'
                        .format(rowObject.id, hasVerify?'btn-primary':'btn-success', hasVerify?'fa-search':'fa-check', hasVerify?'查看':'认定');
            }},
            {label: '备注', name: 'remark', width: 500}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>