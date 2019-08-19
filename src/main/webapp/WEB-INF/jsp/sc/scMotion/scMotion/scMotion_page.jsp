<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=SystemConstants.UNIT_POST_STATUS_DELETE%>" var="UNIT_POST_STATUS_DELETE"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scMotion?cls=1"><i class="fa fa-sliders"></i> 单个岗位调整</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scMotion?cls=2"><i class="fa fa-star"></i> 党委班子换届</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/sc/scMotion?cls=3"><i class="fa fa-link"></i> 行政班子换届</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <c:set var="_query" value="${not empty param.year ||not empty param.unitId ||not empty param.unitPostId}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="scMotion:edit">
                    <button class="popupBtn btn btn-info btn-sm"
                            data-url="${ctx}/sc/scMotion_au">
                        <i class="fa fa-plus"></i> 添加
                    </button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                            data-url="${ctx}/sc/scMotion_au"
                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改
                    </button>
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
                                <div class="input-group" style="width: 150px">
                                    <input class="form-control date-picker" placeholder="请选择年份"
                                           name="year"
                                           type="text"
                                           data-date-format="yyyy" data-date-min-view-mode="2"
                                           value="${param.year}"/>
                                    <span class="input-group-addon"> <i
                                            class="fa fa-calendar bigger-110"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>所属单位</label>
                                <select name="unitId" data-rel="select2-ajax" data-ajax-url="${ctx}/unit_selects"
                                        data-placeholder="请选择">
                                    <option value="${unit.id}" delete="${unit.status==UNIT_STATUS_HISTORY}">${unit.name}</option>
                                </select>
                                <script>
                                    $.register.del_select($("#searchForm select[name=unitId]"))
                                </script>
                            </div>
                            <div class="form-group">
                                <label class="col-xs-3 control-label">拟调整岗位</label>
                                <div class="col-xs-6">
                                    <select name="unitPostId" data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/unitPost_selects"
                                            data-placeholder="请选择">
                                        <option value="${unitPost.id}" delete="${unitPost.status==UNIT_POST_STATUS_DELETE}">${unitPost.code}-${unitPost.name}</option>
                                    </select>
                                    <script>
                                        $.register.del_select($("#searchForm select[name=unitPostId]"))
                                    </script>
                                </div>
                            </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sc/scMotion"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
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
        rownumbers: true,
        url: '${ctx}/sc/scMotion_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen:true},
            {label: '动议编号', name: 'code', width: 200, frozen:true},
            {label: '动议记录', name: '_log', width: 90, formatter: function (cellvalue, options, rowObject) {

                    if (rowObject.way == '<%=ScConstants.SC_MOTION_WAY_OTHER%>') {
                        return "--"
                    }

                    return ('<button class="popupBtn btn {1} btn-xs" data-width="1200" ' +
                        'data-url="${ctx}/sc/scMotion_topics?id={0}"><i class="fa {3}"></i> {2}</button>')
                        .format(rowObject.id,
                            rowObject.topics==undefined?'btn-primary':'btn-success',
                            rowObject.topics==undefined?'编辑':'查看',
                            rowObject.topics==undefined?'fa-edit':'fa-search');
                }, frozen:true},
            {label: '动议日期', name: 'holdDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m.d'}},
            {label: '拟调整岗位',name: 'postName', align:'left', width: 300, frozen:true},
            {label: '分管工作', align:'left', name: 'job', width: 200 },
            {label: '行政级别', name: 'adminLevel', width: 85, formatter: $.jgrid.formatter.MetaType},
            {label: '职务属性', name: 'postType', width: 120, formatter: $.jgrid.formatter.MetaType},
            /*{ label: '纪实',name: '_null', width:80},*/
            /*{
                label: '详情', name: '_detail', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/sc/scMotionPost?motionId={0}"><i class="fa fa-search"></i> 详情</button>')
                            .format(rowObject.id);
                }},*/
            {label: '所属单位', name: 'unitId', width: 200, align: 'left', formatter: $.jgrid.formatter.unit},
            {label: '单位类型', name: 'unitType', width: 120, frozen: true, formatter: $.jgrid.formatter.MetaType},
            {label: '选任方式', name: 'scType', width: 120, formatter: $.jgrid.formatter.MetaType},
            {
                label: '动议主体', name: 'way', width: 150, formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--'
                    if (cellvalue == '<%=ScConstants.SC_MOTION_WAY_OTHER%>') {
                        return "其他：" + rowObject.wayOther
                    }
                    return _cMap.SC_MOTION_WAY_MAP[cellvalue]
                }
            },

            {label: '备注', name: 'remark', width: 350, align: 'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>