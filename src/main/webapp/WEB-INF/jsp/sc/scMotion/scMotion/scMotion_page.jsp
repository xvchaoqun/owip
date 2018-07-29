<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.unitId ||not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scMotion:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/sc/scMotion_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sc/scMotion_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="scMotion:del">
                    <button data-url="${ctx}/sc/scMotion_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sc/scMotion_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <label>年份</label>
                            <input class="form-control search-query" name="year" type="text" value="${param.year}"
                                   placeholder="请输入年份">
                        </div>
                        <div class="form-group">
                            <label>所属单位</label>
                            <input class="form-control search-query" name="unitId" type="text" value="${param.unitId}"
                                   placeholder="请输入所属单位">
                        </div>
                        <div class="form-group">
                            <label>动议事项</label>
                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                   placeholder="请输入动议事项">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scMotion"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="resetBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sc/scMotion"
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
        url: '${ctx}/sc/scMotion_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label: '年份',name: 'year', width:80},
                { label: '动议日期',name: 'holdDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
                { label: '动议编号',name: 'code', width:150, align:'left'},
                { label: '纪实',name: '_null', width:80},
                {
                    label: '详情', name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                    return ('<button class="openView btn btn-success btn-xs" ' +
                    'data-url="${ctx}/sc/scMotionPost?motionId={0}"><i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
                }},
                { label: '单位',name: 'unitId', width: 250, align:'left', formatter: $.jgrid.formatter.unit},
                { label: '部门属性',name: 'unitId', width: 150, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                    var unit = _cMap.unitMap[cellvalue]
                    if(unit==undefined) return '-'
                    return $.jgrid.formatter.MetaType(unit.typeId)
                }},
                { label: '动议事项',name: 'type', width: 150, formatter:$.jgrid.formatter.MetaType},
                { label: '拟调整岗位数',name: 'postCount', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue>0){
                        return '<a href="javascript:;" class="popupBtn" data-url="${ctx}/sc/scMotion_posts?motionId={0}">{1}</a>'
                                .format(rowObject.id, cellvalue)
                    }else{
                        return cellvalue;
                    }
                }},
                { label: '动议形式',name: 'way', width: 150, formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined) return '-'
                    if(cellvalue=='<%=ScConstants.SC_MOTION_WAY_OTHER%>'){
                        return "其他："+ rowObject.wayOther
                    }
                    return _cMap.SC_MOTION_WAY_MAP[cellvalue]
                }},
                { label: '干部选任方式',name: 'scType', width: 120, formatter:$.jgrid.formatter.MetaType},
                { label: '备注',name: 'remark', width: 350, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>