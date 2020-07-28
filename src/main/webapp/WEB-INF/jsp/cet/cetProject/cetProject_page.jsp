<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row rownumbers">
    <div class="col-xs-12 multi-row-head-table">

        <div id="body-content" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.name ||not empty param.projectTypeId ||not empty param.prePeriod
             ||not empty param.subPeriod ||not empty param.objCount}"/>
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
                <shiro:hasRole name="${ROLE_SUPER}">
                <button data-url="${ctx}/cet/refreshAllObjsFinishPeriod"
                        data-title="归档培训学时"
                        data-msg="确定统计并归档该培训班中所有学员最新的培训学时？"
                        data-grid-id="#jqGrid"
                        data-id-name="projectId"
                        data-loading-text="<i class='fa fa-spinner fa-spin'></i> 统计中，请稍后..."
                        class="jqItemBtn btn btn-warning btn-sm">
                     <i class="prompt fa fa-question-circle"
               data-prompt="统计汇总培训班中所有学员的培训学时（已完成学时数）"></i> 归档培训学时
                </button>
                </shiro:hasRole>
                <button class="jqExportBtn btn btn-success btn-sm tooltip-success"
                        data-url="${ctx}/cet/cetProject_data?type=${param.type}"
                        data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                    <i class="fa fa-download"></i> 导出</button>
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
                    <div class="widget-main no-padding columns">
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
                        <div class="form-group">
                            <label>培训类别</label>
                            <select data-rel="select2" name="projectTypeId"
                                    data-width="150"
                                    data-placeholder="请选择">
                                <option></option>
                                <c:forEach items="${cetProjectTypeMap}" var="entity">
                                    <option value="${entity.key}">${entity.value.name}</option>
                                </c:forEach>
                            </select>
                            <script type="text/javascript">
                                $("#searchForm select[name=projectTypeId]").val(${param.projectTypeId});
                            </script>
                        </div>
                        <div class="form-group column">
                            <label>培训学时</label>
                            <div class="input-group input">
                                <input style="width: 50px" class="form-control search-query float" type="text" name="prePeriod"
                                       value="${param.prePeriod}">
                            </div> <label>至</label>
                            <div class="input-group input">
                                <input style="width: 50px" class="form-control search-query float"
                                       type="text"
                                       name="subPeriod"
                                       value="${param.subPeriod}">
                            </div>
                        </div>
                        <%--<div class="form-group">
                            <label>参训人数</label>
                            <input class="form-control search-query num" name="objCount" type="text" value="${param.objCount}"
                                   placeholder="请输入">
                        </div>--%>
                            <div class="clearfix form-actions center">
                                <a class="jqSearchBtn btn btn-default btn-sm"
                                        data-url="${ctx}/cet/cetProject?type=${param.type}"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/cet/cetProject?type=${param.type}"
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

    var cetProjectTypeMap = ${cm:toJSONObject(cetProjectTypeMap)};
    $("#jqGrid").jqGrid({
        rownumbers:true,
        url: '${ctx}/cet/cetProject_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/cet/cetProject_detail?cls=1&projectId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},

            { label: '年度',name: 'year', width: 60, frozen: true},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy.MM.dd"), $.date(rowObject.endDate, "yyyy.MM.dd"))
            }, frozen: true},
            { label: '培训班名称',name: 'name', width: 400, align:'left'},
            {
                label: '培训班类型', name: 'projectTypeId', width: 130, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '--'
                if(cetProjectTypeMap[cellvalue]==undefined) return '--'
                return cetProjectTypeMap[cellvalue].name
            }},
            {label: '培训内容分类', name: 'category', align:'left', width: 180, formatter: function (cellvalue, options, rowObject) {
                    if($.trim(cellvalue)=='') return '--'
                    return ($.map(cellvalue.split(","), function(category){
                        return $.jgrid.formatter.MetaType(category);
                    })).join("，")
                }},
            {label: '培训课件', name: '_file', formatter: function (cellvalue, options, rowObject) {
                    return ('<button data-url="${ctx}/cet/cetProjectFile?projectId={0}" data-width="800"' +
                        'class="popupBtn btn btn-xs btn-primary"><i class="ace-icon fa fa-files-o"></i> 查看({1})</button>')
                        .format(rowObject.id, Math.trimToZero(rowObject.fileCount))
                }},
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

                    var fileName = (rowObject.fileName || rowObject.id);
                    ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordFilePath), encodeURI(fileName));
                }
                return ret;
            }
            },

            { label: '总学时/<br/>结业学时', width: 90,name: 'period', formatter: function (cellvalue, options, rowObject) {

                return rowObject.period + "/" + (rowObject.requirePeriod>0?rowObject.requirePeriod:"--");
            }},
            { label: '是否计入<br/>年度学习任务', name: 'isValid', formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'<span class="green bolder">是</span>', off:'<span class="red bolder">否</span>'}},
            { label: '参训人数',name: 'objCount', formatter: function (cellvalue, options, rowObject) {

                return Math.trimToZero(rowObject.objCount)-Math.trimToZero(rowObject.quitCount);
            }},
            { label: '归档状态', name: 'hasArchive', width: 90, formatter:$.jgrid.formatter.TRUEFALSE, formatoptions:{on:'<span class="green bolder">已归档</span>', off:'<span class="red bolder">未归档</span>'}},
            { label: '备注',name: 'remark', width: 300}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>