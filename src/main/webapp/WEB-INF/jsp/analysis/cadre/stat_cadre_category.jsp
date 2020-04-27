<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_OUT%>" var="CRP_RECORD_TYPE_OUT"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_IN%>" var="CRP_RECORD_TYPE_IN"/>
<c:set value="<%=CrpConstants.CRP_RECORD_TYPE_TRANSFER%>" var="CRP_RECORD_TYPE_TRANSFER"/>
<c:set value="${_pMap['birthToDay']=='true'?'Y.m.d':'Y.m'}" var="_p_birthToDayFormat"/>
<div class="row">
    <div class="col-xs-12">

        <div id="body-content" >
            <div class="myTableDiv"
                 data-url-page="${ctx}/stat_cadre_category"
                 data-url-export="${ctx}/stat_cadre_category_data"
                 data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">
                <c:set var="_query" value="${not empty param.cadreId}" />
                <div class="jqgrid-vertical-offset buttons">
                    <button type="button" class="jqExportBtn btn btn-success btn-sm tooltip-success"
                       data-loading-text="<i class='fa fa-spinner fa-spin '></i> 正在导出"
                       data-rel="tooltip" data-placement="top" title="导出选中记录或所有搜索结果">
                        <i class="fa fa-download"></i> 导出</button>
                </div>
                <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs" style="margin-right: 20px">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4><span class="widget-note">${note_searchbar}</span>

                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-down"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                <input type="hidden" name="type" value="${param.type}">
                                <div class="form-group">
                                    <label>账号</label>
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/cadre_selects?type=0"
                                            name="cadreId" data-placeholder="请选择">
                                        <option value="${cadre.id}">${cadre.realname}-${cadre.code}</option>
                                    </select>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="reloadBtn btn btn-warning btn-sm" data-querystr="type=${param.type}">
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
        url: '${ctx}/stat_cadre_category_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        rownumbers: true,
        colModel: [
            {label: '工作证号', name: 'cadre.code', width: 110, frozen: true},
            {
                label: '姓名',
                name: 'cadre.realname',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.cadre.id, cellvalue);
                },
                frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
            {label: '行政级别', name: 'cadre.adminLevel', formatter: $.jgrid.formatter.MetaType},
            {label: '性别', name: 'cadre.gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '出生日期', name: 'cadre.birth', formatter: $.jgrid.formatter.date, formatoptions: {newformat: '${_p_birthToDayFormat}'}},
            {label: '年龄', name: 'cadre.birth', width: 50, formatter: $.jgrid.formatter.AGE, formatoptions: {newformat: '${_p_birthToDayFormat}'}},
            {label: '政治面貌', name: '_cadreParty', width: 80, formatter: $.jgrid.formatter.cadreParty, formatoptions:{useCadre:true}},
            {label: '专业技术职务', name: 'cadre.proPost', width: 120},
            <c:if test="${param.type==1}">
            // 最高学历、最高学位、国（境）外学历、入学时间、毕业时间、毕业/在读学校、院系、所学专业。
            {label: '最高学历', name: 'cadre.eduId', formatter: $.jgrid.formatter.MetaType},
            {label: '最高学位', name: 'cadre.degree'},
            {label: '国（境）外学历', name: 'eduId', frozen: true, formatter: $.jgrid.formatter.MetaType, width: 120},
            {label: '入学时间', name: 'enrolTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, width: 80},
            {label: '毕业时间', name: 'finishTime', formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}, width: 80},
            {label: '毕业/在读学校', name: 'school', width: 280},
            {label: '院系', name: 'dep', width: 380},
            {label: '所学专业', name: 'major', width: 380},
            </c:if>
            <c:if test="${param.type==2}">
            // 国（境）外工作开始日期、国（境）外工作结束日期、国（境）外工作单位、担任职务或者专技职务。
            {label: '国（境）外工作开始日期', name: 'startTime', width: 170, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '国（境）外工作结束日期', name: 'endTime', width: 170, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '国（境）外工作单位及担任职务或者专技职务', name: 'detail', width: 450, align:'left'},
            </c:if>
            <c:if test="${param.type==3}">
            // 院系工作开始日期、院系工作结束日期、院系工作单位、担任职务或者专技职务。
            {label: '院系工作开始日期', name: 'startTime', width: 170, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '院系工作结束日期', name: 'endTime', width: 170, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '院系工作单位及担任职务或者专技职务', name: 'detail', width: 450, align:'left'},
            </c:if>
            <c:if test="${param.type==4}">
            // 机关工作开始日期、机关工作结束日期、机关工作单位、担任职务或者专技职务。
            {label: '机关工作开始日期', name: 'startTime', width: 170, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '机关工作结束日期', name: 'endTime', width: 170, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '机关工作单位及担任职务或者专技职务', name: 'detail', width: 450, align:'left'},
            </c:if>
            <c:if test="${param.type==5}">
                // 挂职开始日期、挂职结束日期、挂职工作单位、担任职务或者专技职务。
            {label: '挂职开始日期', name: 'startDate', width: 120, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {label: '挂职结束日期', name: 'realEndDate', width: 150, formatter: $.jgrid.formatter.date, formatoptions: {newformat: 'Y.m'}},
            {
                label: '挂职工作单位', name: 'toUnitType', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.type=='${CRP_RECORD_TYPE_IN}') {
                    return rowObject.toUnit;
                }else{
                    if (cellvalue == undefined) return '--';

                    if(rowObject.type=='${CRP_RECORD_TYPE_OUT}') {
                        var unitCodeOtherId = '${cm:getMetaTypeByCode("mt_temppost_out_unit_other").id}'
                    }else if(rowObject.type=='${CRP_RECORD_TYPE_TRANSFER}') {
                        var unitCodeOtherId = '${cm:getMetaTypeByCode("mt_temppost_transfer_unit_other").id}'
                    }

                    return _cMap.metaTypeMap[cellvalue].name +
                            ((cellvalue == unitCodeOtherId) ? ("：" + rowObject.toUnit) : "");
                }
            }, width: 150
            },
            {label: '担任职务或者专技职务', name: 'presentPost', width: 250},
            </c:if>
            <c:if test="${param.type==6}">
                // 人才/荣誉称号
            {label: '人才/荣誉称号', name: 'cadre.talentTitle', width: 470, align:'left'},
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");

    $.register.user_select($('[data-rel="select2-ajax"]'));
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    $.register.date($('.date-picker'));
    $('[data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>