<%@ page import="sys.constants.SystemConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv"
                 data-url-au="${ctx}/memberQuit_au"
                 data-url-page="${ctx}/memberQuit_page"
                 data-url-del="${ctx}/memberQuit_del"
                 data-url-bd="${ctx}/memberQuit_batchDel"
                 data-url-co="${ctx}/memberQuit_changeOrder"
                 data-url-export="${ctx}/memberQuit_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.userId ||not empty param._quitTime
                 ||not empty param.partyId ||not empty param.branchId || not empty param.code || not empty param.sort}"/>

                            <div class="jqgrid-vertical-offset buttons">
                                <shiro:hasPermission name="memberQuit:edit">
                                    <a href="javascript:;" class="editBtn btn btn-info btn-sm">
                                        <i class="fa fa-plus"></i> 添加</a>
                                    <button id="editBtn" class="jqEditBtn btn btn-primary btn-sm"
                                            data-id-name="userId">
                                        <i class="fa fa-edit"></i> 修改信息
                                    </button>
                                </shiro:hasPermission>
                                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                                   data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）"><i
                                        class="fa fa-download"></i> 导出</a>
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
                                        <form class="form-horizontal " id="searchForm">
                                            <input type="hidden" name="cls" value="${cls}">
                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">姓名</label>

                                                        <div class="col-xs-6">
                                                            <div class="input-group">
                                                                <select data-rel="select2-ajax" data-ajax-url="${ctx}/member_selects?status=${MEMBER_STATUS_QUIT}"
                                                                        name="userId" data-placeholder="请输入账号或姓名或学工号">
                                                                    <option value="${sysUser.id}">${sysUser.realname}</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">出党时间</label>

                                                        <div class="col-xs-6">
                                                            <div class="input-group tooltip-success" data-rel="tooltip" title="出党时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                                                <input placeholder="请选择出党时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_quitTime" value="${param._quitTime}"/>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label class="col-xs-3 control-label">分党委</label>

                                                        <div class="col-xs-6">
                                                            <select class="form-control" data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/party_selects"
                                                                    name="partyId" data-placeholder="请选择分党委">
                                                                <option value="${party.id}">${party.name}</option>
                                                            </select>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="col-xs-4" style="${(empty branch)?'display: none':''}" id="branchDiv">
                                                    <div class="form-group">
                                                        <label class="col-xs-4 control-label">党支部</label>

                                                        <div class="col-xs-6">
                                                            <select class="form-control" data-rel="select2-ajax"
                                                                    data-ajax-url="${ctx}/branch_selects"
                                                                    name="branchId" data-placeholder="请选择党支部">
                                                                <option value="${branch.id}">${branch.name}</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <script>
                                                    register_party_branch_select($("#searchForm"), "branchDiv",
                                                            '${cm:getMetaTypeByCode("mt_direct_branch").id}', "${party.id}", "${party.classId}");
                                                </script>
                                            </div>
                                            <div class="clearfix form-actions center">
                                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                                <c:if test="${_query || not empty param.sort}">&nbsp;
                                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                                            data-querystr="cls=${cls}">
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
        <div id="item-content">

        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/memberQuit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '学工号', name: 'user.code', frozen: true},
            { label: '姓名', name: 'user.realname',resizable:false, width: 75, formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/member_view?userId={0}">{1}</a>'
                        .format(rowObject.userId, cellvalue);
            } ,frozen:true },
            {label: '性别', name: 'user.genderName', frozen: true},
            {label: '年龄', name: 'user.age', frozen: true},
            {label: '入党时间', name: 'growTime', frozen: true},
            {
                label: '所属组织机构', name: 'from', resizable: false, width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    var party = rowObject.party;
                    var branch = rowObject.branch;
                    return party + (($.trim(branch) == '') ? '' : '-' + branch);
                }, frozen: true
            },
            {label: '出党时间', name: 'quitTime', width: 150},
            {label: '出党原因', name: 'type', width: 150, formatter: function (cellvalue, options, rowObject) {
                return _cMap.RETIRE_QUIT_TYPE_MAP[rowObject.type];
            }}, {hidden: true, name: 'status'},{hidden:true, key:true, name:'userId'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('[data-rel="select2"]').select2();
    register_user_select($('#searchForm select[name=userId]'));
</script>
