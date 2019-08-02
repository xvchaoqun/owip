<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query" value="${not empty param.year ||not empty param.name
            || not empty param.code || not empty param.sort}"/>

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
                        <input type="hidden" name="type" value="${param.type}">
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
                                        data-url="${ctx}/user/cet/cetProject"
                                        data-target="#page-content"
                                        data-form="#searchForm"><i class="fa fa-search"></i> 查找</a>
                                <c:if test="${_query}">&nbsp;
                                    <button type="button" class="reloadBtn btn btn-warning btn-sm"
                                            data-url="${ctx}/user/cet/cetProject?type=${param.type}"
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
    $.register.date($('.date-picker'));
    $("#jqGrid").jqGrid({
        rownumbers:true,
        multiselect:false,
        url: '${ctx}/user/cet/cetProject_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '详情', name: '_detail', width:'80', formatter: function (cellvalue, options, rowObject) {
                return ('<button class="openView btn btn-success btn-xs" ' +
                'data-url="${ctx}/user/cet/cetProjectPlan?projectId={0}"><i class="fa fa-search"></i> 详情</button>')
                        .format(rowObject.id);
            }, frozen: true},
            { label: '年度',name: 'year', frozen: true},
            { label: '培训时间',name: 'startDate', width: 200, formatter: function (cellvalue, options, rowObject) {
                return '{0} ~ {1}'.format($.date(rowObject.startDate, "yyyy-MM-dd"), $.date(rowObject.endDate, "yyyy-MM-dd"))
            }, frozen: true},
            { label: '培训班名称',name: 'name', width: 400, align:'left', frozen: true},
            {
                label: '培训方案', width: 90,formatter: function (cellvalue, options, rowObject) {
                var pdfFilePath = rowObject.pdfFilePath;
                if ($.trim(pdfFilePath) != '') {
                    var fileName = (rowObject.fileName || rowObject.id) + (pdfFilePath.substr(pdfFilePath.indexOf(".")));
                    return ('<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}" '+
                            'title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 查看</button>')
                            .format(encodeURI(pdfFilePath), encodeURI(fileName));
                }

                return '--';
            }},
            { label: '总学时',name: 'period'},
            { label: '应完成学时数',name: 'obj.shouldFinishPeriod', width: 110,formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(cellvalue)==0) return '--'
                return cellvalue
            }},
            {label: '已完成学时数', name: 'obj.finishPeriod', width: 110},
            { label: '学习进度',name: '_finish',formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.obj.shouldFinishPeriod)==0) return '--'
                var progress = Math.formatFloat(Math.trimToZero(rowObject.obj.finishPeriod)*100/rowObject.obj.shouldFinishPeriod, 1) + "%";
                return ('<div class="progress progress-striped pos-rel" data-percent="{0}">' +
                '<div class="progress-bar progress-bar-success" style="width:{0};"></div></div>').format(progress)
            }},
            { label: '是否结业',name: 'obj.isGraduate',formatter: function (cellvalue, options, rowObject) {
                if(Math.trimToZero(rowObject.obj.shouldFinishPeriod)==0) return '--'
                return cellvalue?"已结业":"未结业"
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
</script>