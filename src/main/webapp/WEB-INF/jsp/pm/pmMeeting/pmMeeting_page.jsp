<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/pmMeeting_au"
             data-url-page="${ctx}/pmMeeting?cls=${cls}&type=${type}"
             data-url-export="${ctx}/pmMeeting"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <div class="tabbable">
                <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_BRANCHADMIN},${ROLE_ADMIN}">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="${cls==1?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=1&type=${type}"}><i class="fa fa-circle-o"></i> 待审核</a>
                    </li>
                    </li>
                    <li class="${cls==2?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=2&type=${type}"}><i class="fa fa-times"></i> 未通过\已退回</a>
                    </li>
                    <li class="${cls==3?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/pmMeeting?cls=3&type=${type}"}><i class="fa fa-check"></i> 已通过</a>
                    </li>
                </ul>
                </shiro:hasAnyRoles>
                <div class="tab-content">
                    <div class="tab-pane in active">
                        <c:set var="_query"
                               value="${not empty param.partyId||not empty param.branchId||not empty param.issue||not empty param.quarter||not empty param._meetingDate}"/>
                        <div class="jqgrid-vertical-offset buttons">

                                <button class="openView btn btn-info btn-sm"
                                        data-url="${ctx}/pmMeeting_au?type=${type}&edit=true">
                                    <i class="fa fa-plus"></i> 添加
                                </button>
                                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                               data-url="${ctx}/pmMeeting_au?edit=true"
                                               data-grid-id="#jqGrid"
                                               data-open-by="page"
                                            ><i class="fa fa-edit"></i>
                                        修改</a>


                                <button data-url="${ctx}/pmMeeting_del"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-trash"></i> 删除
                                </button>

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
                                            <div class="form-group">
                                                <label>${_p_partyName}</label>
                                                <select class="form-control" data-width="250" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                                        name="partyId" data-placeholder="请选择">
                                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                                </select>
                                            </div>
                                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                                 id="branchDiv">
                                                <label>党支部</label>
                                                <select class="form-control" data-rel="select2-ajax"
                                                        data-ajax-url="${ctx}/branch_selects?auth=1"
                                                        name="branchId" data-placeholder="请选择党支部">
                                                    <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                                </select>
                                            </div>
                                            <label>会议议题</label>
                                            <input class="form-control search-query" name="issue" type="text"
                                                   value="${param.issue}"
                                                   placeholder="请输入会议议题">
                                        </div>
                                        <script>
                                            $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                        </script>
                                        <div class="form-group">
                                            <label>会议时间</label>
                                            <div class="input-group tooltip-success" data-rel="tooltip" title="会议时间范围">
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-calendar bigger-110"></i>
                                                            </span>
                                                <input placeholder="请选择会议时间范围" data-rel="date-range-picker"
                                                       class="form-control date-range-picker"
                                                       type="text" name="_meetingDate" value="${param._meetingDate}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>季度</label>
                                            <select class="form-control" data-width="150" data-rel="select2"
                                                    id="quarter"  name="quarter" data-placeholder="请选择">
                                                <option></option>
                                                <option value="1">第1季度</option>
                                                <option value="2">第2季度</option>
                                                <option value="3">第3季度</option>
                                                <option value="4">第4季度</option>

                                            </select>
                                        </div>
                                        <script>
                                           $('#quarter option[value="${param.quarter}"]').attr("selected","selected");
                                        </script>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="cls=${cls}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                        <%--<div class="clearfix form-actions center">--%>
                                            <%--<a class="jqSearchBtn btn btn-default btn-sm"--%>
                                               <%--data-url="${ctx}/pmMeeting?cls=${cls}&type=${type}&partyId=${partyId}&branchId=${branchId}"--%>
                                               <%--data-target="#page-content"--%>
                                               <%--data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>--%>
                                            <%--<c:if test="${_query}">&nbsp;--%>
                                                <%--<button type="button" class="reloadBtn btn btn-warning btn-sm"--%>
                                                        <%--data-url="${ctx}/pmMeeting?cls=${cls}&type=${type}"--%>
                                                        <%--data-target="#page-content">--%>
                                                    <%--<i class="fa fa-reply"></i> 重置--%>
                                                <%--</button>--%>
                                            <%--</c:if>--%>
                                        <%--</div>--%>
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pmMeeting_data?branch=${param.branchId}&type=${type}&cls=${cls}&callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '所属机构', name: 'branch.name', width:370, frozen: true, align:'left', formatter: function (cellvalue, options, rowObject) {
                return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            {label: '会议议题', name: 'issue', width:250, align:'left', formatter:function(cellvalue, options, rowObject){
                    if(cellvalue==undefined|| cellvalue==0) return 0;
                    return '<a href="javascript:;" class="openView" data-url="${ctx}/pmMeeting_au?edit=false&id={0}">{1}</a>'.format( rowObject.id,cellvalue);
                }
            },
            {label: '会议时间', name: 'date', width:180, frozen: true, align:'left'},
            {
                label: '季度', name: 'quarter', align: 'left', formatter: function (cellvalue, options, rowObject) {
                    return '第'+cellvalue+'季度'
                }
            },
            {label: '主持人', name: 'presenterName.realname', align:'left'},
            {label: '记录人', name: 'recorderName.realname', align:'left'},
            {label: '会议地点', name: 'address', align:'left'},
            {label: '参会人员', name: 'attendList', align:'left',formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '--'
                    return $.map(cellvalue, function(u){
                        return u.realname;
                    })
                }},
            {label: '附件', name: 'file', formatter: function (cellvalue, options, rowObject) {

                    return '<button class="popupBtn btn btn-info btn-xs" data-width="500" data-callback="_reload"' +
                        'data-url="${ctx}/pmMeetingFile?id={0}"><i class="fa fa-search"></i> 附件{1}</button>'
                            .format(rowObject.id, rowObject.fileCount>=0?"("+rowObject.fileCount+")":"")
                }},
            { label: '审核情况', name: 'status', width: 80,
                <%--formatter:function(cellvalue, options, rowObject){--%>

                    <%--return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/pmMeeting_page?id={0}&branchId={2}"><i class="fa fa-search"></i> {1}</button>'--%>
                        <%--.format(rowObject.id, '审核',rowObject.branchId);--%>
                <%--}--%>
                formatter: function (cellvalue, options, rowObject) {
                    <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ADMIN}">
                    if (cellvalue == 0||cellvalue == undefined)
                        return '<button class="popupBtn btn btn-success btn-xs" ' +
                            'data-url="${ctx}/pmMeeting_check?id={0}&check=true"><i class="fa fa-check"></i> {1}</button>'.format(rowObject.id, '审核');
                    </shiro:hasAnyRoles>
                    if(rowObject.isBack) return '已退回'
                            return _cMap.PM_MEETING_STATUS_MAP[cellvalue];
                }
                ,frozen:true },
            <c:if test="${cls==1}">
            <shiro:hasAnyRoles name="${ROLE_PARTYADMIN},${ROLE_ADMIN}">
            { label: '退回', name: 'isBack', width: 80, formatter:function(cellvalue, options, rowObject){
                    if (cellvalue) return '已退回'
                    if (rowObject.status != 0)  return '--';

                    return '<button class="popupBtn btn btn-danger btn-xs" data-url="${ctx}/pmMeeting_check?id={0}&check=false"><i class="fa fa-reply"></i> {1}</button>'
                        .format(rowObject.id, '退回');
                },frozen:true
            },
            </shiro:hasAnyRoles>
            {label: '原因', name: 'reason', align:'left'},
            <c:if test="${cls!=1}">
            <shiro:hasAnyRoles name="${ROLE_BRANCHADMIN},${ROLE_ADMIN}">
            { label: '重新编辑', name: 'reedit', width: 80, formatter:function(cellvalue, options, rowObject){
                    if (rowObject.isBack)
                    return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/pmMeeting_au?edit=true&id={0}&reedit=1"><i class="fa fa-edit"></i> {1}</button>'
                        .format(rowObject.id, '重新编辑');
                    return '--';
                },frozen:true
            },
            </shiro:hasAnyRoles>
            </c:if>
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();

    $('[data-rel="tooltip"]').tooltip();

</script>