<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.id ||not empty param.userId || not empty param.code || not empty param.partyId}"/>
                <div class="tabble">
                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                        <li class="active">
                            <a href="javascript:;" class="loadPage" data-url="${ctx}/party/partyPost_menu"><i class="fa fa-circle-o-notch"></i> 党内任职情况</a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <div class="jqgrid-vertical-offset buttons">
                                <button class="popupBtn btn btn-info btn-sm"
                                        data-url="${ctx}/party/partyPost_au?userId=${param.userId}">
                                    <i class="fa fa-plus"></i> 添加任职经历</button>
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/party/partyPost_au?userId=${param.userId}"
                                        data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                    修改</button>
                                <button data-url="${ctx}/party/partyPost_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>
                                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-url="${ctx}/partyPost_data"
                                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                    <i class="fa fa-download"></i> 导出</button>--%>
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
                                <label>${_p_partyName}</label>
                                <select class="form-control" data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?auth=1&notDirect=1"
                                        data-width="350"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${party.id}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>党员</label>
                                <select data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/member_selects"
                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                    <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                </select>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/party/partyPost_menu"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/party/partyPost_menu"
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
    $("#jqGrid").jqGrid({ondblClickRow: function () {
        },
        pager: "jqGridPager",
        url: '${ctx}/party/partyPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '学工号',name: 'user.code',frozen:true,width:120},
            { label: '党员',name: 'user.realname',formatter:function (cellvalue, options, rowObject) {
                    return $.trim(cellvalue);
                },frozen:true},
            { label: '所属${_p_partyName}',name: 'partyId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称

                    var party = _cMap.partyMap[cellvalue];
                    var _partyView = null;
                    if (party != undefined) {
                        _partyView = party.name;
                        if ($.inArray("party:list", _permissions) >= 0 || $.inArray("party:*", _permissions) >= 0)
                            _partyView = $.trim('{0}'.format(party.name));
                    }else {

                    }
                    if (_partyView != null) {
                        return '<span class="{0}">{1}</span>'.format(party.isDeleted ? "delete" : "", _partyView);
                    }
                },frozen:true},
            { label: '所属党支部',name: 'branchId',width:280,formatter:function (cellvalue, optinons, rowObject) { // 显示组织名称
                    var branch = (cellvalue == undefined) ? undefined : _cMap.branchMap[cellvalue];
                    var _branchView = null;
                    if (branch != undefined) {
                        var _branchView = branch.name;
                        if ($.inArray("branch:list", _permissions) >= 0 || $.inArray("branch:*", _permissions) >= 0)
                            _branchView = $.trim('{0}'.format(branch.name))
                            ;
                    }
                    if (_branchView != null) { // 仅显示党支部
                        return '<span class="{0}">{1}</span>'
                            .format(branch.isDeleted ? "delete" : "", _branchView);
                    }
                    return '--';
                },frozen:true},
            { label: '任职开始时间',name:'startDate',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
            { label: '任职结束时间',name:'endDate',formatter:$.jgrid.formatter.date,formatoptions:{newformat:'Y.m.d'}},
            { label: '工作单位及担任职务',name: 'detail',width:280},
            { label: '备注',name: 'remark',width:200},{ hidden: true, key: true,name: 'id'}
        ],
        rowattr: function (rowData, currentObj, rowId) {
            if (rowData.isPresent) {
                //console.log(rowData)
                return {'class': 'success'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="tooltip"]').tooltip();
    $.register.fancybox();
    function _reload() {
        $("#modal").modal('hide');
        $("#view-box .tab-content").loadPage("${ctx}/party/partyPost_menu?${cm:encodeQueryString(pageContext.request.queryString)}");
    }
</script>