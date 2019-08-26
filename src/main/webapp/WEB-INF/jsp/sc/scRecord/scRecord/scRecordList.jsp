<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="_query" value="${not empty param.postName}"/>
<form class="form-inline" id="searchForm_popup">
    <div class="form-group">
        <label>岗位名称</label>
        <input class="form-control search-query" name="postName" type="text" value="${param.postName}"
               placeholder="请输入岗位名称">
    </div>
    <button type="button" class="jqSearchBtn btn btn-default btn-sm"
       data-url="${ctx}/sc/scRecord?cls=10"
       data-target="#popup-content"
       data-form="#searchForm_popup"><i class="fa fa-search"></i> 查找</button>
    <c:if test="${_query}">&nbsp;
        <button type="button" class="reloadBtn btn btn-warning btn-sm"
                data-url="${ctx}/sc/scRecord?cls=10"
                data-target="#popup-content">
            <i class="fa fa-reply"></i> 重置
        </button>
    </c:if>
    <span style="padding-left: 10px">注：双击行进行选择</span>
    <div class="buttons pull-right">
        <button type="button" class="btn btn-warning btn-sm"
                onclick="_selectItem()"><i class="fa fa-eraser"></i> 清除选择</button>
    </div>
</form>

<div class="space-4"></div>
<table id="jqGrid_popup" class="jqGrid table-striped"></table>
<div id="jqGridPager_popup"></div>
<script>

    $("#jqGrid_popup").jqGrid({
        multiselect:false,
        rowNum:10,
        rowList:[], // 不起作用？
        pager:'jqGridPager_popup',
        rownumbers: true,
        loadui:'disabled',
        height:270,
        url: '${ctx}/sc/scRecord_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年份', name: 'year', width: 80, frozen: true},
            {label: '选任岗位', name: 'postName', align: 'left', width: 200, frozen: true},
            {label: '岗位编号',name: 'postCode'},
            {label: '分管工作', align: 'left', name: 'job', width: 200},
            {label: '纪实编号', name: 'code', width: 200, frozen: true},
            {
                label: '纪实状态', name: 'status', formatter: function (cellvalue, options, rowObject) {
                    return _cMap.SC_RECORD_STATUS_MAP[cellvalue]
                }
            },{name: 'unitPostId', hidden:true},{name: 'unitId', hidden:true},{name: 'adminLevel', hidden:true}
        ],
        ondblClickRow: function (id, status) {
            var rowData = $(this).getRowData(id);
            _selectItem(id, rowData);
        }
    });
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");
</script>