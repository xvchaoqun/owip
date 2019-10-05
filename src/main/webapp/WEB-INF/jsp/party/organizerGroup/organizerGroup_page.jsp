<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_ORGANIZER_STATUS_NOW" value="<%=OwConstants.OW_ORGANIZER_STATUS_NOW%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name ||not empty param.unitId
            ||not empty param.userId || not empty param.code || not empty param.sort}"/>

            <jsp:include page="../organizer/menu.jsp"/>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="organizerGroup:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/organizerGroup_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/organizerGroup_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="organizerGroup:del">
                    <button data-url="${ctx}/organizerGroup_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>

                <shiro:hasPermission name="organizerGroup:edit">
                    <button class="jqOpenViewBtn btn btn-info btn-sm"
                       data-url="${ctx}/organizerGroupUser"
                            data-id-name="groupId"
                       data-grid-id="#jqGrid"><i class="fa fa-users"></i>
                        设置小组成员</button>
                    <button class="jqOpenViewBtn btn btn-warning btn-sm"
                       data-url="${ctx}/organizerGroupUnit"
                            data-id-name="groupId"
                       data-grid-id="#jqGrid"><i class="fa fa-sitemap"></i>
                        设置联系单位</button>
                </shiro:hasPermission>

                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/organizerGroup_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                        <div class="form-group">
                            <label>组别</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入">
                        </div>
                        <div class="form-group">
                            <label>组织员姓名</label>
                            <select data-rel="select2-ajax"
                                    data-ajax-url="${ctx}/organizer_selects?type=${OW_ORGANIZER_TYPE_SCHOOL}"
                                    name="userId" data-placeholder="请选择成员">
                                <option value="${organizer.id}" delete="${organizer.status!=OW_ORGANIZER_STATUS_NOW}">${organizer.user.realname} - ${organizer.user.code}</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>联系的单位</label>
                             <select data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                    name="unitId" data-placeholder="请选择">
                                <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                            </select>
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/organizerGroup?cls=${cls}&type=${type}"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/organizerGroup?cls=${cls}&type=${type}"
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
        url: '${ctx}/organizerGroup_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '校级组织员小组',name: 'name', align:'left', width:300},
                { label: '小组成员',name: 'organizers', align:'left', formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return'--'
                        return ($.map(cellvalue.split(","), function(u){
                            return u.split("|")[0];
                        })).join("、")
                    }, width:300},
                { label: '联系单位',name: 'units', align:'left', formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return'--'
                        return ($.map(cellvalue.split(","), function(u){
                            return u.split("|")[0];
                        })).join("、")
                    }, width:700},
                { label: '备注',name: 'remark', width:200}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.user_select($('#searchForm select[name=userId]'));
    $.register.ajax_select($('#searchForm select[name=unitId]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>