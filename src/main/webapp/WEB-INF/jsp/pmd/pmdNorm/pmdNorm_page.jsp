<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="rownumbers myTableDiv"
                 data-url-page="${ctx}/pmd/pmdNorm"
                 data-url-export="${ctx}/pmd/pmdNorm_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="pmdNorm:edit">
                    <a class="popupBtn btn btn-info btn-sm"
                       data-url="${ctx}/pmd/pmdNorm_au?type=${param.type}"><i class="fa fa-plus"></i> 添加</a>
                    <a class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/pmd/pmdNorm_au"
                       data-grid-id="#jqGrid"
                       ><i class="fa fa-edit"></i>
                        修改</a>
                </shiro:hasPermission>
                <shiro:hasPermission name="pmdNorm:del">
                    <button data-url="${ctx}/pmd/pmdNorm_batchDel"
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
                        <a href="#" data-action="collapse">
                            <i class="ace-icon fa fa-chevron-${_query?'up':'down'}"></i>
                        </a>
                    </div>
                </div>
                <div class="widget-body">
                    <div class="widget-main no-padding">
                        <form class="form-inline search-form" id="searchForm">
                            <input type="hidden" name="type" value="${type}">
                        <div class="form-group">
                            <label>标准名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入标准名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>

                                <c:if test="${_query}">&nbsp;
                                    <button type="button"
                                            class="resetBtn btn btn-warning btn-sm"
                                            data-querystr="type=${type}">
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
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/pmd/pmdNorm_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '${PMD_NORM_TYPE_MAP.get(type)}',name: 'name', align:'left', width:260},
            { label: '额度设定类型',name: 'setType', formatter:function(cellvalue, options, rowObject){
                return _cMap.PMD_NORM_SET_TYPE_MAP[cellvalue];
            }, width:120},
            { label: '公式类型',name: 'formulaType', formatter:function(cellvalue, options, rowObject){
                if(rowObject.setType!='${PMD_NORM_SET_TYPE_FORMULA}') return '-'
                return _cMap.PMD_FORMULA_TYPE_MAP[cellvalue];
            }, width:200},
            <c:if test="${!_query}">
            {
                label: '排序', align: 'center', index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/pmd/pmdNorm_changeOrder"},frozen:true
            },
            </c:if>
            { label: '额度', name: 'pmdNormValue.amount', formatter:function(cellvalue, options, rowObject){
                if(rowObject.setType!='${PMD_NORM_SET_TYPE_FIXED}') return '-'
                if(cellvalue==undefined) return '未设定额度'
                return cellvalue;
            }},
            { label: '额度管理',name: '_amount', formatter:function(cellvalue, options, rowObject){
                if(rowObject.setType!='${PMD_NORM_SET_TYPE_FIXED}') return '-'
                return ('<button class="popupBtn btn btn-primary btn-xs" ' +
                        'data-url= "${ctx}/pmd/pmdNormValue?normId={0}" ><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }},
            { label: '启用日期',name: 'startTime', width:120, formatter:function(cellvalue, options, rowObject){
                if(rowObject.status!='${PMD_NORM_STATUS_INIT}'){
                    return $.date(cellvalue, "yyyy-MM-dd");
                }
                return '-';
            }},
            { label: '作废日期',name: 'endTime', width:120, formatter:function(cellvalue, options, rowObject){
                if(rowObject.status=='${PMD_NORM_STATUS_ABOLISH}'){
                    return $.date(cellvalue, "yyyy-MM-dd");
                }
                return '-';
            }},
            { label: '状态',name: 'status', formatter:function(cellvalue, options, rowObject){
                var css = "text-danger";
                if(rowObject.status=='${PMD_NORM_STATUS_USE}') css = "text-success";

                return '<span class="{0}">{1}</span>'.format(css, _cMap.PMD_NORM_STATUS_MAP[cellvalue]);
            }},
            { label: '启用',name: '_use', formatter:function(cellvalue, options, rowObject){
                if(rowObject.setType=='${PMD_NORM_SET_TYPE_FIXED}' && rowObject.pmdNormValue==undefined) return '-'

                if(rowObject.status=='${PMD_NORM_STATUS_INIT}'){
                    return ('<button class="confirm btn btn-success btn-xs" ' +
                    'data-title="启用" data-msg="确定启用？" data-callback="_reload"' +
                    'data-url= "${ctx}/pmd/pmdNorm_use?id={0}" ><i class="fa fa-check"></i> 启用</button>')
                            .format(rowObject.id);
                }
                return '-'
            }},
            { label: '作废',name: '_abolish', formatter:function(cellvalue, options, rowObject){
                if(rowObject.status=='${PMD_NORM_STATUS_USE}'){
                    return ('<button class="confirm btn btn-danger btn-xs" ' +
                    'data-title="作废" data-msg="确定作废？" data-callback="_reload"' +
                    'data-url= "${ctx}/pmd/pmdNorm_abolish?id={0}" ><i class="fa fa-times"></i> 作废</button>')
                            .format(rowObject.id);
                }
                return '-'
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>