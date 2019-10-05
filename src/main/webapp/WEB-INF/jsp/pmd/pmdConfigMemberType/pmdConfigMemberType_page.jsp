<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv rownumbers"
                 data-url-page="${ctx}/pmd/pmdConfigMemberType"
                 data-url-export="${ctx}/pmd/pmdConfigMemberType_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.type || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pmdConfigMemberType:edit">
                    <a class="popupBtn btn btn-info btn-sm"  data-url="${ctx}/pmd/pmdConfigMemberType_au"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pmd/pmdConfigMemberType_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="pmdConfigMemberType:del">
                    <button data-url="${ctx}/pmd/pmdConfigMemberType_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</a>
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
                            <label>类别</label>
                            <input class="form-control search-query" name="type" type="text" value="${param.type}"
                                   placeholder="请输入类别">
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
        rownumbers:true,
        url: '${ctx}/pmd/pmdConfigMemberType_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '党员类别',name: 'type', formatter:function(cellvalue, options, rowObject){
                return _cMap.PMD_MEMBER_TYPE_MAP[cellvalue];
            }, width:120},
            { label: '党员分类别',name: 'name', width:220},
            { label: '党费计算方式',name: 'pmdNorm.name', width:260},
            { label:'排序', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/pmd/pmdConfigMemberType_changeOrder"},frozen:true },
            { label: '备注',name: 'remark', formatter:function(cellvalue, options, rowObject){
                if(rowObject.isAuto) return "默认"
                return $.trim(cellvalue);
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>