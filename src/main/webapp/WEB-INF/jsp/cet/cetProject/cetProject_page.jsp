<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row rownumbers">
    <div class="col-xs-12 multi-row-head-table">

        <div id="body-content" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.name || not empty param.code || not empty param.sort}"/>
            <div class="jqgrid-vertical-offset buttons">
                <shiro:hasPermission name="cetProject:edit">
                    <button class="openView btn btn-info btn-sm"
                            data-url="${ctx}/cet/cetProject_au?_type=${param.type}">
                        <i class="fa fa-plus"></i> 添加</button>
                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                       data-url="${ctx}/cet/cetProject_au"
                            data-open-by="page"
                       data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                        修改</button>
                </shiro:hasPermission>
                <shiro:hasPermission name="cetProject:del">
                    <button data-url="${ctx}/cet/cetProject_batchDel"
                            data-title="彻底删除"
                            data-msg="确定删除这{0}条数据？（该培训班下的所有数据均将彻底删除，删除后无法恢复，请谨慎操作！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 彻底删除
                    </button>
                </shiro:hasPermission>

                <button data-url="${ctx}/cet/refreshAllObjsFinishPeriod"
                        data-title="刷新培训学时"
                        data-msg="确定统计并刷新该培训班中所有学员最新的培训学时？"
                        data-grid-id="#jqGrid"
                        data-id-name="projectId"
                        data-loading-text="<i class='fa fa-spinner fa-spin'></i> 统计中，请稍后..."
                        class="jqItemBtn btn btn-warning btn-sm">
                     <i class="prompt fa fa-question-circle"
               data-prompt="统计汇总培训班中所有学员的培训学时（已完成学时数）"></i> 刷新培训学时
                </button>
                <%--<button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                   data-url="${ctx}/cet/cetProject_data"
                   data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>--%>
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
                            <label>年度</label>
                            <input class="form-control date-picker" placeholder="请选择年份"
                                   name="year" type="text" style="width: 100px;"
                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                   value="${param.year}"/>
                        </div>
                        <div class="form-group">
                            <label>培训班名称</label>
                            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                                   placeholder="请输入培训班名称">
                        </div>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                        data-url="${ctx}/cet/cetProject"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetProject"
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
    function _reload(){
        $("#jqGrid").trigger("reloadGrid");
    }
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetProject_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail?projectId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},

            { label: '年度',name: 'year', frozen: true},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy-MM-dd"), $.date(rowObject.endDate, "yyyy-MM-dd"))
            }, frozen: true},
            { label: '培训班名称',name: 'name', width: 400, align:'left'},
            {
                label: '专题分类', name: 'projectTypeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--'
                var projectTypeMap = ${cm:toJSONObject(projectTypeMap)};
                return projectTypeMap[cellvalue].name
            }
            },
            {
                label: '培训方案', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                var ret = "";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id);
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    //console.log(rowObject.fileName)
                    var fileName = (rowObject.fileName || rowObject.id);
                    ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret;
            }
            },
            { label: '总学时',name: 'period'},
            {label: '是否计入<br/>年度学习任务', name: 'isValid', formatter: function (cellvalue, options, rowObject) {
              if (cellvalue==undefined) {
                return '--'
              }
              return cellvalue?'是':'否'
            }},
            { label: '参训人数',name: 'objCount'},

            { name: 'status', hidden:true},
            { label: '备注',name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>