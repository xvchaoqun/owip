<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/crsPostRequire"
                 data-url-export="${ctx}/crsPostRequire_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="crsPostRequire:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/crsPostRequire_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/crsPostRequire_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="crsPostRequire:del">
                    <button data-url="${ctx}/crsPostRequire_batchDel"
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

                    <div class="widget-toolbar">
                        <a href="javascript:;" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                        <div class="form-group">
                            <label>模板名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入模板名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm">
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
        url: '${ctx}/crsPostRequire_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '模板名称',name: 'name', width: 400, align:'left', frozen: true},

            {
                label: '排序', width: 90, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/crsPostRequire_changeOrder"}, frozen: true
            },
            {label: '岗位要求', name: '_ruleNum', formatter: function (cellvalue, options, rowObject) {

                var num = rowObject.rules.length;
                if (num==0)
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/crsRequireRule?postRequireId={0}">编辑</a>'
                            .format(rowObject.id);
                else
                    return '<a href="javascript:void(0)" class="openView" data-url="${ctx}/crsRequireRule?postRequireId={0}">查看({1})</a>'
                            .format(rowObject.id, num);
            }},
            {label: '预览', name: 'rankNum', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.normNum==0||rowObject.rankNum==0) return '-'
                return '<a href="javascript:void(0)" class="popupBtn" data-url="${ctx}/crsPostRequire_preview?id={0}">预览</a>'
                        .format(rowObject.id);
            }, width: 80},
            { label: '备注',name: 'remark', width: 300, frozen: true}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    $.initNavGrid("jqGrid", "jqGridPager");

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>