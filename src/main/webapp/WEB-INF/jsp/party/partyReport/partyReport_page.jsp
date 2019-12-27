<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=OwConstants.OW_PARTY_EVA_MAP%>" var="OW_PARTY_EVA_MAP"/>
<c:set value="<%=OwConstants.OW_REPORT_STATUS_MAP%>" var="OW_REPORT_STATUS_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.year ||not empty param.partyId||not empty param.status || not empty param.code || not empty param.sort}"/>
            <jsp:include page="/WEB-INF/jsp/member/memberReport/menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="partyReport:base">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/partyReport_au">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button id="editBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/partyReport_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
                    <button id="reportBtn" data-url="${ctx}/partyReport_report"
                            data-title="报送"
                            data-msg="确定报送这{0}条数据？报送后不可修改"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-success btn-sm">
                        <i class="fa fa-hand-paper-o"></i> 报送
                    </button>
                    <shiro:hasRole name="${ROLE_ODADMIN}">
                        <button id="backBtn" data-url="${ctx}/partyReport_report?back=1"
                                data-title="退回"
                                data-msg="确定退回这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-reply"></i> 退回
                        </button>
                    </shiro:hasRole>
                    <button id="delBtn" data-url="${ctx}/partyReport_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                   <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                      data-url="${ctx}/partyReport_data"
                      data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                       <i class="fa fa-download"></i> 导出</button>
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
                                <label>年度</label>
                                <input required class="date-picker"
                                       name="year" type="text"
                                       data-date-start-view="2"
                                       data-date-min-view-mode="2"
                                       data-date-max-view-mode="2"
                                       data-date-format="yyyy"
                                       style="width: 100px"
                                       value="${param.year}"/>
                            </div>
                        <shiro:hasPermission name="memberReport:base">
                            <div class="form-group">
                                <label>所属${_p_partyName}</label>
                                <select required class="form-control" data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                        name="partyId" data-placeholder="请选择${_p_partyName}" data-width="272">
                                    <option value="${party.id}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                <label> 党支部</label>
                                    <select class="form-control"  data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/branch_selects?auth=1"
                                            name="branchId" data-placeholder="请选择党支部" data-width="272">
                                        <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                    </select>
                            </div>
                            <script>
                                $.register.party_branch_select($("#searchForm"), "branchDiv",
                                    '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}",  "${party.classId}" );
                            </script>
                        </shiro:hasPermission>
                            <div class="form-group">
                                <label> 状态</label>
                                <select data-rel="select2" name="status" data-placeholder="请选择" data-width="100">
                                    <option></option>
                                    <c:forEach var="_type" items="${OW_REPORT_STATUS_MAP}">
                                        <option value="${_type.key}">${_type.value}</option>
                                    </c:forEach>
                                </select>
                                <script type="text/javascript">
                                    $("#searchForm select[name=status]").val(${param.status});
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/partyReport"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/partyReport"
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
        url: '${ctx}/partyReport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                    if (cellvalue == '<%=OwConstants.OW_REPORT_STATUS_UNREPORT%>') {
                        return '<span class="text-danger">未报送</span>'
                    }
                    if (cellvalue ==<%=OwConstants.OW_REPORT_STATUS_REPORT%>) {
                        return '<span class="text-success">已报送</span>'
                    }
                }
            },
            {label: '年度', name: 'year'},
            { label: '所属${_p_partyName}', name: 'partyId',align:'left', width: 300 ,  formatter:function(cellvalue, options, rowObject){
                    return $.party(rowObject.partyId);
                }},
            { label: '党支部',  name: 'branchId',align:'left', width: 300,formatter:function(cellvalue, options, rowObject){

                    return $.party(null, rowObject.branchId);
                }, frozen:true },
            {
                label: '工作总结', name: 'reportFile',width: 120 , formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '<button class="popupBtn btn btn-xs btn-primary"' +
                        'data-url="${ctx}/partyReport_file?id={0}"><i class="fa fa-edit"></i> 修改</button>'
                            .format(rowObject.id);
                    if (rowObject.status == '<%=OwConstants.OW_REPORT_STATUS_UNREPORT%>') {
                    return '<button class="downloadBtn btn btn-info btn-xs" ' +
                        'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>&nbsp<button class="popupBtn btn btn-xs btn-primary"' +
                        'data-url="${ctx}/partyReport_file?id={2}"><i class="fa fa-edit"> 修改</i></button>'
                            .format(cellvalue, "工作总结(" +  rowObject.party.name + ")",rowObject.id);
                    }
                    if (rowObject.status == '<%=OwConstants.OW_REPORT_STATUS_REPORT%>') {
                        return '<button class="downloadBtn btn btn-info btn-xs" ' +
                            'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>'
                                .format(cellvalue, "工作总结(" +  rowObject.party.name + ")");
                    }
                }
            },
            {
                label: '考核结果', name: 'evaResult', frozen: true, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '-'
                    return _cMap.OW_PARTY_EVA_MAP[cellvalue];
                }
            },
            {
                label: '考核结果文件', name: 'evaFile', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                    return '<button class="downloadBtn btn btn-info btn-xs" ' +
                        'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>'
                            .format(cellvalue, "考核结果(" + rowObject.party.name + ")")
                }
            },
            {label: '备注', name: 'remark', width: 300, align: 'left'},
            {hidden: true, name: 'status'}
        ],
        onSelectRow: function (id, status) {

            _onSelectRow(this);
        },
        onSelectAll: function (aRowids, status) {

            _onSelectRow(this)
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");

    function _onSelectRow(grid) {
        <shiro:lacksRole name="${ROLE_ODADMIN}">
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length == 1) {
            var rowData = $(grid).getRowData(ids[0]);
            if (rowData.status == '2') {
                $("#editBtn").prop("disabled", true);
                $("#delBtn").prop("disabled", true);
                $("#reportBtn").prop("disabled", true);
            }else{
                $("#editBtn").removeAttr("disabled");
                $("#delBtn").removeAttr("disabled");
                $("#reportBtn").removeAttr("disabled");
            }
        }
        </shiro:lacksRole>
    }

   // $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [name="status"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>