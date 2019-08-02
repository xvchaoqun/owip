<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content">
            <div class="myTableDiv"
                 data-url-page="${ctx}/attachFile"
                 data-url-export="${ctx}/attachFile_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query"
                       value="${not empty param.type || not empty param.code || not empty param.filename}"/>
                <div class="jqgrid-vertical-offset buttons">
                    <shiro:hasPermission name="attachFile:edit">
                        <a class="popupBtn btn btn-info btn-sm" data-url="${ctx}/attachFile_au"><i
                                class="fa fa-plus"></i> 添加</a>
                        <a class="jqOpenViewBtn btn btn-primary btn-sm"
                           data-url="${ctx}/attachFile_au"
                           data-grid-id="#jqGrid"><i class="fa fa-edit"></i>修改</a>

                        <button class="popupBtn btn btn-success btn-sm tooltip-info"
                                data-url="${ctx}/attachFile_import"
                                data-rel="tooltip" data-placement="top" title="批量导入"><i
                                class="fa fa-upload"></i>
                            批量导入更新
                        </button>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="attachFile:del">
                        <button data-url="${ctx}/attachFile_batchDel"
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
                                    <label>类别</label>
                                    <select name="type" data-placeholder="请选择" data-rel="select2">
                                        <option></option>
                                        <c:forEach items="<%=SystemConstants.ATTACH_FILE_TYPE_MAP%>" var="_type">
                                            <option value="${_type.key}">${_type.value}</option>
                                        </c:forEach>
                                    </select>
                                    <script>
                                        $("#searchForm select[name=type]").val('${param.type}');
                                    </script>
                                </div>
                                <div class="form-group">
                                    <label>下载标识</label>
                                    <input class="form-control" name="code" type="text" value="${param.code}"
                                           placeholder="请输入">
                                </div>
                                <div class="form-group">
                                    <label>文件名</label>
                                    <input class="form-control" name="filename" type="text" value="${param.filename}"
                                           placeholder="请输入">
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
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/attachFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [

            {
                label: '文件名',
                name: 'filename',
                width: 400,
                align: 'left',
                frozen: true,
                formatter: function (cellvalue, options, rowObject) {
                    return rowObject.type == '<%=SystemConstants.ATTACH_FILE_TYPE_PDF%>' ? $.pdfPreview(rowObject.path, cellvalue) : cellvalue;
                }
            },
            {label: '唯一标识', name: 'code', frozen: true, align: 'left', width: 250},
            {
                label: '排序', width: 90, index: 'sort', formatter: $.jgrid.formatter.sortOrder,
                formatoptions: {url: "${ctx}/attachFile_changeOrder"}
            },
            {
                label: '下载地址',
                name: 'id',
                width: 550,
                align: 'left',
                formatter: function (cellvalue, options, rowObject) {
                    return ('<a href="javascript:;" data-url="${ctx}/attach?code={1}" data-type="download" '
                        + 'class="downloadBtn">{0}/attach?code={1}</a>')
                        .format('${_p_siteHome}', rowObject.code);
                }
            },
            {label: '扩展名', name: 'ext'},
            {
                label: '类别', name: 'type', formatter: function (cellvalue, options, rowObject) {
                    if (cellvalue == undefined) return '--';
                    return _cMap.ATTACH_FILE_TYPE_MAP[cellvalue];
                }
            },
            {label: '上传路径', name: 'path', width: 200},
            {label: '备注', name: 'remark', width: 200},
            {
                label: '上传人', name: 'user.realname', formatter: function (cellvalue, options, rowObject) {
                    return $.user(rowObject.userId, cellvalue);
                }
            },
            {label: '上传时间', name: 'createTime', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');

    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>