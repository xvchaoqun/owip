<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.userId ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>
            <c:if test="${cls!=1}">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${cls==2}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCheck?cls=2"><i
                                class="fa fa-circle-o-notch"></i> 待审批<c:if test="${applyCount>0}">(${applyCount})</c:if></a>
                    </li>
                    <li class="<c:if test="${cls==3}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCheck?cls=3"><i
                                class="fa fa-check"></i> 审批通过<c:if test="${passCount>0}">(${passCount})</c:if></a>
                    </li>
                    <li class="<c:if test="${cls==4}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCheck?cls=4"><i
                                class="fa fa-times"></i> 返回修改<c:if test="${backCount>0}">(${backCount})</c:if></a>
                    </li>
                    <li class="<c:if test="${cls==5}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/member/memberCheck?cls=5"><i
                                class="fa fa-list"></i> 全部申请</a>
                    </li>
                </ul>
                <div class="space-4"></div>
            </c:if>
            <div class="jqgrid-vertical-offset buttons">
                <c:if test="${cls==1}">
                    <button class="openView btn btn-info btn-sm"
                            data-url="${ctx}/member/memberCheck_au">
                        <i class="fa fa-plus"></i> 申请
                    </button>
                </c:if>
                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                        data-open-by="page"
                        data-url="${ctx}/member/memberCheck_approval">
                    <i class="fa fa-search"></i> 详情
                </button>
                <c:if test="${cls==2}">
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                            data-open-by="page"
                            data-url="${ctx}/member/memberCheck_approval?opType=check">
                        <i class="fa fa-check"></i> 审核
                    </button>
                </c:if>
                <c:if test="${cls!=1}">
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                            data-url="${ctx}/applyApprovalLog"
                            data-querystr="&type=<%=OwConstants.OW_APPLY_APPROVAL_LOG_TYPE_MEMBER_CHECK%>"
                            data-open-by="page">
                        <i class="fa fa-history"></i> 操作记录
                    </button>
                </c:if>

                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/member/memberCheck_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
            </div>
            <c:if test="${cls!=1}">
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
                                    <label>用户</label>
                                    <div class="input-group">
                                        <select data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects"
                                                name="userId" data-placeholder="请输入账号或姓名或学工号">
                                            <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                                        </select>
                                    </div>
                                </div>
                                 <div class="form-group">
                                        <label>${_p_partyName}</label>
                                        <select class="form-control" data-width="350" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/party_selects?auth=1"
                                                name="partyId" data-placeholder="请选择">
                                            <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                        </select>
                                    </div>
                                    <div class="form-group" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                        <label>党支部</label>
                                        <select class="form-control" data-rel="select2-ajax"
                                                data-ajax-url="${ctx}/branch_selects?auth=1"
                                                name="branchId" data-placeholder="请选择党支部">
                                            <option value="${branch.id}" delete="${branch.isDeleted}">${branch.name}</option>
                                        </select>
                                    </div>
                                    <script>
                                        $.register.party_branch_select($("#searchForm"), "branchDiv",
                                                '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                    </script>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/member/memberCheck?cls=${cls}"
                                       data-target="#page-content"
                                       data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/member/memberCheck?cls=${cls}"
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
            </c:if>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers: true,
        <c:if test="${cls==1}">
        multiselect:false,
        </c:if>
        url: '${ctx}/member/memberCheck_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {
                label: '申请人', name: 'user.realname', width: 75, formatter: function (cellvalue, options, rowObject) {
                    <c:if test="${cls!=1}">
                    return $.member(rowObject.userId, cellvalue);
                    </c:if>
                    <c:if test="${cls==1}">
                    return cellvalue
                    </c:if>
                }, frozen: true
            },
            {label: '学工号', name: 'user.code', width:130, frozen: true},
           { label: '类别', name: 'user.type', width: 80, formatter:function(cellvalue, options, rowObject){
                if(cellvalue==undefined) return '--';
                return _cMap.USER_TYPE_MAP[cellvalue];
            }, frozen: true},
            {
                label: '所在党组织',
                name: 'party',
                align: 'left',
                width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    return $.party(rowObject.partyId, rowObject.branchId);
                }
            },
            <c:if test="${cls==1}">
            {
                label: '操作', name: '_op', width:140, formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.userId != '${_user.id}'
                        || rowObject.status=='<%=MemberConstants.MEMBER_CHECK_STATUS_PASS%>') return '--'

                    return '<button data-url="${ctx}/member/memberCheck_au" class="openView btn btn-primary btn-xs">'
                        + '<i class="fa fa-edit"></i> 修改</button> &nbsp;'
                    +
                    '<button data-url="${ctx}/member/memberCheck_batchDel?ids[]={0}" data-msg="确定撤销申请？" data-callback="_reload" class="confirm btn btn-danger btn-xs">'
                            .format(rowObject.id)
                        + '<i class="fa fa-times"></i> 撤销</button>'
                }
            },
            </c:if>
            {
                label: '状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--'
                return _cMap.MEMBER_CHECK_STATUS_MAP[cellvalue];
            }
            },
            {label: '审核备注', name: 'reason', align:'left', width:240},
            { label: '申请时间', name: 'createTime', width:170},
            { label: 'IP', name: 'ip', width:140},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>