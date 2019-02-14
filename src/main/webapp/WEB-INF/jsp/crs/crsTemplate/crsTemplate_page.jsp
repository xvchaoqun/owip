<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
                 data-url-page="${ctx}/crsTemplate"
                 data-url-export="${ctx}/crsTemplate_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="crsTemplate:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-width="750" data-url="${ctx}/crsTemplate_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/crsTemplate_au"
                       data-grid-id="#jqGrid"
                       data-width="750"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="crsTemplate:del">
                    <button data-url="${ctx}/crsTemplate_batchDel"
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
        url: '${ctx}/crsTemplate_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '类别',name: 'type', width: 200, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.CRS_TEMPLATE_TYPE_MAP[cellvalue];
            }},
            { label: '模板名称',name: 'name', width: 400, align:'left'}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid",function(){
        $(window).triggerHandler('resize.jqGrid');
    })
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>