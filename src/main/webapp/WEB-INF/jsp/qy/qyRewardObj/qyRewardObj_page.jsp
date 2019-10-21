<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
            <div class="widget-header">
                <h4 class="widget-title lighter smaller">
                    <a href="javascript:;" class="hideView btn btn-xs btn-success">
                        <i class="ace-icon fa fa-backward"></i>
                        返回</a>
                </h4>
            </div>
    <div class="widget-body">
        <div class="widget-main padding-4">
            <c:set var="_query" value="${not empty param.partyId}"/>
            <div class="tab-content padding-8">
                <shiro:hasPermission name="qyReward:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/qyRewardObj_au?recordId=${param.recordId}&type=${param.type}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/qyRewardObj_au?type=${param.type}"
                       data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/qyRewardObj_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid2"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/qyRewardObj_data?type=${param.type}&exportType=2"
                        data-grid-id="#jqGrid2"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>

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
                        <form class="form-inline search-form" id="searchForm2">
                            <div class="form-group">
                                <label>院系级党委</label>
                                <select class="form-control" data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?del=0"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>

                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/qyRewardObj?type=${param.type}"
                                   data-target="#body-content-view"
                                   data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/qyRewardObj?type=${param.type}"
                                            data-target="#body-content-view">
                                        <i class="fa fa-reply"></i> 重置
                                    </button>
                                </c:if>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid2" class="jqGrid2 table-striped"></table>
            <div id="jqGridPager2"></div>
          </div>
        <%--<div id="body-content-view"></div>--%>
       </div>
   </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager:"#jqGridPager2",
        rownumbers:true,
        url: '${ctx}/qyRewardObj_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年度',name: 'year'},
                { label: '奖项',name: 'rewardName', width: 250,align:'left'},
            <c:if test="${param.type==4}">
                { label: '获表彰党日活动名称',name: 'meetingName',width: 250,align:'left'},
                { label: '所属党支部',name: 'branchName', width: 300,align:'left'},
            </c:if>
            <c:if test="${param.type==3}">
                { label: '获表彰党员工作证号',name: 'user.code', width: 150},
                { label: '获表彰党员姓名',name: 'user.realname', width: 120},
            </c:if>
                { label: '${param.type==1?"获表彰院系级党委":"所属党委"}',name: 'partyName',width: 300,align:'left'},

            <c:if test="${param.type==1}">
                { label: '党委编号',name: 'party.code',formatter:function(cellvalue, options, rowObject){
                        if($.isBlank(cellvalue))
                            return "--"
                        return    '<a href="javascript:;" class="openView" data-url="${ctx}/party_view?id={0}">{1}</a>'
                            .format(rowObject.partyId, cellvalue);
                    }
                 },
            </c:if>
            <c:if test="${param.type==2}">
                { label: '获表彰党支部',name: 'branchName', width: 300,align:'left',formatter:function(cellvalue, options, rowObject){
                        var branchName=cellvalue;
                        if(rowObject.party.classId==${cm:getMetaTypeByCode("mt_direct_branch").id}){
                            branchName=rowObject.partyName;
                        }
                        if($.isBlank(branchName))
                            return "--"
                        return branchName;
                    }},
                { label: '党支部编号',name: 'branch.code',formatter:function(cellvalue, options, rowObject){
                    var id=rowObject.branchId;
                    var code=cellvalue;
                    var url="branch_view";
                    if(rowObject.party.classId==${cm:getMetaTypeByCode("mt_direct_branch").id}){
                        id=rowObject.partyId;
                        code=rowObject.party.code;
                        url="party_view"
                    }
                    if($.isBlank(code))
                        return "--"
                        return    '<a href="javascript:;" class="openView" data-url="${ctx}/{2}?id={0}">{1}</a>'
                            .format(id, code,url);
                    }},
            </c:if>
            <c:if test="${!_query}">
                { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                    formatoptions:{grid:'#jqGrid2',url:'${ctx}/qyRewardObj_changeOrder'},frozen:true },
            </c:if>
              /* { label: '备注',name: 'remark', width: 300},*/
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
   // $('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>