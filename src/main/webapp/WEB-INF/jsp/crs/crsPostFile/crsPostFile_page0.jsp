<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<div class="jqgrid-vertical-offset buttons">
    <shiro:hasPermission name="crsPostFile:edit">
        <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/crsPostFile_au?postId=${param.id}">
            <i class="fa fa-plus"></i> 添加</a>
        <a class="jqOpenViewBtn btn btn-primary btn-sm"
           data-url="${ctx}/crsPostFile_au"
           data-grid-id="#jqGrid2"
           data-querystr="&postId=${param.id}"><i class="fa fa-edit"></i>
            修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="crsPostFile:del">
        <button data-url="${ctx}/crsPostFile_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
    <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
        <i class="fa fa-download"></i> 导出</a>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="40"></table>
<div id="jqGridPager2"></div>
<script>
    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        url: '${ctx}/crsPostFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '文件名', name: 'fileName', width:150},
            {label: '文件', name: 'file', width: 150, formatter: function (cellvalue, options, rowObject) {

                return '<a class="various" title="{1}" data-path="{0}" data-fancybox-type="image" href="${ctx}/pic?path={0}"> 查看</a>'
                        .format(encodeURI(cellvalue), rowObject.fileName + ".jpg");
            }},
            {label: '类别', name: 'type', width: 180, formatter: function (cellvalue, options, rowObject) {
                return _cMap.CRS_POST_FILE_TYPE_MAP[cellvalue]
            }},
            {label: '上传时间', name: 'createTime', width: 180},
            {label: '备注', name: 'remark', width: 480}
        ]
    }).jqGrid("setFrozenColumns");

    $.register.fancybox(function () {
        //console.log(this)
        this.title = '<div class="title">' + this.title + '<div class="download">【<a href="${ctx}/attach/download?path={0}&filename={1}" target="_blank">点击下载</a>】</div></div>'
                        .format($(this.element).data('path'), this.title);
    });

    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>