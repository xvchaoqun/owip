<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.id || not empty param.name || not empty param.code ||not empty param.classId ||not empty param.phone || not empty param.code || not empty param.sort
            ||not empty param._foundTime ||not empty param.presentGroupCount}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <c:if test="${cls==1}">
                <shiro:hasPermission name="dpParty:add">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpParty_au?cls=${cls}">
                        <i class="fa fa-plus"></i> 添加</button>
                </shiro:hasPermission>
                </c:if>
                <shiro:hasPermission name="dpParty:edit">
                    <button class="jqEditBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpParty_au?cls=${cls}"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <c:if test="${cls==1}">
                            <a class="jqOpenViewBatchBtn btn btn-danger btn-sm"
                               data-url="${ctx}/dp/dpParty_cancel" data-title="移除民主党派"
                               data-msg="确定移除这{0}个民主党派吗？"><i class="fa fa-minus-square"></i> 移除</a>
                    <button class="popupBtn btn btn-info btn-sm tooltip-info"
                            data-url="${ctx}/dp/dpParty_import"
                            data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                        批量导入
                    </button>
                    </c:if>
                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/dp/dpParty_data"
                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出</button>
                </shiro:hasPermission>

                <shiro:hasPermission name="dpOrgAdmin:list">
                <button data-url="${ctx}/dp/dp_org_admin"
                        data-id-name="partyId" class="jqOpenViewBtn btn btn-warning btn-sm">
                    <i class="fa fa-user"></i> 编辑管理员
                </button>
                </shiro:hasPermission>
                <button class="jqOpenViewBtn btn btn-info btn-sm"
                        data-url="${ctx}/sysApprovalLog"
                        data-querystr="&type=<%=SystemConstants.SYS_DP_LOG_TYPE_PARTY%>"
                        data-open-by="page">
                    <i class="fa fa-sign-in"></i> 查看操作记录
                </button>
                <c:if test="${cls==2}">
                    <shiro:hasPermission name="dpParty:edit">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/dp/dpParty_batchDel"
                           data-querystr="isDeleted=0"
                           data-title="恢复已移除民主党派"
                           data-msg="确定恢复这{0}个民主党派吗？"><i class="fa fa-reply"></i> 恢复</a>
                    </shiro:hasPermission>
                </c:if>
                <shiro:hasPermission name="dpParty:del">
                    <button data-url="${ctx}/dp/dpParty_del"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <c:if test="${cls==1}">
                    【注：移除操作将删除其下所有的委员会和相关管理员权限，请谨慎操作！】
                </c:if>
                <c:if test="${cls==2}">
                    【注：恢复操作之后需要重新设置委员会及相关管理员权限！】
                </c:if>
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
                            <input type="hidden" name="cls" value="${cls}">
                            <div class="form-group">
                                <label>民主党派名称</label>
                                <div class="input-group">
                                    <select  data-width="230" data-rel="select2-ajax"
                                             data-ajax-url="${ctx}/dp/dpParty_selects?auth=1"
                                             name="id" data-placeholder="请选择">
                                        <option value="${dpParty.id}" title="${dpParty.isDeleted}">${dpParty.name}</option>
                                    </select>
                                </div>
                                <script>         $("#searchForm select[name=id]").val('${param.id}');     </script>
                            </div>
                            <div class="form-group">
                                <label>编号</label>
                                <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入编号">
                            </div>
                            <div class="form-group">
                                <label>所属民主党派类别</label>
                                    <select data-width="230" name="classId" data-rel="select2" data-placeholder="请选择">
                                        <option></option>
                                        <c:import url="/metaTypes?__id=${cm:getMetaClassByCode('mc_dp_party_class').id}"/>
                                    </select>
                                <script>         $("#searchForm select[name=classId]").val('${param.classId}');     </script>
                            </div>
                            <div class="form-group">
                                <label>成立时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="成立时间范围">
                                                    <span class="input-group-addon">
                                                        <i class="fa fa-calendar bigger-110"></i>
                                                    </span>
                                    <input placeholder="请选择成立时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_foundTime" value="${param._foundTime}"/>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpParty?cls=${cls}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpParty?cls=${cls}"
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
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>

    $("#jqGrid").jqGrid({
        rownumbers:false,
        url: '${ctx}/dp/dpParty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情',
                name: '_detail',
                width: 80,
                frozen: true,
                formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/dp/dpParty_view?id={0}">'
                        + '<i class="fa fa-search"></i> 查看</button>').format(rowObject.id)}},
            {label: '编号', name: 'code', frozen: true, width:100},
            {label: '名称', name: 'name', width: 200, frozen: true, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.id, cellvalue, ctx);
                     if (cellvalue != ''){
                         return '<span class="{0}">{1}</span>'.format(rowObject.isDeleted ? "delete" : "", _dpPartyView);
                     }
                     return "--";
                }},
            <shiro:hasPermission name="dpParty:edit">
            <c:if test="${!_query}">
            {label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: '${ctx}/dp/dpParty_changeOrder'}, frozen: true},
            </c:if>
            </shiro:hasPermission>
            <c:if test="${cls==2}">
            {label: '移除时间', name: 'deleteTime', width: 100, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            </c:if>
            {label: '成员总数', name: 'memberCount', width: 80, formatter: function (cellvalue, option, rowObject) {
                    <shiro:hasPermission name="dpMember:list">
                    if (cellvalue == undefined ||cellvalue == 0)return 0;
                    return '<a href="#${ctx}/dp/dpMember?cls=10&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>}},
            {label: '在职成员数',
                name: 'teacherMemberCount',
                width: 90,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined || cellvalue == 0) return 0;
                    <shiro:hasPermission name="dpMember:list">
                    return '<a href="#${ctx}/dp/dpMember?cls=2&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="dpMember:list">
                    return cellvalue;
                    </shiro:lacksPermission>}},
            {label: '离退休成员数',
                name: 'retireMemberCount',
                width: 100,
                formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined || cellvalue == 0) return 0;
                    <shiro:hasPermission name="dpMember:list">
                    return '<a href="#${ctx}/dp/dpMember?cls=3&partyId={0}" target="_blank">{1}</a>'.format(rowObject.id, cellvalue);
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="dpMember:list">
                    return cellvalue;
                    </shiro:lacksPermission>}},
            {label: '委员会总数', name: 'groupCount', width: 90, formatter: function (cellvalue, options, rowObject) {
                    return cellvalue == undefined ? 0 : cellvalue;}},
            {label: '是否已设立现任委员会',
                name: 'presentGroupCount',
                width: 160,
                formatter: function (cellvalue, options, rowObject) {
                    return cellvalue >= 1 ? "是" : "否";}},
            {label: '党派简称', name: 'shortName', width: 180},
            {label: '民族党派类别', name: 'classId', width:300, formatter: $.jgrid.formatter.MetaType},
            {label: '联系电话', name: 'phone'},
            {label: '邮箱', name: 'email'},
            {label: '信箱', name: 'mailbox'},
            {label: '成立时间', name: 'foundTime', width: 100, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '备注', name: 'remark', width: 180}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>