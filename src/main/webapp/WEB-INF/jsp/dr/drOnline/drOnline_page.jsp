<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:if test="${status!=3}">
                <c:set var="_query" value="${not empty param.recordId || not empty param.status||not empty param.year ||not empty param._recommendDate ||not empty param.seq ||not empty param.type ||not empty param._startTime ||not empty param._endTime || not empty param.code || not empty param.sort}"/>
            </c:if>
            <c:if test="${status==3}">
                <c:set var="_query" value="${not empty param.recordId || not empty param.year ||not empty param._recommendDate ||not empty param.seq ||not empty param.type ||not empty param._startTime ||not empty param._endTime || not empty param.code || not empty param.sort}"/>
            </c:if>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="tab-content multi-row-head-table">
                    <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <c:if test="${isDeleted!=1}">
                <c:if test="${status!=3}">
                    <shiro:hasPermission name="drOnline:edit">
                        <button class="popupBtn btn btn-info btn-sm"
                                data-url="${ctx}/dr/drOnline_au">
                            <i class="fa fa-plus"></i> 添加</button>
                        <button class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/dr/drOnline_au"
                           data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                            修改</button>
                    </shiro:hasPermission>
                    </c:if>
                    <shiro:hasPermission name="drOnline:edit">
                        <c:if test="${status==3}">
                            <button data-url="${ctx}/dr/drOnline_changeStatus?status=1"
                                    data-title="撤回"
                                    data-msg="确定撤回这{0}条数据？"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-success btn-sm">
                                <i class="fa fa-reply"></i> 撤回
                            </button>
                        </c:if>
                        <c:if test="${status!=3}">
                            <button data-url="${ctx}/dr/drOnline_changeStatus?status=1"
                                    data-title="发布"
                                    data-msg="确定发布这{0}条数据？"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-success btn-sm">
                                <i class="fa fa-check-circle-o"></i> 发布
                            </button>
                            <button data-url="${ctx}/dr/drOnline_changeStatus?status=2"
                                    data-title="撤回"
                                    data-msg="确定撤回这{0}条数据？"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-warning btn-sm">
                                <i class="fa fa-times-circle-o"></i> 撤回
                            </button>
                            <button data-url="${ctx}/dr/drOnline_changeStatus?status=3"
                                    data-title="完成推荐"
                                    data-msg="确定这{0}条数据完成推荐？"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-success btn-sm">
                                <i class="fa fa-check"></i> 完成推荐
                            </button>
                        </c:if>
                    </shiro:hasPermission>
                    <c:if test="${status!=3}">
                        <shiro:hasPermission name="drOnline:del">
                            <button data-url="${ctx}/dr/drOnline_missDel?isDeleted=1"
                                    data-title="删除"
                                    data-msg="确定删除这{0}条数据移入已删除批次？"
                                    data-grid-id="#jqGrid"
                                    class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 删除
                            </button>
                        </shiro:hasPermission>
                    </c:if>
                </c:if>
                <c:if test="${isDeleted==1}">
                    <shiro:hasPermission name="drOnline:del">
                        <button data-url="${ctx}/dr/drOnline_missDel?isDeleted=0"
                                data-title="撤回"
                                data-msg="确定撤回这{0}条数据？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-success btn-sm">
                            <i class="fa fa-reply"></i> 撤回
                        </button>
                        <button data-url="${ctx}/dr/drOnline_batchDel"
                                data-title="删除"
                                data-msg="确定删除这{0}条民主推荐数据的相关数据吗？"
                                data-grid-id="#jqGrid"
                                class="jqBatchBtn btn btn-danger btn-sm"
                                title="该功能会删除该批次民主推荐的所有相关数据，请谨慎使用！">
                            <i class="fa fa-times"></i> 完全删除
                        </button>
                    </shiro:hasPermission>
                </c:if>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/dr/drOnline_data"
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
                            <label>年份</label>
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年份">
                        </div>
                        <div class="form-group">
                            <label>推荐日期</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="推荐日期范围">
                                            <span class="input-group-addon">
                                                <i class="fa fa-calendar bigger-110"></i>
                                            </span>
                                <input placeholder="请选择推荐日期范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_recommendDate" value="${param._recommendDate}"/>
                            </div>
                        </div>
                        <c:if test="${status!=3||isDeleted==1}">
                            <div class="form-group">
                                <label>状态</label>
                                <select name="status" data-width="150" data-rel="select2"
                                        data-placeholder="请选择状态">
                                    <option></option>
                                    <c:forEach items="<%=DrConstants.DR_ONLINE_MAP%>" var="status" begin="0" end="2">
                                        <option value="${status.key}">${status.value}</option>
                                    </c:forEach>
                                </select>
                                <script>
                                    $("#searchForm select[name=status]").val('${param.status}');
                                </script>
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>推荐类型</label>
                            <select data-width="230" name="type" data-rel="select2" data-placeholder="请选择">
                                <option></option>
                                <c:import url="/metaTypes?__code=mc_dr_type"/>
                            </select>
                            <script>         $("#searchForm select[name=type]").val('${param.type}');     </script>
                        </div>
                        <div class="form-group">
                            <label>推荐开始时间</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="开始时间范围">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar bigger-110"></i>
                                                </span>
                                <input placeholder="请选择开始时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_startTime" value="${param._startTime}"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label>推荐截止时间</label>
                            <div class="input-group tooltip-success" data-rel="tooltip" title="截止时间范围">
                                                <span class="input-group-addon">
                                                    <i class="fa fa-calendar bigger-110"></i>
                                                </span>
                                <input placeholder="请选择截止时间范围" data-rel="date-range-picker" class="form-control date-range-picker" type="text" name="_endTime" value="${param._endTime}"/>
                            </div>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dr/drOnline?isDeleted=${isDeleted}&cls=1<c:if test="${status==3}">&status=3</c:if>"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dr/drOnline?isDeleted=${isDeleted}&cls=1<c:if test="${status==3}">&status=3</c:if>"
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
            <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="5"></table>
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

    function addColor(rowId, val, rawObject, cm, rdata) {
        var now = new Date().format("yyyy-MM-dd HH:mm:ss");
        //console.log((rawObject.endTime)<(new Date().format("yyyy-MM-dd HH:mm:ss")))
        if (rawObject.endTime < now) {
            return "style='color:red'";
        }
    }
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/dr/drOnline_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年份',name: 'year', frozen:true, width:60},
                { label: '推荐日期',name: 'recommendDate',width:100, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}, frozen:true},
                { label: '编号',name: 'code', width:210,frozen: true},
                { label: '状态',name: 'status',formatter: function (cellvalue, options, rowObject) {

                    return _cMap.DR_ONLINE_MAP[cellvalue];
                }, frozen:true, width:80},
                { label: '参评人说明', name: '_notice',  width:150, formatter: function (cellvalue, options, rowObject) {
                    var str = '<button class="jqOpenViewBtn btn btn-primary btn-xs" data-url="${ctx}/dr/drOnline_noticeEdit?id={0}&isMobile=0"><i class="fa fa-desktop"></i> PC端</button>'
                        .format(rowObject.id)
                        + '&nbsp;&nbsp;<button class="jqOpenViewBtn btn btn-primary btn-xs" data-url="${ctx}/dr/drOnline_noticeEdit?id={0}&isMobile=1"><i class="glyphicon glyphicon-phone"></i> 手机端</button>'
                            .format(rowObject.id);
                    return  str;
                }},
                { label: '其他说明', name: '_otherNotice',  width:85, formatter: function (cellvalue, options, rowObject) {
                    var str = '<button class="jqOpenViewBtn btn btn-primary btn-xs" data-url="${ctx}/dr/drOnline_inspectorNotice?id={0}"><i class="glyphicon glyphicon-modal-window"></i> 纸质票</button>'
                        .format(rowObject.id);
                    return  str;
                }},
                {
                    label: '推荐职务', name: '_post', width:85, formatter: function (cellvalue, options, rowObject) {

                       return $.button.openView({
                            style:"btn-success",
                            url:"${ctx}/dr/drOnlinePost_menu?onlineId="+rowObject.id,
                            icon:"fa-list",
                            label:"查看"});
                }},
                {
                    label: '参评人<br/>账号管理', name: '_account', width:80, formatter: function (cellvalue, options, rowObject) {

                        return $.button.openView({
                            style:"btn-warning",
                            url:"${ctx}/dr/drOnlineInspectorLog_menu?onlineId="+rowObject.id,
                            icon:"fa-key",
                            label:"查看"});
                }},
                {
                    label: '推荐结果', name: '_result', formatter: function (cellvalue, options, rowObject) {

                        return $.button.openView({
                            style:"btn-info",
                            url:"${ctx}/dr/drOnline/drOnlineResult?onlineId="+rowObject.id,
                            icon:"fa-bar-chart",
                            label:"查看"});
                    }, width: 80
                },
                { label: '推荐类型',name: 'type', width: 105, formatter: $.jgrid.formatter.MetaType},
                { label: '推荐开始时间',name: 'startTime',width:130, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'}},
                { label: '推荐截止时间',name: 'endTime',width:130, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'},cellattr:addColor},


                {label: '推荐组负责人', name: 'chiefMember.user.realname', width: 100, formatter: function (cellvalue, options, rowObject) {

                        var val = $.trim(cellvalue);
                        if(val=='') return '--'
                        if(rowObject.chiefMember.status!='<%=DrConstants.DR_MEMBER_STATUS_NOW%>'){
                            return '<span class="delete">{0}</span>'.format(val)
                        }
                        return val;
                    }},
                {
                    label: '推荐组成员', name: 'drMembers', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == undefined || cellvalue.length == 0) return '--';
                        var names = []
                        cellvalue.forEach(function(drMember, i){
                            if (drMember.user.realname)
                                names.push(drMember.user.realname)
                        })

                        return names.join("，")
                    }, width: 250
                },
                { label: '备注', name: 'remark', width: 350, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>