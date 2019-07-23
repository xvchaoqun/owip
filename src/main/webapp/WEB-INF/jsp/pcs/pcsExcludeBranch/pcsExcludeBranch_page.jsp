<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/pcsExcludeBranch"
                 data-url-export="${ctx}/pcsExcludeBranch_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.partyId || not empty param.branchId || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pcsExcludeBranch:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/pcsExcludeBranch_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pcsExcludeBranch_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="pcsExcludeBranch:del">
                    <button data-url="${ctx}/pcsExcludeBranch_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
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
                                <label>${_p_partyName}</label>
                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/party_selects?del=0&notDirect=1"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
        url: '${ctx}/pcsExcludeBranch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '所属${_p_partyName}',
                name: 'partyId',
                align: 'left',
                width: 400,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(cellvalue);
                }
            },
            {
                label: '党支部名称',
                name: 'branchId',
                align: 'left',
                width: 250,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(null, cellvalue);
                }
            },
            { label: '添加时间',name: 'createTime', width:150},
            { label: '备注',name: 'remark', width:500}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.del_select($('#searchForm select[name=partyId]'));
</script>