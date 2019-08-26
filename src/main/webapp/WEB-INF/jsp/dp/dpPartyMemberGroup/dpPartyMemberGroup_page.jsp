<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.id ||not empty param.partyId || not empty param.code || not empty param.sort
            ||not empty param._appointTime ||not empty param._tranTime}"/>
           <div class="tabbable">
               <jsp:include page="menu.jsp"/>
               <div class="tab-content">
                   <div class="tab-pane in active">
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="dpPartyMemberGroup:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/dp/dpPartyMemberGroup_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/dp/dpPartyMemberGroup_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改信息</button>
                <c:if test="${status>=0}">
                    <shiro:hasPermission name="dpPartyMemberGroup:realDel">
                        <div class="btn-group">
                            <button data-toggle="dropdown"
                                    data-rel="tooltip" data-placement="top" data-html="true"
                                    title="<div style='width:180px'>批量导入操作</div>"
                                    class="btn btn-info btn-sm dropdown-toggle tooltip-success">
                                <i class="fa fa-hand-o-right"></i> 批量导入 <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-success" role="menu" style="z-index: 1031">
                                <li>
                                    <a href="javascript:;" class="popupBtn"
                                       data-url="${ctx}/dp/dpPartyMemberGroup_import">
                                        <i class="fa fa-upload"></i> 批量导入委员会</a>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <a href="javascript:;" class="popupBtn"
                                       data-url="${ctx}dp/dpPartyMember_import">
                                        <i class="fa fa-upload"></i> 批量导入委员会成员</a>
                                </li>
                            </ul>
                        </div>
                    </shiro:hasPermission>
                </c:if>
                </shiro:hasPermission>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/dp/dpPartyMemberGroup_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
                <c:if test="${status>=0}">
                    <shiro:hasPermission name="dpPartyMemberGroup:del">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dp/dpPartyMemberGroup_batchDel" data-title="撤销委员会"
                           data-msg="确定撤销这{0}个委员会吗？"><i class="fa fa-history"></i> 撤销</a>
                        【注：撤销操作将同时删除相关管理员，请谨慎操作！】
                    </shiro:hasPermission>
                </c:if>
                <c:if test="${status==-1}">
                    <shiro:hasPermission name="dpPartyMemberGroup:realDel">
                        <a class="jqBatchBtn btn btn-danger btn-sm"
                           data-url="${ctx}/dp/dpPartyMemberGroup_realDel"
                           data-title="删除领导班子"
                           data-msg="确定完全删除这{0}个委员会吗？（不可恢复，请谨慎操作！）"><i class="fa fa-times"></i> 完全删除</a>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="dpPartyMemberGroup:del">
                        <a class="jqBatchBtn btn btn-success btn-sm"
                           data-url="${ctx}/dp/dpPartyMemberGroup_batchDel"
                           data-querystr="isDeleted=0"
                           data-title="恢复已删除委员会"
                           data-msg="确定恢复这{0}个委员会吗？"><i class="fa fa-reply"></i> 恢复</a>
                        【注：恢复操作之后需要重新设置相关管理员！】
                    </shiro:hasPermission>
                </c:if>
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
                            <input type="hidden" name="cls" value="${status}">
                            <div class="form-group">
                                <label>所在委员会</label>
                                <select  data-width="300" data-rel="select2-ajax"
                                         data-ajax-url="${ctx}/dp/dpPartyMemberGroup_selects"
                                         name="id" data-placeholder="请选择">
                                    <option value="${param.id}">${param.name}</option>
                                </select>
                                <script>         $.register.del_select($("#searchForm select[name=id]"), 300)     </script>
                            </div>
                            <div class="form-group">
                                <label>所在民主党派</label>
                                <select  data-width="300" data-rel="select2-ajax"
                                         data-ajax-url="${ctx}/dp/dpParty_selects"
                                         name="partyId" data-placeholder="请选择">
                                    <option value="${dpParty.id}">${dpParty.name}</option>
                                </select>
                                <script>         $.register.del_select($("#searchForm select[name=partyId]"), 300)     </script>
                            </div>
                            <div class="form-group">
                                <label>任命时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="任命时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                    <input placeholder="请选择任命时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                           type="text" name="_appointTime" value="${param._appointTime}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>应换届时间</label>
                                <div class="input-group tooltip-success" data-rel="tooltip" title="应换届时间范围">
                                                                <span class="input-group-addon">
                                                                    <i class="fa fa-calendar bigger-110"></i>
                                                                </span>
                                    <input placeholder="请选择应换届时间范围" data-rel="date-range-picker" class="form-control date-range-picker"
                                           type="text" name="_tranTime" value="${param._tranTime}"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>是否现任</label>
                                <select name="isPresent" data-width="80"
                                        data-rel="select2" data-placeholder="请选择">
                                    <option></option>
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                                <script>
                                    $("#searchForm select[name=isPresent]").val('${param.isPresent}');
                                </script>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/dp/dpPartyMemberGroup"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/dp/dpPartyMemberGroup"
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
                       <div id="jqGridPager"></div></div>
                   </div></div></div></div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/dp/dpPartyMemberGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                {
                label: '名称',
                name: 'name',
                align: 'left',
                width: 450,
                formatter: function (cellvalue, options, rowObject) {
                    var str = '<span class="label label-sm label-primary" style="display: inline!important;"> 当届委员会</span>&nbsp;';
                    return (rowObject.isPresent) ? str + cellvalue : cellvalue;
                },
                frozen: true
                },
                {
                label: '查看委员', name: 'memberCount', width: 110, formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-warning btn-xs" ' +
                        'data-url="${ctx}/dp/dpPartyMember_menu?groupId={0}">'
                        + '<i class="fa fa-search"></i> 查看委员</button>')
                        .format(rowObject.id);
                }
                },
                {
                label: '导出委员', name: 'courseNum', formatter: function (cellvalue, options, rowObject) {
                    if (rowObject.isPresent)
                        return ('<button class="downloadBtn btn btn-primary btn-xs" ' +
                            'data-url="${ctx}/dp/dpPartyMember?export=1&groupId={0}"><i class="fa fa-file-excel-o"></i> 导出委员</a>')
                            .format(rowObject.id);
                    return '--'
                }
                },
            {
                label: '所属民主党派',
                name: 'dpParty.name',
                width: 300, formatter: function (cellvalue, options, rowObject) {
                    var _dpPartyView = null;
                    if ($.inArray("dpParty:list", _permissions) >= 0 || $.inArray("dpParty:*", _permissions) >= 0)
                        _dpPartyView = '<a href="javascript:;" class="openView" data-url="{2}/dp/dpParty_view?id={0}">{1}</a>'
                            .format(rowObject.partyId, cellvalue, ctx);
                    if (cellvalue != ''){
                        return '<span class="{0}">{1}</span>'.format(rowObject.isDeleted ? "delete" : "", _dpPartyView);
                    }
                    return "--";
                }
            },
            {label: '委员会届数', name: 'groupSession', width: 100},
            {label: '任命时间', name: 'appointTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {
                hidden: true, name: 'isPresent', formatter: function (cellvalue, options, rowObject) {
                    return (rowObject.isPresent) ? 1 : 0;
                }
            },
            {label: '应换届时间', name: 'tranTime', width: 130,
                formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'},
                cellattr: function (rowId, val, rowObject, cm, rdata) {
                    if (rowObject.isPresent &&
                        rowObject.tranTime <= new Date().format('yyyy.MM.dd'))
                        return "class='danger'";
                }
            },
            {
                label: '实际换届时间',
                name: 'actualTranTime',
                width: 130,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            }
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('[data-rel="select2-ajax"]'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>