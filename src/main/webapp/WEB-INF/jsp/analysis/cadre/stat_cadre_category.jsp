<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="myTableDiv">
                <div class="jqgrid-vertical-offset widget-box collapsed hidden-sm hidden-xs" style="margin-right: 20px">
                    <div class="widget-header">
                        <h4 class="widget-title">搜索</h4>

                        <div class="widget-toolbar">
                            <a href="javascript:;" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-down"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main no-padding">
                            <form class="form-inline search-form" id="searchForm">
                                <div class="form-group">
                                    <label>年份</label>
                                    <select data-rel="select2-ajax"
                                            data-ajax-url="${ctx}/cadre_selects?type=0"
                                            name="cadreId" data-placeholder="请选择">
                                        <option value="${cadre.id}">${cadre.user.realname}-${cadre.user.code}</option>
                                    </select>
                                </div>
                                <div class="clearfix form-actions center">
                                    <a class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找</a>
                                    <c:if test="${_query}">&nbsp;
                                        <button type="button" class="resetBtn btn btn-warning btn-sm">
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
        <div id="item-content"></div>
    </div>
</div>
<script>
    $("#jqGrid").jqGrid({
        url: '${ctx}/stat_cadre_category_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        rownumbers: true,
        colModel: [
            {label: '工作证号', name: 'cadre.user.code', width: 100, frozen: true},
            {
                label: '姓名',
                name: 'cadre.user.realname',
                width: 120,
                formatter: function (cellvalue, options, rowObject) {
                    return $.cadre(rowObject.id, cellvalue);
                },
                frozen: true
            },
            {label: '所在单位及职务', name: 'cadre.title', align: 'left', width: 350},
            {
                label: '行政级别', name: 'cadre.typeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.adminLevelMap[cellvalue].name;
            }
            },
            {label: '性别', name: 'cadre.gender', width: 50, formatter: $.jgrid.formatter.GENDER},
            {label: '出生时间', name: 'cadre.birth', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '年龄', name: 'cadre.birth', width: 50, formatter: $.jgrid.formatter.AGE},
            {
                label: '党派', name: 'cadre.cadreDpType', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (cellvalue == 0) return "中共党员"
                else if (cellvalue > 0) return _cMap.metaTypeMap[rowObject.dpTypeId].name
                return "-";
            }
            },
            {label: '专业技术职务', name: 'cadre.proPost', width: 120},
            <c:if test="${param.type==1}">
            // 最高学历、最高学位、国（境）外学历、入学时间、毕业时间、毕业/在读学校、院系、所学专业。
            {label: '最高学历', name: 'cadre.eduId', formatter: $.jgrid.formatter.MetaType},
            {label: '最高学位', name: 'cadre.degree'},
            {label: '国（境）外学历', name: 'eduId', frozen: true, formatter: $.jgrid.formatter.MetaType},
            {label: '入学时间', name: 'enrolTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
            {label: '毕业时间', name: 'finishTime', formatter: 'date', formatoptions: {newformat: 'Y.m'}, width: 80},
            {label: '毕业/在读学校', name: 'school', width: 280},
            {label: '院系', name: 'dep', width: 380},
            {label: '所学专业', name: 'major', width: 380},
            </c:if>
            <c:if test="${param.type==2}">
            // 国（境）外工作开始日期、国（境）外工作结束日期、国（境）外工作单位、担任职务或者专技职务。
            </c:if>
        ]
    }).jqGrid("setFrozenColumns");

    register_user_select($('[data-rel="select2-ajax"]'));
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    register_date($('.date-picker'));
    $('[data-rel="select2"]').select2();
</script>