<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/pcs/constants.jsp" %>
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
            <div class="tab-content padding-8">
                <c:set var="_query" value="${not empty param.username ||not empty param.isPositive}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <c:if test="${!pcsPoll.hasReport}">
                        <shiro:hasPermission name="pcsPollInspector:edit">
                            <button class="popupBtn btn btn-info btn-sm tooltip-success"
                                    data-url="${ctx}/pcs/pcsPollInspector_au?pollId=${param.pollId}" data-rel="tooltip" >
                                <i class="fa fa-plus"></i> 生成帐号</button>
                            <a class="btn btn-primary btn-sm" target="_blank"
                               href="${ctx}/pcs/pcsPollInspector_print?pollId=${param.pollId}"
                               data-rel="tooltip" title="打印选中记录或所有搜索记录结果">
                                <i class="fa fa-print"></i> 打印
                            </a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="pcsPollInspector:del">
                            <button data-url="${ctx}/pcs/pcsPollInspector_batchDel?pollId=${param.pollId}"
                                    data-title="删除"
                                    data-msg="确定删除这{0}个账号？（如果账号已完成投票，则相应的投票结果数据将同时删除，不可恢复，请谨慎操作！）"
                                    data-grid-id="#jqGrid2"
                                    class="jqBatchBtn btn btn-danger btn-sm">
                                <i class="fa fa-trash"></i> 删除
                            </button>
                        </shiro:hasPermission>

                    <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                            data-url="${ctx}/pcs/pcsPollInspector_data?pollId=${param.pollId}"
                            data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果"
                            data-grid-id="#jqGrid2">
                        <i class="fa fa-download"></i> 导出</button>
                    <span style='font-size:14.0pt;font-weight:bolder;padding-left: 20px;'>
                        <a href="${homeURL}/ddh" target="_blank" style="border-bottom: 2px solid;">点此查看登录地址</a>
                    </span>
                        </c:if>
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
                            <form class="form-inline search-form" id="searchForm2">
                                <input type="hidden" name="pollId" value="${param.pollId}"/>
                                <div class="form-group">
                                    <label>登录账号</label>
                                    <input class="form-control search-query" name="username" type="text" value="${param.username}"
                                           placeholder="请输入登录账号">
                                </div>
                                <div class="form-group">
                                    <label>投票人身份</label>
                                    <div class="input-group">
                                        <select required data-rel="select2" name="isPositive" data-width="150"
                                                data-placeholder="请选择">
                                            <option></option>
                                            <option value="1">正式党员</option>
                                            <option value="0">预备党员</option>
                                            <option value="-1">未完成投票</option>
                                        </select>
                                    </div>
                                </div>
                                <script> $("#searchForm2 select[name=isPositive]").val(${param.isPositive}) </script>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"
                                       data-url="${ctx}/pcs/pcsPollInspector"
                                       data-target="#body-content-view"
                                       data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                data-url="${ctx}/pcs/pcsPollInspector?pollId=${param.pollId}"
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
                <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="51"></table>
                <div id="jqGridPager2"></div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers:true,
        url: '${ctx}/pcs/pcsPollInspector_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '登录账号',name: 'username'},
            { label: '登录密码',name: 'passwd'},
            { label: '创建时间',name: 'createTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y.m.d H:i:s',newformat: 'Y.m.d H:i:s'}},
            { label: '是否完成投票',name: 'isFinished',formatter:$.jgrid.formatter.TRUEFALSE},

            <shiro:hasPermission name="${PERMISSION_PCSADMIN}">
            { label: '投票详情',name: '_detail', width:80, formatter: function (cellvalue, options, rowObject) {

                if(!rowObject.isFinished) return '--'
                return '<button class="popupBtn btn btn-warning btn-xs" data-width="500" data-callback="_reload"' +
                    'data-url="${ctx}/pcs/pcspollInspector_Result?id={0}"><i class="fa fa-search"></i> 查看</button>'
                        .format(rowObject.id)
                }},
            </shiro:hasPermission>
            { label: '投票人身份',name: 'isPositive', formatter: $.jgrid.formatter.TRUEFALSE, formatoptions:{on: '正式党员', off: '预备党员'}},
            { label: '提交时间',name: 'submitTime', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {srcformat:'Y.m.d H:i:s',newformat: 'Y.m.d H:i:s'}},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $.register.ajax_select($('[data-rel="select2-ajax"]'));
    $('#searchForm2 [data-rel="select2"]').select2();
    $('#searchForm2 [data-rel="tooltip"]').tooltip();
    $.register.date($('.date-picker'));
</script>