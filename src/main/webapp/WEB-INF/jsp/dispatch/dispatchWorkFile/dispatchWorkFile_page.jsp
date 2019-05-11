<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/dispatchWorkFile"
             data-url-export="${ctx}/dispatchWorkFile_data"
             data-url-co="${ctx}/dispatchWorkFile_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.fileName ||not empty param.unitTypes ||not empty param.startYear
                   ||not empty param.endYear ||not empty param.workTypes
                    ||not empty param.privacyTypes || not empty param.code || not empty param.sort}"/>
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${status}">active</c:if>">
                        <a href="javascript:;" class="hashchange" data-querystr="status=1"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 有效执行文件</a>
                    </li>
                    <li class="<c:if test="${!status}">active</c:if>">
                        <a href="javascript:;" class="hashchange" data-querystr="status=0"><i class="fa fa-history"></i>
                            失效作废文件</a>
                    </li>
                    <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                        <button type="button" class="popupBtn btn btn-danger btn-sm"
                                data-url="${ctx}/dispatchWorkFile_search"><i class="fa fa-search"></i> 查询文件所属类别</button>
                    </div>
                </ul>

                <div class="tab-content">
                    <div class="tab-pane in active">
                        <div class="jqgrid-vertical-offset buttons">
                            <shiro:hasPermission name="dispatchWorkFile:edit">
                                <c:if test="${status}">
                                    <a class="popupBtn btn btn-info btn-sm"
                                       data-url="${ctx}/dispatchWorkFile_au?type=${param.type}"><i
                                            class="fa fa-plus"></i> 添加</a>
                                </c:if>
                                <a class="jqOpenViewBtn btn btn-primary btn-sm"
                                   data-url="${ctx}/dispatchWorkFile_au"
                                   data-grid-id="#jqGrid"
                                   ><i class="fa fa-edit"></i>
                                    修改</a>
                                <c:if test="${status}">
                                    <button data-url="${ctx}/dispatchWorkFile_abolish"
                                            data-title="过期作废"
                                            data-msg="确定作废这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-warning btn-sm">
                                        <i class="fa fa-history"></i> 过期作废
                                    </button>
                                </c:if>
                                <button data-url="${ctx}/dispatchWorkFile_transfer"
                                        data-querystr="type=${param.type}"
                                        class="jqOpenViewBatchBtn btn btn-primary btn-sm">
                                    <i class="fa fa-random"></i> 批量转移
                                </button>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="dispatchWorkFile:del">
                                <button data-url="${ctx}/dispatchWorkFile_batchDel"
                                        data-title="删除"
                                        data-msg="确定删除这{0}条数据？"
                                        data-grid-id="#jqGrid"
                                        class="jqBatchBtn btn btn-danger btn-sm">
                                    <i class="fa fa-times"></i> 删除
                                </button>
                            </shiro:hasPermission>
                            <a class="jqExportBtn btn btn-success btn-sm tooltip-success"
                               data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                                <i class="fa fa-download"></i> 导出</a>
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
                                        <input name="type" type="hidden" value="${param.type}">
                                        <input name="status" type="hidden" value="${status}">
                                        <div class="form-group">
                                            <label>文件名</label>
                                            <input class="form-control" type="text" name="fileName" value="${param.fileName}">
                                        </div>
                                        <div class="form-group">
                                            <label>发文单位</label>
                                            <select class="multiselect" multiple="" name="unitTypes">
                                                <c:import url="/metaTypes?__code=mc_dwf_unit_type"/>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>年度</label>
                                            <input style="width: 70px;" class="form-control date-picker" placeholder="起始年份" name="startYear"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${param.startYear}"/>
                                            -
                                            <input style="width: 70px;" class="form-control date-picker" placeholder="结束年份" name="endYear"
                                                   type="text"
                                                   data-date-format="yyyy" data-date-min-view-mode="2"
                                                   value="${param.endYear}"/>
                                        </div>
                                        <div class="form-group">
                                            <label>所属专项工作</label>
                                            <select class="multiselect" multiple="" name="workTypes">
                                                <c:import url="/metaTypes?__code=mc_dwf_work_type"/>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>保密级别</label>
                                            <select class="multiselect" multiple="" name="privacyTypes">
                                                <c:import url="/metaTypes?__code=mc_dwf_privacy_type"/>
                                            </select>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                                        data-querystr="type=${param.type}&status=${status}">
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
            </div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $.register.multiselect($('#searchForm select[name=unitTypes]'), ${cm:toJSONArray(selectUnitTypes)});
    $.register.multiselect($('#searchForm select[name=workTypes]'), ${cm:toJSONArray(selectWorkTypes)});
    $.register.multiselect($('#searchForm select[name=privacyTypes]'), ${cm:toJSONArray(selectPrivacyTypes)});

    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatchWorkFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '文件名', width: 420, name: 'fileName', align:'left', frozen: true},
            {
                label: '文件', width: 200, align:'left', formatter: function (cellvalue, options, rowObject) {

                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    var fileName = (rowObject.fileName || rowObject.id) + (wordFilePath.substr(wordFilePath.indexOf(".")));
                    ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret;
            }
            },
            {label: '发文单位', name: 'unitType', width: 120, formatter: $.jgrid.formatter.MetaType},
            {label: '发文号', name: 'code', width: 150},
            {label: '发文日期', name: 'pubDate', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y-m-d'}},
            {label: '年度', name: 'year', width: 75},
            {
                label: '排序', width: 80, formatter: $.jgrid.formatter.sortOrder
            },
            {label: '所属专项工作', name: 'workType', width: 180, formatter: $.jgrid.formatter.MetaType},
            {label: '保密级别', name: 'privacyType', width: 120, formatter: $.jgrid.formatter.MetaType},
            {
                label: '查看权限', name: '_auth', width: 120, formatter: function (cellvalue, options, rowObject) {
                return '<button class="popupBtn btn btn-xs btn-success"' +
                        'data-url="${ctx}/dispatchWorkFileAuth?id={0}"><i class="fa fa-search"></i> 查看权限({1})</button>'
                                .format(rowObject.id, rowObject.postCount);
            }
            },
            {label: '备注', name: 'remark', width: 250, align:"left"}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid');
    $.register.date($('.date-picker'));
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>