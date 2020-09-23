<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="../constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.gridName ||not empty param.year ||not empty param.partyId ||not empty param.status}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="oaGridParty:edit">
                                    <%--<button class="jqOpenViewBtn btn btn-primary btn-sm"
                                       data-url="${ctx}/oa/oaGridParty_au" data-open-by="page"
                                       data-grid-id="#jqGrid"><i class="fa fa-upload"></i>
                                        上传文件</button>--%>
                                    <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_SUPER},${ROLE_ODADMIN}">
                                        <c:if test="${cls==OA_GRID_PARTY_REPORT}">
                                            <button data-url="${ctx}/oa/oaGridParty_report?report=${OA_GRID_PARTY_BACK}"
                                                    data-title="退回"
                                                    data-msg="确定退回这{0}条数据？"
                                                    data-grid-id="#jqGrid"
                                                    class="jqOpenViewBatchBtn btn btn-warning btn-sm">
                                                <i class="fa fa-reply"></i> 退回
                                            </button>
                                        </c:if>
                                    </shiro:hasAnyRoles>
                                </shiro:hasPermission>
                                <button class="jqOpenViewBtn btn btn-info btn-sm"
                                        data-url="${ctx}/sysApprovalLog"
                                        data-querystr="&type=<%=SystemConstants.SYS_APPROVAL_LOG_TYPE_OA_GRID_PARTY%>"
                                        data-open-by="page">
                                    <i class="fa fa-search"></i> 操作记录
                                </button>
                                <shiro:hasAnyRoles name="${ROLE_ADMIN},${ROLE_SUPER},${ROLE_ODADMIN}">
                                    <c:if test="${cls!=OA_GRID_PARTY_REPORT}">
                                        <shiro:hasPermission name="oaGridParty:del">
                                            <button data-url="${ctx}/oa/oaGridParty_batchDel"
                                                    data-title="删除"
                                                    data-msg="确定删除这{0}条数据？"
                                                    data-grid-id="#jqGrid"
                                                    class="jqBatchBtn btn btn-danger btn-sm">
                                                <i class="fa fa-trash"></i> 删除
                                            </button>
                                        </shiro:hasPermission>
                                    </c:if>
                                </shiro:hasAnyRoles>
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
                                            <label>所属表格模板</label>
                                            <input class="form-control search-query" name="gridName" type="text" value="${param.gridName}"
                                                   placeholder="请输入所属表格模板">
                                        </div>
                                        <div class="form-group">
                                            <label>所属年度</label>
                                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                                   placeholder="请输入所属年度">
                                        </div>
                                        <div class="form-group">
                                            <label>所在${_p_partyName}</label>
                                            <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/party_selects?auth=1"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>状态</label>
                                            <select data-rel="select2" name="status" data-placeholder="请选择" data-width="120">
                                                <option></option>
                                                <c:forEach items="${OA_GRID_PARTY_STATUS_MAP}" var="entry">
                                                    <option value="${entry.key}">${entry.value}</option>
                                                </c:forEach>
                                            </select>
                                            <script> $("#searchForm select[name=status]").val(${param.status}) </script>
                                        </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/oa/oaGridParty"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/oa/oaGridParty"
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
        url: '${ctx}/oa/oaGridParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            <c:if test="${cls!=OA_GRID_PARTY_REPORT}">
                { label: '报送',name: '_report', width:80, formatter: function (cellvalue, options, rowObject) {
                        if (rowObject.status==${OA_GRID_PARTY_REPORT}) return '<span class="text-success">已报送</span>';
    
                        return ('<button class="jqBatchBtn btn btn-success btn-xs" data-width="800" title="{2}" data-title="报送" data-msg="确定报送该条数据？"' +
                            'data-url="${ctx}/oa/oaGridParty_report?report=2&id={0}" {1}><i class="fa fa-hand-paper-o"></i> 报送</button>')
                            .format(rowObject.id, $.trim(rowObject.reportMsg)==''? '' : 'disabled', $.trim(rowObject.reportMsg));
                    }, frozen:true},
            </c:if>
            <c:if test="${cls!=OA_GRID_PARTY_REPORT}">
                { label: '填报',name: '_upload', width:80, formatter: function (cellvalue, options, rowObject) {
                        return ('<button class="jqOpenViewBtn btn btn-info btn-xs" data-url="${ctx}/oa/oaGridParty_au" data-open-by="page" ' +
                            ' data-grid-id="#jqGrid">{0}</button>').format(rowObject.excelFilePath==null?'<i class="fa fa-upload"></i> 填报':'<i class="fa fa-edit"></i> 修改');
                    }, frozen:true},
            </c:if>
            { label: '所属年度',name: 'year',frozen:true},
            { label: '表格名称（点击下载模板）',name: 'gridName',align:'left', width: 252,frozen:true,formatter: function (cellvalue, options, rowObject) {
                var path = '';
                $.each(${cm:toJSONArray(oaGridList)}, function (i, grid) {
                    if (grid.id==rowObject.gridId){
                        path = grid.templateFilePath;
                    } 
                })
                return ('<a href="${ctx}/attach_download?path={1}&filename={0}">{0}</a>')
                        .format(rowObject.grid.name,path)
                }},
            { label: '报送文件预览',name: '_excelFilePath',width:150, formatter: function (cellvalue, options, rowObject) {
                var str='';
                if(rowObject.excelFilePath!=undefined){
                    str = '<button href="javascript:void(0)" data-url="${ctx}/oa/oaGridParty_preview?id={0}"  title="EXCEL文件预览" data-width="1100" data-height="850" class="openUrl btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                            .format(rowObject.id)
                        + '&nbsp;<button class="downloadBtn btn btn-xs btn-success" data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button> &nbsp;'
                            .format(rowObject.excelFilePath,rowObject.gridName);
                    return str;
                }
                return '--';
                }},
            { label: '签字文件',name: '_filePath',width:80, formatter: function (cellvalue, options, rowObject) {

                    return '<button class="popupBtn btn btn-info btn-xs" data-width="500"' +
                        'data-url="${ctx}/oa/oaGridParty_files?id={0}"><i class="fa fa-search"></i> 查看</button>'
                            .format(rowObject.id)
                }},
            <c:if test="${cls!=OA_GRID_PARTY_REPORT}">
            { label: '状态',name: 'status',width:90,formatter: function (cellvalue, options, rowObject) {
                if (rowObject.status==${OA_GRID_PARTY_BACK})
                    return '<button class="popupBtn btn btn-info btn-xs" data-width="500"' +
                        'data-url="${ctx}/oa/oaGridParty_backReason?id={0}"><i class="fa fa-search"></i> 已退回</button>'
                            .format(rowObject.id)
                return _cMap.OA_GRID_PARTY_STATUS_MAP[cellvalue];
                }},
            </c:if>
            { label: '报送${_p_partyName}名称', name: 'partyName',align:'left', width: 350},
            <c:if test="${cls==OA_GRID_PARTY_REPORT}">
                { label: '报送人',name: 'user.realname'},
                { label: '报送时间',name: 'reportTime',width:130, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'}}
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>