<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv"
             data-url-page="${ctx}/dispatchWorkFile"
             data-url-export="${ctx}/dispatchWorkFile_data"
             data-url-co="${ctx}/dispatchWorkFile_changeOrder"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.unitType ||not empty param.year ||not empty param.workType ||not empty param.privacyType || not empty param.code || not empty param.sort}"/>
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
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">
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
                                   data-querystr="&"><i class="fa fa-edit"></i>
                                    修改</a>
                                <c:if test="${status}">
                                    <button data-url="${ctx}/dispatchWorkFile_abolish"
                                            data-title="过期作废"
                                            data-msg="确定作废这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-warning btn-sm">
                                        <i class="fa fa-trash"></i> 过期作废
                                    </button>
                                </c:if>
                            </shiro:hasPermission>
                            <shiro:hasPermission name="dispatchWorkFile:del">
                                <button data-url="${ctx}/dispatchWorkFile_batchDel"
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
                                            <label>发文单位</label>
                                            <select data-rel="select2" name="unitType" data-placeholder="请选择"
                                                    data-width="270">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_dwf_unit_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=unitType]").val('${param.unitType}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>年度</label>

                                            <div class="input-group">
                                                <input class="form-control date-picker" placeholder="请选择年份" name="year"
                                                       type="text"
                                                       data-date-format="yyyy" data-date-min-view-mode="2"
                                                       value="${param.year}"/>
                                                <span class="input-group-addon"> <i
                                                        class="fa fa-calendar bigger-110"></i></span>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>所属专项工作</label>
                                            <select data-rel="select2" name="workType" data-placeholder="请选择"
                                                    data-width="270">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_dwf_work_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=workType]").val('${param.workType}');
                                            </script>
                                        </div>
                                        <div class="form-group">
                                            <label>保密级别</label>
                                            <select data-rel="select2" name="privacyType" data-placeholder="请选择"
                                                    data-width="270">
                                                <option></option>
                                                <c:import url="/metaTypes?__code=mc_dwf_privacy_type"/>
                                            </select>
                                            <script type="text/javascript">
                                                $("#searchForm select[name=privacyType]").val('${param.privacyType}');
                                            </script>
                                        </div>
                                        <div class="clearfix form-actions center">
                                            <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i>
                                                查找</a>

                                            <c:if test="${_query}">&nbsp;
                                                <button type="button" class="resetBtn btn btn-warning btn-sm"
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
        <div id="item-content"></div>
    </div>
</div>
<script type="text/template" id="sort_tpl">
<a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="1" title="上升"><i
            class="fa fa-arrow-up"></i></a>
<input type="text" value="1" class="order-step tooltip-success" data-rel="tooltip" data-placement="top"
           title="修改操作步长">
<a href="javascript:;" class="jqOrderBtn" data-id="{{=id}}" data-direction="-1" title="下降"><i
            class="fa fa-arrow-down"></i></a>
</script>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/dispatchWorkFile_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '发文单位', name: 'unitType', formatter: $.jgrid.formatter.MetaType, frozen: true},
            {label: '年度', name: 'year', width: 75, frozen: true},
            {label: '所属专项工作', name: 'workType', width: 180, formatter: $.jgrid.formatter.MetaType, frozen: true},
            {
                label: '排序', width: 80, index: 'sort', formatter: function (cellvalue, options, rowObject) {
                return _.template($("#sort_tpl").html().NoMultiSpace())({id: rowObject.id})
            }, frozen: true
            },
            {label: '发文号', name: 'code'},
            {label: '发文日期', name: 'pubDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '文件名', width: 360, name: 'fileName'},
            {
                label: '文件', width: 200, formatter: function (cellvalue, options, rowObject) {

                var ret = "-";
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                    //console.log(fileName + " =" + pdfFilePath.substr(pdfFilePath.indexOf(".")))
                    ret = '<button href="javascript:void(0)" data-url="${ctx}/swf/preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}" title="下载PDF文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-download"></i> PDF</button>'
                                    .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }
                var wordFilePath = rowObject.wordFilePath;
                if ($.trim(wordFilePath) != '') {

                    var fileName = (rowObject.fileName || rowObject.id) + (wordFilePath.substr(wordFilePath.indexOf(".")));
                    ret += '&nbsp;<button data-url="${ctx}/attach/download?path={0}&filename={1}"  title="下载WORD文件" class="linkBtn btn btn-xs btn-warning"><i class="fa fa-download"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret;
            }
            },
            {label: '保密级别', name: 'privacyType', width: 120, formatter: $.jgrid.formatter.MetaType},
            {
                label: '查看权限', name: '_auth', width: 120, formatter: function (cellvalue, options, rowObject) {
                return '<button class="popupBtn btn btn-xs btn-success"' +
                        'data-url="${ctx}/dispatchWorkFileAuth?id={0}"><i class="fa fa-search"></i> 查看权限({1})</button>'
                                .format(rowObject.id, rowObject.postCount);
            }
            },
            {label: '备注', name: 'remark', width: 150}
        ]
    }).jqGrid("setFrozenColumns").on("initGrid", function () {
        $(window).triggerHandler('resize.jqGrid');
    })
    register_date($('.date-picker'));
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>