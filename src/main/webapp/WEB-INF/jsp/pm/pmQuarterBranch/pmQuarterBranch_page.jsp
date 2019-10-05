<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent" id="view-box">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
            <%-- <i class="ace-icon fa fa-user"></i>学生党员个人信息--%>
        </h4>
    </div>
</div>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty partyId ||not empty branchId }"/>
            <div class="jqgrid-vertical-offset buttons">

                <a class="btn btn-info btn-sm"
                   href="${ctx}/pmQuarterBranch_au?quarterId=1&partyId=17">
                    <i class="fa fa-plus"></i> 添加</a>
                <button data-url="${ctx}/pmExcludeBranch_au?partyId=${param.partyId}"
                        data-title="不召开党员大会"
                        data-msg="确定设置这{0}个支部不召开党员大会？"
                        data-grid-id="#jqGridBranch"
                        class="jqBatchBtn btn btn-danger btn-sm">
                    <i class="fa fa-sign-out"></i> 不召开党员大会
                </button>
                <button data-url="${ctx}/pmExcludeBranch_del?partyId=${param.partyId}"
                        data-title="召开党员大会"
                        data-msg="确定设置这{0}个支部召开党员大会？"
                        data-grid-id="#jqGridBranch"
                        class="jqBatchBtn btn btn-warning btn-sm">
                    <i class="fa fa-sign-in"></i> 召开党员大会
                </button>
                <shiro:hasPermission name="pmQuarterBranch:del">
                    <button data-url="${ctx}/pmQuarterBranch_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pmQuarterBranch_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
            </div>
            <div class="jqgrid-vertical-offset widget-box ${_query?'':'collapsed'} hidden-sm hidden-xs">
                <div class="widget-header">
                    <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

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
                            <label>分党委</label>
                            <%--<input class="form-control search-query" name="partyId" type="text" value="${param.partyId}"--%>
                                   <%--placeholder="请输入分党委id">--%>
                        </div>
                        <div class="form-group">
                            <label>支部</label>
                            <%--<input class="form-control search-query" name="branchId" type="text" value="${param.branchId}"--%>
                                   <%--placeholder="请输入支部id">--%>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pmQuarterBranch"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pmQuarterBranch"
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
            <table id="jqGridBranch" class="jqGrid2 table-striped"></table>
            <div id="jqGridPagerBranch"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGridBranch").jqGrid({
        rownumbers:true,
        pager:"jqGridPagerBranch",
        url: '${ctx}/pmQuarterBranch_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                {label: '所属机构', name: 'branch.name', width:400, frozen: true, align:'left', formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                  }
                  },
                { label: '查看', name: '_detail', width: 80, formatter:function(cellvalue, options, rowObject){

                    return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/pmMeeting?type=1&cls=3&partyId={1}&branchId={2}"><i class="fa fa-search"></i> {0}</button>'
                        .format('查看',rowObject.partyId, rowObject.branchId);
                },frozen:true },
                { label: '是否需要召开党员大会', name: 'isExclude' ,width:200,formatter:function(cellvalue, options, rowObject){
                        if(cellvalue==undefined||rowObject.isExclude==false) return '是';
                        return '否';
                       }
                },
                { label: '会议次数',name: 'meetingCount'
                },

                { label: '备注',name: 'remark',width:150,align:'left' },
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGridBranch", "jqGridPagerBranch");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>