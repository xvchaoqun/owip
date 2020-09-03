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
                   value="${not empty param.year ||not empty param.partyId ||not empty param.userId || not empty param.status|| not empty param.code || not empty param.sort}"/>
            <jsp:include page="menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
            <shiro:hasPermission name="memberReport:base">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/member/memberReport_au">
                        <i class="fa fa-plus"></i> 添加
                    </button>

                    <button id="editBtn" class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/member/memberReport_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>

                    <button id="reportBtn" data-url="${ctx}/member/memberReport_report"
                            data-title="报送"
                            data-msg="确定报送这{0}条数据？报送后不可修改"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-success btn-sm">
                        <i class="fa fa-hand-paper-o"></i> 报送
                    </button>

                    <shiro:hasRole name="${ROLE_ODADMIN}">
                        <button id="backBtn" data-url="${ctx}/member/memberReport_report?back=1"
                                data-title="退回"
                                data-msg="确定退回这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm">
                            <i class="fa fa-reply"></i> 退回
                        </button>
                    </shiro:hasRole>

                    <button id="delBtn" data-url="${ctx}/member/memberReport_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>

                    <button class="popupBtn btn btn-success btn-sm tooltip-info"
                            data-url="${ctx}/member/memberReport_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                </shiro:hasPermission>
                  <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                     data-url="${ctx}/member/memberReport_data"
                     data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                      <i class="fa fa-download"></i> 导出</button>

                <shiro:hasRole name="${ROLE_SUPER}">
                    <button data-url="${ctx}/member/memberReport_delFile"
                            data-title="清除考核结果"
                            data-msg="确定清除这{0}条数据考核结果？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 清除考核结果
                    </button>
                </shiro:hasRole>

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
                            <div class="form-group" <%--id="userIdDiv" style="${(empty memberReport.userId)?'display: none':''}"--%>>
                                <label>姓名</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member/pb_member_selects"
                                        name="userId" data-width="272" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${user.userId}">${user.realname}-${user.code}-${user.unit}</option>
                                </select>
                            </div>
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
                                   data-url="${ctx}/member/memberReport"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/member/memberReport"
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
        url: '${ctx}/member/memberReport_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
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
            {label: '学工号', name: 'user.code'},
            {label: '姓名', name: 'user.realname'},
            {label: '所属${_p_partyName}', name: 'party.name', algin: "left", width: 400},
            {
                label: '述职报告', name: 'reportFile', width: 120, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '<button class="popupBtn btn btn-xs btn-primary"' +
                        'data-url="${ctx}/member/memberReport_file?id={0}"><i class="fa fa-edit"></i> 修改</button>'
                            .format(rowObject.id);
                    if (rowObject.status == '<%=OwConstants.OW_REPORT_STATUS_UNREPORT%>') {
                        return '<button class="downloadBtn btn btn-info btn-xs" ' +
                            'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>'
                                .format(cellvalue, "述职报告(" + rowObject.user.realname + ")")+'&nbsp'+'<button class="popupBtn btn btn-xs btn-primary"' +
                        'data-url="${ctx}/member/memberReport_file?id={0}"><i class="fa fa-edit"> 修改</i></button>'.format(rowObject.id);
                    }
                    if (rowObject.status == '<%=OwConstants.OW_REPORT_STATUS_REPORT%>') {
                        return '<button class="downloadBtn btn btn-info btn-xs" ' +
                            'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>'
                                .format(cellvalue, "述职报告(" + rowObject.user.realname + ")");
                    }
                }
            },
            {
                label: '考核结果', name: 'evaResult',width: 120, frozen: true, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '<button class="popupBtn btn btn-xs btn-success"' +
                    'data-url="${ctx}/member/memberReport_result?id={0}"><i class="fa fa-plus"></i> 添加</button>'
                        .format(rowObject.id);
                    if (rowObject.status == '<%=OwConstants.OW_REPORT_STATUS_UNREPORT%>') {
                    return _cMap.OW_PARTY_EVA_MAP[cellvalue]+'&nbsp<button class="popupBtn btn btn-xs btn-primary"' +
                        'data-url="${ctx}/member/memberReport_result?id={0}"><i class="fa fa-edit"></i> 修改</button>'
                            .format(rowObject.id);
                    }
                    if (rowObject.status == '<%=OwConstants.OW_REPORT_STATUS_REPORT%>') {
                        return _cMap.OW_PARTY_EVA_MAP[cellvalue];
                    }

                }
            },
            {
                label: '考核结果文件', name: 'evaFile', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                    return '<button class="downloadBtn btn btn-info btn-xs" ' +
                        'data-url="${ctx}/attach_download?path={0}&filename={1}"><i class="fa fa-download"></i> 下载</button>'
                            .format(cellvalue, "考核结果(" + rowObject.user.realname + ")")
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
    /*$("#searchForm select[name=partyId]").change(function () {
        var partyId = $("#searchForm select[name=partyId]").val();
        if ($.isBlank(partyId)) {
            $("#searchForm select[name=userId]").val(null).trigger("change");
            $("#userIdDiv").hide();
            return;
        }
        $('#searchForm select[name="userId"]').data('ajax-url', "${ctx}/member/pb_member_selects?partyId=" + partyId);
        $.register.user_select($("#searchForm select[name=userId]"));
        $("#userIdDiv").show();
    });*/
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>