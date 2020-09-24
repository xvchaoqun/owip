<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.status}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                        <shiro:hasPermission name="oaGrid:edit">
                            <c:if test="${cls!=OA_GRID_HASDELETED}">
                            <button class="popupBtn btn btn-info btn-sm"
                                    data-url="${ctx}/oa/oaGrid_au" data-width="1000">
                                <i class="fa fa-plus"></i> 添加</button>
                            </c:if>
                            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                               data-url="${ctx}/oa/oaGrid_au"
                               data-grid-id="#jqGrid" data-width="1000"><i class="fa fa-edit"></i>
                                修改</button>
                            <c:if test="${cls==OA_GRID_HASDELETED}">
                                <button data-url="${ctx}/oa/oaGrid_batchDel?delete=${OA_GRID_USE}"
                                        data-title="返回数据模板"
                                        data-msg="确定将这{0}条数据返回数据模板？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-warning btn-sm">
                                    <i class="fa fa-reply"></i> 返回数据模板
                                </button>
                            </c:if>
                            <c:if test="${cls!=OA_GRID_HASDELETED}">
                                    <button data-url="${ctx}/oa/oaGrid_batchDel?delete=${OA_GRID_HASDELETED}"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                            </c:if>
                            <c:if test="${cls==OA_GRID_HASDELETED}">
                                <button data-url="${ctx}/oa/oaGrid_realDel"
                                        data-title="完全删除"
                                        data-msg="确定完全删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </c:if>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="oaGrid:release">
                            <shiro:lacksPermission name="oaGrid:edit">
                            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                               data-url="${ctx}/oa/oaGrid_au"
                               data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                修改报送信息</button>
                            </shiro:lacksPermission>
                            <c:if test="${cls!=OA_GRID_HASDELETED}">
                                <button class="jqBatchBtn btn btn-success btn-sm"
                                        data-title="下发表格"
                                        data-msg="确定向所有的${_p_partyName}下发这{0}张表格？"
                                        data-url="${ctx}/oa/oaGrid_release"
                                        data-grid-id="#jqGrid"><i class="fa fa-check-circle-o"></i>
                                    下发表格</button>
                            </c:if>
                        </shiro:hasPermission>
                        </div>
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
                                            <label>表格名称</label>
                                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                   placeholder="请输入">
                                        </div>
                                        <%--<div class="form-group">
                                            <label>状态</label>
                                            <select data-rel="select2" name="status" data-placeholder="请选择" data-width="120">
                                                <option></option>
                                                <c:forEach items="${OA_GRID_STATUS_MAP}" var="entry">
                                                    <option value="${entry.key}">${entry.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script> $("#searchForm select[name=status]").val(${param.status}) </script>
                                        </div>--%>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"
                                               data-url="${ctx}/oa/oaGrid?cls=${param.cls}"
                                               data-target="#page-content"
                                               data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-url="${ctx}/oa/oaGrid"
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
                        <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="1"></table>
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
        rownumbers:true,
        url: '${ctx}/oa/oaGrid_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '所属年度',name: 'year',frozen:true},
            <shiro:hasPermission name="oaGrid:edit">
            { label: '类别',name: 'type',formatter: function (cellvalue, options, rowObject) {
                var str = '--';
                if (cellvalue==1){
                    str =  '党统';
                }
                return str;
                },frozen:true},
            </shiro:hasPermission>
            { label: '表格名称',name: 'name',width:252,align:'left',frozen:true},
            {label: '表格模板', name: '_file',width:130, formatter: function (cellvalue, options, rowObject) {
                var str='';
                    if(rowObject.templateFilePath!=undefined){
                        str = '<button href="javascript:void(0)" data-url="${ctx}/oa/oaGrid_preview?id={0}&summary=0"  title="EXCEL文件预览" data-width="1100" data-height="850" class="openUrl btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(rowObject.id)
                            + '&nbsp;<button class="downloadBtn btn btn-xs btn-success" data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button> &nbsp;'
                            .format(rowObject.templateFilePath,rowObject.name);
                        return str;
                    }
                    return '--';
                }},
            {label: '汇总统计', name: '_summary',width:130, formatter: function (cellvalue, options, rowObject) {
                    var str='';
                    if(rowObject.templateFilePath!=undefined){
                        str = '<button href="javascript:void(0)" data-url="${ctx}/oa/oaGrid_preview?id={0}&summary=1"  title="EXCEL文件预览" data-width="1100" data-height="850" class="openUrl btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(rowObject.id)
                            + '&nbsp;<button class="downloadBtn btn btn-xs btn-success" data-url="${ctx}/oa/oaGrid_summaryExport?id={0}"><i class="fa fa-download"></i> 下载</button> &nbsp;'
                                .format(rowObject.id);
                        return str;
                    }
                    return '--';
                }},
            <c:if test="${cls!=3}">
                { label: '状态',name: 'status',formatter:function (cellvalue, options, rowObject) {
                        return _cMap.OA_GRID_STATUS_MAP[cellvalue];
                    }},
            </c:if>
            <shiro:hasPermission name="oaGrid:edit">
            { label: '表格行数',name: 'row',width:70},
            { label: '表格列数',name: 'col',width:70,},
            { label: '数据填报的左上角<br>单元格坐标',name: 'startPos',width:130},
            { label: '数据填报的右下角<br>单元格坐标',name: 'endPos',width:130},
            { label: '只读单元格<br>坐标',name: 'readonlyPos',width:272,align:"left"},
            </shiro:hasPermission>
            { label: '应完成时间',name: 'deadline',width: 130, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'}},
            { label: '填报说明',name: 'content',width:300,align:'left'},
            { label: '联系方式',name: 'contact'},
            { label: '备注',name: 'remark',width:300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));

</script>