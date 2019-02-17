<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="myTableDiv"
             data-url-au="${ctx}/unit_au"
             data-url-page="${ctx}/unit"
             data-url-export="${ctx}/unit_data"
             data-url-del="${ctx}/unit_del"
             data-url-bd="${ctx}/unit_batchDel"
             data-url-co="${ctx}/unit_changeOrder?status=${status}"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.name
            ||not empty param.typeId || not empty param.sort}"/>
            <div class="tabbable">
                <jsp:include page="menu.jsp"/>
                <div class="<shiro:hasPermission name="unitPost:*">multi-row-head-table </shiro:hasPermission>tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${status==1}">
                            <shiro:hasPermission name="unit:edit">
                                <a class="editBtn btn btn-info btn-sm"><i class="fa fa-plus"></i> 添加</a>
                            </shiro:hasPermission>
                            </c:if>
                            <shiro:hasPermission name="unit:edit">
                                <button class="jqEditBtn btn btn-primary btn-sm">
                                    <i class="fa fa-edit"></i> 修改信息
                                </button>
                            </shiro:hasPermission>
                            <a class="popupBtn btn btn-success btn-sm tooltip-success"
                               data-url="${ctx}/unit_import?status=${status}"
                               data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                                批量导入</a>

                            <a class="jqExportBtn btn btn-info btn-sm tooltip-info"
                               data-rel="tooltip" data-placement="top" title="导出当前搜索的全部结果（按照当前排序）">
                                <i class="fa fa-download"></i> 导出</a>

                            <c:if test="${status==1}">
                                <shiro:hasPermission name="unit:abolish">
                                <button class="jqBatchBtn btn btn-warning btn-sm"
                                   data-url="${ctx}/unit_abolish" data-title="转移"
                                   data-msg="确定将这{0}个单位转移到历史单位吗？">
                                    <i class="fa fa-recycle"></i> 转移
                                </button>
                                </shiro:hasPermission>
                            </c:if>
                            <%--<shiro:hasPermission name="unit:history">
                                <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                        data-url="${ctx}/unit_history">
                                    <i class="fa fa-history"></i> 编辑历史单位
                                </button>
                            </shiro:hasPermission>--%>

                            <shiro:hasPermission name="unit:del">
                                <a class="jqBatchBtn btn btn-danger btn-sm"
                                   data-url="${ctx}/unit_batchDel" data-title="删除单位"
                                   data-msg="确定删除这{0}个单位吗？"><i class="fa fa-trash"></i> 删除</a>
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
                                                    <label>单位编号</label>
                                                        <input type="hidden" name="status" value="${status}">
                                                        <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                                               placeholder="请输入单位编号">
                                                </div>
                                                <div class="form-group">
                                                    <label>单位名称</label>
                                                        <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                                               placeholder="请输入单位名称">
                                                </div>
                                                <div class="form-group">
                                                    <label>单位类型</label>
                                                        <select data-rel="select2" name="typeId" data-placeholder="请选择单位类型">
                                                            <option></option>
                                                            <c:import url="/metaTypes?__code=mc_unit_type"/>
                                                        </select>
                                                        <script type="text/javascript">
                                                            $("#searchForm select[name=typeId]").val('${param.typeId}');
                                                        </script>
                                                </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                            <c:if test="${_query || not empty param.sort}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="status=${status}">
                                                    <i class="fa fa-reply"></i> 重置
                                                </button>
                                            </c:if>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <%--<div class="space-4"></div>--%>
                        <table id="jqGrid" class="jqGrid"> </table>
                        <div id="jqGridPager"> </div>
                    </div>
                </div></div></div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $("#jqGrid").jqGrid({
        //forceFit:true,
        url: '${ctx}/unit_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '详情', name: '_detail', width: 80, formatter:function(cellvalue, options, rowObject){
                return '<button class="openView btn btn-success btn-xs" data-url="${ctx}/unit_view?id={0}"><i class="fa fa-search"></i> {1}</button>'
                        .format(rowObject.id, '详情');
            },frozen:true },
            { label: '单位编号', name: 'code', width: 80,frozen:true },
            { label: '单位名称', name: 'name', width: 350, align:'left', formatter:function(cellvalue, options, rowObject){
                return '<a href="javascript:;" class="openView" data-url="${ctx}/unit_view?id={0}">{1}</a>'
                        .format(rowObject.id, cellvalue);
            },frozen:true },
            <c:if test="${!_query}">
            { label:'排序',align:'center', formatter: $.jgrid.formatter.sortOrder,frozen:true },
            </c:if>
            { label: '单位类型', name: 'typeId', width: 250, formatter: $.jgrid.formatter.MetaType },
            <c:if test="${status==1}">
            <shiro:hasPermission name="unitPost:*">
            { label: '正职<br/>岗位数', name: 'principalPostCount', width: 80, formatter: $.jgrid.formatter.defaultString},
            { label: '副职<br/>岗位数', name: 'vicePostCount', width: 80, formatter: $.jgrid.formatter.defaultString},
            </shiro:hasPermission>
            { label: '正处级<br/>干部职数', name: 'mainCount', width: 80, formatter: $.jgrid.formatter.defaultString},
            { label: '副处级<br/>干部职数', name: 'viceCount', width: 80, formatter: $.jgrid.formatter.defaultString},
            { label: '无行政级别<br/>干部职数', name: 'noneCount', width: 90, formatter: $.jgrid.formatter.defaultString},
            </c:if>
            /*{ label: '成立时间', name: 'workTime', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '成立文件', name: 'filePath', width: 80, formatter: function (cellvalue, options, rowObject) {
                return $.swfPreview(cellvalue, rowObject.name + "-成立文件", "查看");
            }},*/
            { label: '备注', align:'left', name: 'remark', width: 500 }
        ]}).jqGrid("setFrozenColumns");

    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>