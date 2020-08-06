<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_PASS%>" var="CET_UNITTRAIN_RERECORD_PASS"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_PARTY%>" var="CET_UNITTRAIN_RERECORD_PARTY"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_CET%>" var="CET_UNITTRAIN_RERECORD_CET"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_SAVE%>" var="CET_UNITTRAIN_RERECORD_SAVE"/>
<c:set value="<%=CetConstants.CET_UNITTRAIN_RERECORD_MAP%>" var="CET_UNITTRAIN_RERECORD_MAP"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
            <c:set var="_query"
                   value="${not empty param.type ||not empty param.unitId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
                <div class="tabbable">
                    <jsp:include page="menu.jsp"/>

                    <div class="tab-content">
                        <div class="tab-pane in active">
                            <c:if test="${cls==6}">
                                <div class="jqgrid-vertical-offset buttons">
                                    <button class="popupBtn btn btn-success btn-sm"
                                            data-width="1000"
                                            data-url="${ctx}/user/cet/cetUnitTrain_list?reRecord=1">
                                        <i class="fa fa-plus"></i> 补录申请</button>
                                    <button class="jqOpenViewBtn btn btn-primary btn-sm"
                                            data-url="${ctx}/cet/cetUnitTrain_au?apply=1" data-width="1000"
                                            data-grid-id="#jqGrid"><i class="fa fa-edit"></i>
                                        修改
                                    </button>
                                    <button data-url="${ctx}/user/cet/cetUnitTrain_batchDel"
                                            data-title="删除"
                                            data-msg="确定删除这{0}条数据？"
                                            data-grid-id="#jqGrid"
                                            class="jqBatchBtn btn btn-danger btn-sm">
                                        <i class="fa fa-trash"></i> 删除
                                    </button>
                                </div>
                            </c:if>
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
    var traineeTypeMap = ${cm:toJSONObject(traineeTypeMap)};
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiselect:${cls==6},
        url: '${ctx}/user/cet/cetUnitTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            {label: '年度', name: 'project.year', width: 60, frozen:true},
            {label: '培训班名称', name: 'project.projectName', align: 'left', width: 350, frozen:true},
            {label: '培训班主办方', name: 'project.cetParty.name', width: 350, align: 'left'},
            {label: '主办单位', name: 'project.unitId', width: 250, align: 'left', formatter: $.jgrid.formatter.unit},
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
            {label: '参训人姓名', name: 'user.realname'},
            {label: '参训人工号', width: 110, name: 'user.code'},
            <c:if test="${cm:getMetaTypes('mc_cet_identity').size()>0}">
                {
                    label: '参训人身份', name: 'identity', width: 150, align: 'left', formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue == null) {
                            return "--";
                        }
                        return ($.map(cellvalue.split(","), function(identity){
                            return $.jgrid.formatter.MetaType(identity);
                        })).join("，")
                    }},
            </c:if>
            { label: '参训人类型', name: 'traineeTypeId', formatter: function (cellvalue, options, rowObject) {
                    if(cellvalue==undefined)return '--';
                    if(cellvalue==0) return rowObject.otherTraineeType;
                    return traineeTypeMap[cellvalue].name;
                }, width:180},
            <c:if test="${cls==6}">
                {label: '补录进度', name: 'status', width: 120, formatter: function (cellvalue, options, rowObject) {
                        if (cellvalue==${CET_UNITTRAIN_RERECORD_CET})
                            return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_CET)}';
                        else if (cellvalue==${CET_UNITTRAIN_RERECORD_PARTY})
                            return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_PARTY)}';
                        else if (cellvalue==${CET_UNITTRAIN_RERECORD_SAVE})
                            return '${CET_UNITTRAIN_RERECORD_MAP.get(CET_UNITTRAIN_RERECORD_SAVE)}';
                    }},
            </c:if>
            {label: '时任单位及职务', name: 'title', align: 'left', width: 350},
            {label: '职务属性', name: 'postType', width: 120, align: 'left', formatter: $.jgrid.formatter.MetaType},
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
                                .format(pdfNote, encodeURI(fileName));
                    }
                    var wordNote = rowObject.wordNote;
                    if ($.trim(wordNote) != '') {
                        ret += '&nbsp;<button data-url="${ctx}/attach_download?path={0}&filename={1}"  title="下载WORD文件" class="downloadBtn btn btn-xs btn-success"><i class="fa fa-file-word-o"></i> DOC</button>'
                            .format(wordNote, encodeURI(fileName));
                    }
                    return ret;
                }
            },
            /*{label: '操作人', name: 'addUser.realname'},*/
            {label: '添加时间', name: 'addTime', width: 150},
            <c:if test="${cls==6}">
                {label: '补录审批备注', name: 'reason', width: 200},
            </c:if>
            {label: '备注', name: 'remark', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>