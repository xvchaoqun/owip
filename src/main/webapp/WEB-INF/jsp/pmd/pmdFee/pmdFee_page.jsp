<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId ||not empty param.isOnlinePay ||not empty param.hasPay ||not empty param.payTime || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pmdFee:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/pmd/pmdFee_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pmd/pmdFee_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="pmdFee:del">
                    <button data-url="${ctx}/pmd/pmdFee_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pmd/pmdFee_data"
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
                            <label>姓名</label>
                            <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                                   placeholder="请输入用户ID">
                        </div>
                        <div class="form-group">
                            <label>所属分党委</label>
                            <input class="form-control search-query" name="partyId" type="text" value="${param.partyId}"
                                   placeholder="请输入所属分党委">
                        </div>
                        <div class="form-group">
                            <label>所在党支部</label>
                            <input class="form-control search-query" name="branchId" type="text" value="${param.branchId}"
                                   placeholder="请输入所在党支部">
                        </div>
                        <div class="form-group">
                            <label>缴费方式</label>
                            <input class="form-control search-query" name="isOnlinePay" type="text" value="${param.isOnlinePay}"
                                   placeholder="请输入缴费方式">
                        </div>
                        <div class="form-group">
                            <label>状态</label>
                            <input class="form-control search-query" name="hasPay" type="text" value="${param.hasPay}"
                                   placeholder="请输入状态">
                        </div>
                        <div class="form-group">
                            <label>缴费时间</label>
                            <input class="form-control search-query" name="payTime" type="text" value="${param.payTime}"
                                   placeholder="请输入缴费时间">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pmdFee"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pmdFee"
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
        //rownumbers:true,
        url: '${ctx}/pmd/pmdFee_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '缴费类型',name: 'type',width: 130,formatter:function (cellvalue, options, rowObject) {

                    return _cMap.metaTypeMap[cellvalue].name;
                    }},
                { label: '缴费月份',name: 'payMonth',width: 120,formatter: $.jgrid.formatter.date,formatoptions: {newformat: 'Y.m'}},
                { label: '姓名',name: 'user.realname'},
                { label: '所属分党委',name: 'partyId',width: 350,align: 'left',formatter: function (cellvalue, options, rowObject) {
                    return cellvalue==undefined?"":_cMap.partyMap[cellvalue].name;
                    }},
                { label: '所在党支部',name: 'branchId',width: 250,align: 'left',formatter: function (cellvalue, options, rowObject) {
                    return cellvalue==undefined?"":_cMap.branchMap[cellvalue].name;
                    }},
                { label: '缴费金额',name: 'amt'},
                { label: '缴费原因',name: 'reason'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>