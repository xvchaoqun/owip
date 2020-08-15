<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=PcsConstants.PCS_POLL_STAGE_MAP%>" var="PCS_POLL_STAGE_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name ||not empty param.partyId ||not empty param.branchId ||not empty param.configName ||not empty param.isSecond ||not empty param.hasReport}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>
                    <div class="tab-content multi-row-head-table">
                        <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pcsPoll:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/pcs/pcsPoll_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pcs/pcsPoll_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/pcs/pcsPoll_report"
                            data-title="报送"
                            data-msg="确定报送这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-success btn-sm">
                        <i class="fa fa-circle-o"></i> 报送
                    </button>
                </shiro:hasPermission>
                <shiro:hasPermission name="pcsPoll:del">
                    <button data-url="${ctx}/pcs/pcsPoll_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/pcs/pcsPoll_data"
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
                            <label>投票名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入投票名称">
                        </div>
                            <div class="form-group">
                                <label>所在${_p_partyName}</label>
                                <select class="form-control" data-width="350" data-rel="select2-ajax"
                                        data-ajax-url="${ctx}/party_selects?auth=1"
                                        name="partyId" data-placeholder="请选择">
                                    <option value="${party.id}" delete="${party.isDeleted}">${party.name}</option>
                                </select>
                            </div>
                            <div class="form-group" style="${(empty branch)?'display: none':''}"
                                 id="branchDiv">
                                <label>所在党支部</label>
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
                            <div class="form-group">
                                <label>党代会</label>
                                <input class="form-control search-query" name="configName" type="text" value="${param.configName}"
                                       placeholder="请输入党代会名称">
                            </div>
                        <div class="form-group">
                            <label>投票阶段</label>
                            <select data-rel="select2" name="isSecond" data-placeholder="请选择" data-width="120">
                                <option></option>
                                <c:forEach items="${PCS_POLL_STAGE_MAP}" var="entry">
                                    <option value="${entry.key}">${entry.value}</option>
                                </c:forEach>
                            </select>
                            <script> $("#searchForm select[name=isSecond]").val(${param.isSecond}) </script>
                        </div>
                        <div class="form-group">
                            <label>是否报送</label>
                            <select data-rel="select2" name="hasReport" data-placeholder="请选择" data-width="120">
                                <option></option>
                                <option value="1">已报送</option>
                                <option value="0">未报送</option>
                            </select>
                            <script> $("#searchForm select[name=hasReport]").val(${param.hasReport}) </script>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pcs/pcsPoll"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pcs/pcsPoll"
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
            <table id="jqGrid" class="jqGrid table-striped" data-height-reduce="1"></table>
            <div id="jqGridPager"></div>
                        </div></div></div></div>
        <div id="body-content-view"></div>
    </div>
</div>
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
        url: '${ctx}/pcs/pcsPoll_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '投票阶段',name: 'isSecond', formatter: $.jgrid.formatter.TRUEFALSE, formatoptions:{on: '二下阶段', off: '一下阶段'}, frozen: true},
            { label: '投票名称',name: 'name',align:'left', width: 252, frozen: true},
            { label: '是否报送',name: 'hasReport', formatter: $.jgrid.formatter.TRUEFALSE, formatoptions:{on: '已报送', off: '未报送'}},
            { label: '党代会投票说明',name: '_notice',  width:150, formatter: function (cellvalue, options, rowObject) {
                    var str = '<button class="jqOpenViewBtn btn btn-primary btn-xs" data-url="${ctx}/pcs/pcsPoll_noticeEdit?id={0}&isMobile=0"><i class="fa fa-desktop"></i> PC端</button>'
                            .format(rowObject.id)
                        + '&nbsp;&nbsp;<button class="jqOpenViewBtn btn btn-primary btn-xs" data-url="${ctx}/pcs/pcsPoll_noticeEdit?id={0}&isMobile=1"><i class="glyphicon glyphicon-phone"></i> 手机端</button>'
                            .format(rowObject.id);
                    return  str;
            }},
            /*{ label: '其他说明',name: '_otherNotice',  width:85, formatter: function (cellvalue, options, rowObject) {
                var str = '<button class="jqOpenViewBtn btn btn-primary btn-xs" data-url="${ctx}/pcs/pcsPoll_noticeEdit?id={0}"><i class="glyphicon glyphicon-modal-window"></i> 纸质票</button>'
                    .format(rowObject.id);
                return  str;
            }},*/
            { label: '投票<br/>账号管理',name: '_inspector', width:80, formatter: function (cellvalue, options, rowObject) {

                    return $.button.openView({
                        style:"btn-warning",
                        url:"${ctx}/pcs/pcsPollInspector?pollId="+rowObject.id,
                        icon:"fa-key",
                        label:"查看"});
                }},
            {
                label: '投票结果', name: '_result', formatter: function (cellvalue, options, rowObject) {

                    return $.button.openView({
                        style:"btn-info",
                        url:"${ctx}/pcs/pcsPollResult?pollId="+rowObject.id,
                        icon:"fa-bar-chart",
                        label:"查看"});
                }, width: 80
            },
            { label: '所属党代会',name: 'pcsConfig.name',align:'left', width: 252},
            { label: '所属${_p_partyName}',name: 'partyId',align:'left', width: 300, formatter:function(cellvalue, options, rowObject){
                return $.party(rowObject.partyId);
            }},
            { label: '所属支部',name: 'branchId',align:'left', width: 300 ,  formatter:function(cellvalue, options, rowObject){
                    return $.party(null,rowObject.branchId);
                }},


            { label: '代表的<br/>最大推荐人数',name: 'prNum'},
            { label: '党委委员的<br/>最大推荐人数',name: 'dwNum'},
            { label: '纪委委员的<br/>最大推荐人数',name: 'jwNum'},

            { label: '推荐人管理',name: '_candidate', width:80, formatter: function (cellvalue, options, rowObject) {
                    if (!rowObject.isSecond) return "--";
                    return $.button.openView({
                        style:"btn-success",
                        url:"${ctx}/pcs/pcsPollCandidate?pollId="+rowObject.id,
                        icon:"fa-list",
                        label:"查看"});
                }},

            { label: '投票起始时间',name: 'startTime',width:130, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'}},
            { label: '投票截止时间',name: 'endTime',width:130, formatter: $.jgrid.formatter.date, formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'},cellattr:addColor},
            { label: '备注',name: 'remark',width: 252}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
</script>