<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="widget-box transparent">
                <div class="widget-header">
                    <jsp:include page="/WEB-INF/jsp/member/memberApply/menu.jsp"/>
                </div>
                <div class="widget-body">
                    <div class="widget-main padding-12 no-padding-left no-padding-right" style="padding-top: 5px;">
                        <div class="tab-content padding-4">
                            <div class="tab-pane in active">
                                <c:set var="_query"
                                       value="${not empty param.year ||not empty param.displaySn
                                       || not empty param.isUsed || not empty param.userId || not empty param.isAbolished
                                       || not empty param.partyId|| not empty param.branchId}"/>
                                <div class="jqgrid-vertical-offset buttons">

                                    <shiro:hasPermission name="applySnRange:change">
                                        <button id="changeBtn" class="jqOpenViewBtn tooltip-warning btn btn-warning btn-sm"
                                                data-url="${ctx}/applySn_change"
                                                data-rel="tooltip" data-placement="top" title="原编码作废，新分配编码"
                                                data-grid-id="#jqGrid"><i class="fa fa-refresh"></i>
                                            换领志愿书
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="applySnRange:change">
                                        <button id="changeBtn" class="jqOpenViewBtn tooltip-primary btn btn-primary btn-sm"
                                                data-url="${ctx}/applySn_exchange"
                                                data-rel="tooltip" data-placement="top" title="对已分配的编码进行调换"
                                                data-grid-id="#jqGrid"><i class="fa fa-refresh"></i>
                                            调换编码
                                        </button>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="applySnRange:reuse">
                                        <button id="reuseBtn"
                                                data-title="恢复使用"
                                                data-msg="确定恢复使用这个编码？"
                                                class="jqItemBtn tooltip-success btn btn-danger btn-sm"
                                                data-url="${ctx}/applySn_reuse"
                                                data-rel="tooltip" data-placement="top" title="恢复已作废的编码，更新为未使用状态"
                                                data-grid-id="#jqGrid"><i class="fa fa-reply"></i>
                                            恢复使用
                                        </button>
                                    </shiro:hasPermission>
                                    <c:if test="${cls==8}">
                                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                            data-url="${ctx}/applySn_data?cls=${cls}"
                                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                        <i class="fa fa-download"></i> 导出
                                    </button>
                                    </c:if>
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
                                                <div class="form-group">
                                                    <label>年份</label>
                                                    <div class="input-group" style="width: 120px">
                                                        <input class="form-control date-picker" name="year" type="text"
                                                               data-date-format="yyyy"
                                                               data-date-min-view-mode="2"
                                                               placeholder="选择年份" value="${param.year}"/>
                                                        <span class="input-group-addon"> <i
                                                                class="fa fa-calendar bigger-110"></i></span>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label>志愿书编码</label>
                                                    <input class="form-control search-query" name="displaySn"
                                                           type="text" value="${param.displaySn}"
                                                           placeholder="请输入">

                                                </div>
                                                <c:if test="${cls==8}">
                                                <div class="form-group">
                                                    <label>${_p_partyName}</label>
                                                        <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/party_selects?auth=1"
                                                                name="partyId" data-placeholder="请选择">
                                                            <option value="${party.id}" title="${party.isDeleted}">${party.name}</option>
                                                        </select>
                                                </div>

                                                <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                    <label>党支部</label>
                                                        <select class="form-control" data-rel="select2-ajax"
                                                                data-ajax-url="${ctx}/branch_selects?auth=1"
                                                                name="branchId" data-placeholder="请选择党支部">
                                                            <option value="${branch.id}" title="${branch.isDeleted}">${branch.name}</option>
                                                        </select>
                                                </div>
                                                <script>
                                                    $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                                </c:if>
                                                <c:if test="${cls==7}">
                                                <div class="form-group">
                                                    <label>是否已使用</label>
                                                    <div class="input-group">
                                                        <select name="isUsed" data-rel="select2"
                                                                data-width="90"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isUsed]").val("${param.isUsed}");
                                                        </script>
                                                    </div>
                                                </div>
                                                </c:if>
                                                <div class="form-group">
                                                    <label>使用人</label>
                                                    <select data-rel="select2-ajax"
                                                            data-ajax-url="${ctx}/sysUser_selects"
                                                            name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                                    </select>
                                                </div>

                                                <div class="form-group">
                                                    <label>是否作废</label>
                                                    <div class="input-group">
                                                        <select name="isAbolished" data-rel="select2"
                                                                data-width="90"
                                                                data-placeholder="请选择">
                                                            <option></option>
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                        <script>
                                                            $("#searchForm select[name=isAbolished]").val("${param.isAbolished}");
                                                        </script>
                                                    </div>
                                                </div>

                                                <div class="clearfix form-actions center">
                                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                                       data-url="${ctx}/applySn?cls=${cls}"
                                                       data-target="#page-content"
                                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                                    <c:if test="${_query}">&nbsp;
                                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                                data-url="${ctx}/applySn?cls=${cls}"
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        url: '${ctx}/applySn_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'applySnRange.year', width: 90},
            {label: '志愿书编码', name: 'displaySn', width: 150},
            <c:if test="${cls==7}">
            {
                label: '所属号段', name: 'applySnRange', width: 320, formatter: function (cellvalue, options, rowObject) {
                    return $.trim(cellvalue.prefix) + cellvalue.startSn.zfill(cellvalue.len)
                        + " ~ " + $.trim(cellvalue.prefix) + cellvalue.endSn.zfill(cellvalue.len)
                }
            },
            {label: '是否已使用', name: 'isUsed', width: 90, formatter: $.jgrid.formatter.TRUEFALSE},
            </c:if>
            {label: '使用人', name: 'user.realname'},
            {label: '使用学工号', name: 'user.code', width: 120},
            <c:if test="${cls==8}">
            {
                label: '所属组织机构', name: 'party', align:'left',  width: 550, formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.partyId, rowObject.branchId);
            }},
            </c:if>
            {label: '是否作废', name: 'isAbolished', width: 80, formatter: $.jgrid.formatter.TRUEFALSE},
            {
                name: '_isUsed', hidden: true, formatter: function (cellvalue, options, rowObject) {
                    return rowObject.isUsed;
                }
            },
            {
                name: '_isAbolished', hidden: true, formatter: function (cellvalue, options, rowObject) {
                    return rowObject.isAbolished;
                }
            }
        ],
        onSelectRow: function (id, status) {
            saveJqgridSelected("#" + this.id, id, status);
            _onSelectRow(this)
        },
        onSelectAll: function (aRowids, status) {
            saveJqgridSelected("#" + this.id);
            _onSelectRow(this)
        },
        rowattr: function(rowData, currentObj, rowId)
        {
            if(rowData.isAbolished) {
                //console.log(rowData)
                return {'class':'danger'}
            }
        }
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));

    function _onSelectRow(grid) {
        var ids = $(grid).getGridParam("selarrrow");
        if (ids.length > 1) {
            $("#changeBtn, #reuseBtn").prop("disabled", true);
        } else if (ids.length == 1) {

            var rowData = $(grid).getRowData(ids[0]);
            var canChange = (rowData._isUsed == "true") && (rowData._isAbolished != "true");
            $("#changeBtn").prop("disabled", !canChange);
            var canReuse = (rowData._isUsed == "true") && (rowData._isAbolished == "true");
            $("#reuseBtn").prop("disabled", !canReuse);
        }
    }
</script>