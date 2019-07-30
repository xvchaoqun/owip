<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <jsp:include page="../sysConfig/menu.jsp"/>
        <div class="space-4"></div>
        <div id="body-content" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.code ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="sysProperty:edit">
                    <button class="popupBtn btn btn-success btn-sm"
                            data-url="${ctx}/sysProperty_au">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/sysProperty_au"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="sysProperty:del">
                    <button data-url="${ctx}/sysProperty_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </shiro:hasPermission>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/sysProperty_data"
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
                            <label>代码</label>
                            <input class="form-control search-query" name="code" type="text" value="${param.code}"
                                   placeholder="请输入代码">
                        </div>
                        <div class="form-group">
                            <label>名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                   data-url="${ctx}/sysProperty"
                                   data-target="#page-content"
                                   data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/sysProperty"
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
        url: '${ctx}/sysProperty_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
                { label:'类别', name: 'type', width: 90, formatter:function(cellvalue, options, rowObject){
                    return _cMap.SYS_PROPERTY_TYPE_MAP[cellvalue];
                },frozen:true},
                {label: '排序', width: 90, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{url: "${ctx}/sysProperty_changeOrder"},frozen:true},
                { label: '名称',name: 'name', width:180, align:'left'},
                { label: '代码',name: 'code', width:250, align:'left'},
                { label: '取值',name: 'content', width:380, align:'left', formatter:function(cellvalue, options, rowObject){

                    if(rowObject.type==<%=SystemConstants.SYS_PROPERTY_TYPE_PIC%>){
                        if($.trim(cellvalue)=='') return '--';
                        return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}">查看</a>'
                        .format(encodeURI(cellvalue), rowObject.name + ".jpg");
                    }

                    return $.jgrid.formatter.htmlencodeWithNoSpace(cellvalue);
                }},
                { label: '说明',name: 'remark', width:480, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });
</script>