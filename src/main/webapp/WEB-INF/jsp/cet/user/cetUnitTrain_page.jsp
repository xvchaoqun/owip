<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <c:set var="_query"
                   value="${not empty param.type ||not empty param.unitId ||not empty param.userId || not empty param.code || not empty param.sort}"/>

            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiselect:false,
        url: '${ctx}/user/cet/cetUnitTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'project.year', width: 60},
            {label: '培训班主办方', name: 'project.unitId', width: 250, align: 'left', formatter: $.jgrid.formatter.unit},
            {label: '培训班名称', name: 'project.projectName', align: 'left', width: 350},
            {
                label: '培训<br/>开始时间',
                name: 'project.startDate',
                width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {
                label: '培训<br/>结束时间',
                name: 'project.endDate',
                width: 120,
                formatter: $.jgrid.formatter.date,
                formatoptions: {newformat: 'Y.m.d'}
            },
            {label: '参训人姓名', name: 'user.realname', frozen: true},
            {label: '参训人工号', width: 110, name: 'user.code', frozen: true},
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '职务属性', name: 'postId', width: 120, align: 'left', formatter: $.jgrid.formatter.MetaType},
            {label: '完成培训学时', name: 'period'},
            {
                label: '培训总结', name: '_note', width: 200, formatter: function (cellvalue, options, rowObject) {

                    var ret = "";
                    var fileName = "培训总结(" + rowObject.user.realname + ")";
                    var pdfNote = rowObject.pdfNote;
                    if ($.trim(pdfNote) != '') {

                        //console.log(fileName + " =" + pdfNote.substr(pdfNote.indexOf(".")))
                        ret = '<button href="javascript:void(0)" data-url="${ctx}/pdf_preview?path={0}&filename={1}"  title="PDF文件预览" class="popupBtn btn btn-xs btn-primary"><i class="fa fa-search"></i> 预览</button>'
                                .format(encodeURI(pdfNote), encodeURI(fileName))
                            + '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}" title="下载PDF文件" class="downloadBtn btn btn-xs btn-warning"><i class="fa fa-file-pdf-o"></i> PDF</button>'
                                .format(encodeURI(pdfNote), encodeURI(fileName));
                    }
                    var wordNote = rowObject.wordNote;
                    if ($.trim(wordNote) != '') {
                        ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(encodeURI(wordNote), encodeURI(fileName));
                    }
                    return ret;
                }
            },
            {label: '操作人', name: 'addUser.realname'},
            {label: '添加时间', name: 'addTime', width: 150},
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>