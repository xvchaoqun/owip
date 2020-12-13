<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param._meetingMonth}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pm3Guide:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/pm/pm3Guide_au">
                        <i class="fa fa-plus"></i> 上传生活指南</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pm/pm3Guide_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                    <button data-url="${ctx}/pm/pm3Guide_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
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
                            <label>活动年月</label>
                            <input required name="_meetingMonth" class="form-control date-picker" data-date-min-view-mode="1"
                                   data-date-format="yyyy-mm" type="text">
                            <script>
                                $('#searchForm input[name=_meetingMonth]').val('${param._meetingMonth}')
                            </script>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/pm/pm3Guide"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/pm/pm3Guide"
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
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pm/pm3Guide_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '活动年月',name: 'meetingMonth',
                width:'150',
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m'}},
            { label: '报送时间',name: 'reportTime',
                width: '200',
                formatter: $.jgrid.formatter.date,
                formatoptions: {srcformat: 'Y-m-d H:i', newformat: 'Y.m.d H:i'}},
            { label: '组织生活指南',name: '_files',width:'110',formatter:function(cellvalue, options, rowObject){
                    return '<button class="popupBtn btn btn-info btn-xs" data-width="550"' +
                        'data-url="${ctx}/pm/pm3Guide_files?id={0}"><i class="fa fa-search"></i> 详情</button>'
                            .format(rowObject.id)
                }},
            <shiro:hasPermission name="pm3Guide:edit">
                { label: '未完成分党委',name: '_files',width:'115',formatter:function(cellvalue, options, rowObject){
                        return '<button class="popupBtn btn btn-warning btn-xs" data-width="510"' +
                            'data-url="${ctx}/pm/pm3Guide_notice?id={0}"><i class="fa fa-info-circle"></i> 提醒</button>'
                                .format(rowObject.id)
                    }},
            </shiro:hasPermission>
            <shiro:lacksPermission name="pm3Guide:edit">
            <c:if test="${cm:hasRole(ROLE_PARTYADMIN)}">
                { label: '未完成党支部',name: '_files',width:'115',formatter:function(cellvalue, options, rowObject){
                        return '<button class="popupBtn btn btn-warning btn-xs" data-width="510"' +
                            'data-url="${ctx}/pm/pm3Guide_notice?id={0}&partyIds=${partyIds}"><i class="fa fa-info-circle"></i> 提醒</button>'
                                .format(rowObject.id)
                    }},
            </c:if>
            </shiro:lacksPermission>
            { label: '备注', name: 'remark',width:'252'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.date($('.date-picker'));
</script>