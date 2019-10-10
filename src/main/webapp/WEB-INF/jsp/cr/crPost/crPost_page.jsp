<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            <a href="javascript:;" class="hideView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>

        </h4>
        <span class="text text-info bolder" style="cursor: auto;padding-left: 20px;">
                    ${cm:formatDate(crInfo.addDate, "yyyy.MM.dd")} 招聘信息
                </span>
    </div>
    <div class="space-4"></div>
    <c:set var="_query" value="${not empty param.name || not empty param.code || not empty param.sort}"/>
    <div class="jqgrid-vertical-offset buttons">
        <shiro:hasPermission name="crPost:edit">
            <button class="popupBtn btn btn-info btn-sm"
                    data-url="${ctx}/crPost_au?infoId=${param.infoId}">
                <i class="fa fa-plus"></i> 添加
            </button>
            <button class="jqOpenViewBtn btn btn-primary btn-sm"
                    data-url="${ctx}/crPost_au"
                    data-grid-id="#jqGrid2"><i class="fa fa-edit"></i>
                修改
            </button>
            <button class="popupBtn btn btn-success btn-sm tooltip-info"
                    data-url="${ctx}/crPost_import?infoId=${param.infoId}"
                    data-rel="tooltip" data-placement="top" title="批量导入"><i class="fa fa-upload"></i>
                批量导入
            </button>
        </shiro:hasPermission>
        <shiro:hasPermission name="crPost:del">
            <button data-url="${ctx}/crPost_batchDel"
                    data-title="删除"
                    data-msg="确定删除这{0}条数据？"
                    data-grid-id="#jqGrid2"
                    class="jqBatchBtn btn btn-danger btn-sm">
                <i class="fa fa-trash"></i> 删除
            </button>
        </shiro:hasPermission>
        <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                data-url="${ctx}/crPost_data"
                data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
            <i class="fa fa-download"></i> 导出
        </button>--%>
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
                <form class="form-inline search-form" id="searchForm2">
                    <div class="form-group">
                        <label>岗位名称</label>
                        <input class="form-control search-query" name="name" type="text" value="${param.name}"
                               placeholder="请输入岗位名称">
                    </div>
                    <div class="clearfix form-actions center">
                        <a class="jqSearchBtn btn btn-default btn-sm"
                           data-url="${ctx}/crPost?infoId=${param.infoId}"
                           data-target="#body-content-view"
                           data-form="#searchForm2"><i class="fa fa-search"></i> 查找</a>
                        <c:if test="${_query}">&nbsp;
                            <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                    data-url="${ctx}/crPost?infoId=${param.infoId}"
                                    data-target="#body-content-view">
                                <i class="fa fa-reply"></i> 重置
                            </button>
                        </c:if>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="space-4"></div>
    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="30"></table>
    <div id="jqGridPager2"></div>
</div>
<script>
    var requireMap = ${cm:toJSONObject(requireMap)}
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers: true,
        url: '${ctx}/crPost_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '岗位名称', name: 'name', width:400 , align:'left'},
            { label:'排序', width: 80, formatter: $.jgrid.formatter.sortOrder,
                formatoptions:{grid:'#jqGrid2',url:'${ctx}/crPost_changeOrder', frozen:true }},
            {label: '招聘人数', name: 'num'},
            {label: '主要职责', name: 'duty', width:600, align:'left'},
            { label: '资格审核模板',name: 'requireId', width: 180, formatter: function (cellvalue, options, rowObject) {
                if(cellvalue==undefined || requireMap[cellvalue]==undefined) return '--'
                      return requireMap[cellvalue].name;
                  }},
            {label: '备注', name: 'remark', width:200, align:'left'}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>