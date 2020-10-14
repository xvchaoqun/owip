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
                                <shiro:hasPermission name="oaGrid:release">
                                    <button data-url="${ctx}/oa/oaGridParty_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？（删除后不可恢复，请谨慎操作！）"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
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
                                                <label>所属年度</label>
                                                <div class="input-group" style="width: 120px">
                                                    <input class="form-control date-picker" name="year" type="text"
                                                           data-date-format="yyyy"
                                                            data-date-min-view-mode="2"
                                                           placeholder="选择年份"  value="${param.year}" />
                                                    <span class="input-group-addon"> <i class="fa fa-calendar bigger-110"></i></span>
                                                </div>
                                            </div>
                                        <div class="form-group">
                                            <label>表格名称</label>
                                            <input class="form-control search-query" name="gridName" type="text" value="${param.gridName}"
                                                   placeholder="请输入所属表格模板">
                                        </div>

                                        <div class="form-group">
                                            <label>所属${_p_partyName}</label>
                                            <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                    data-ajax-url="${ctx}/party_selects?auth=1"
                                                    name="partyId" data-placeholder="请选择">
                                                <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                            </select>
                                        </div>

                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"
                                                   data-url="${ctx}/oa/oaGridParty?cls=${cls}"
                                                   data-target="#page-content"
                                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                <c:if test="${_query}">&nbsp;
                                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                            data-url="${ctx}/oa/oaGridParty?cls=${cls}"
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
    
                        return ('<button class="jqBatchBtn btn btn-success btn-xs" data-width="800" title="{2}" data-title="报送" data-msg="确定报送？（报送后不可修改，请核对数据准确无误后报送）"' +
                            'data-url="${ctx}/oa/oaGridParty_report?report=2&id={0}" {1}><i class="fa fa-hand-paper-o"></i> 报送</button>')
                            .format(rowObject.id, $.trim(rowObject.reportMsg)==''? '' : 'disabled', $.trim(rowObject.reportMsg));
                    }, frozen:true},
            </c:if>
            <c:if test="${cls!=OA_GRID_PARTY_REPORT}">
                { label: '填报',name: '_upload', width:80, formatter: function (cellvalue, options, rowObject) {
                        var hasUpload = (rowObject.excelFilePath!=null);
                        return ('<button class="jqOpenViewBtn btn {1} btn-xs" data-url="${ctx}/oa/oaGridParty_au" data-open-by="page" ' +
                            ' data-grid-id="#jqGrid">{0}</button>')
                            .format(!hasUpload?'<i class="fa fa-upload"></i> 填报':'<i class="fa fa-edit"></i> 修改', !hasUpload?'btn-primary':'btn-info');
                    }, frozen:true},
            </c:if>
            { label: '所属年度',name: 'year',frozen:true},
            { label: '表格名称',name: 'gridName',align:'left', width: 252,frozen:true,formatter: function (cellvalue, options, rowObject) {

                return '<a href="javascript:void(0)" data-url="${ctx}/oa/oaGridParty_preview?id={0}&tpl=1"  title="表格模板预览" data-width="1100" data-height="850" class="openUrl"><i class="fa fa-search"></i> {1}</button>'
                        .format(rowObject.id,rowObject.gridName)
                }},
            { label: '已上传<br/>数据文件预览',name: '_excelFilePath',width:150, formatter: function (cellvalue, options, rowObject) {
                var str='';
                if(rowObject.excelFilePath!=undefined){
                    str = '<button href="javascript:void(0)" data-url="${ctx}/oa/oaGridParty_preview?id={0}"  title="已上传数据文件预览" data-width="1100" data-height="850" class="openUrl btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                            .format(rowObject.id)
                        + '&nbsp;<button class="downloadBtn btn btn-xs btn-success" data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button> &nbsp;'
                            .format(rowObject.excelFilePath,rowObject.gridName);
                    return str;
                }
                return '--';
                }},
            { label: '提交后台的<br/>数据',name: '_excelFilePath',width:80, formatter: function (cellvalue, options, rowObject) {
                    var str='';
                    if(rowObject.excelFilePath!=undefined){
                        return '<button href="javascript:void(0)" data-url="${ctx}/oa/oaGridParty_preview?id={0}&isSave=1"  title="预览提交后台的数据" data-width="1100" data-height="850" class="openUrl btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(rowObject.id);
                    }
                    return '--';
                }},
            { label: '已上传<br/>签字文件',name: '_filePath',width:80, formatter: function (cellvalue, options, rowObject) {

                if(rowObject.excelFilePath==undefined) return '--'
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
            { label: '所属${_p_partyName}', name: 'partyName',align:'left', width: 350},
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
    $.register.date($('.date-picker'));
</script>