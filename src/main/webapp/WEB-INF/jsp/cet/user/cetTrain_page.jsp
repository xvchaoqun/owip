<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content" class="myTableDiv">
            <div class="tabbable">
                <c:if test="${module==2}">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${isFinished==0}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/user/cet/cetTrain?module=${module}&isFinished=0"><i
                                class="fa fa-circle-o-notch fa-spin"></i> 正在进行</a>
                    </li>
                    <li class="<c:if test="${isFinished==1}">active</c:if>">
                        <a href="javascript:;" class="loadPage"
                           data-url="${ctx}/user/cet/cetTrain?module=${module}&isFinished=1"><i
                                class="fa fa-check"></i> 已结课</a>
                    </li>
                </ul>
                </c:if>
                <div class="tab-content">
                    <div id="home4" class="tab-pane in active rownumbers">
                        <div class="jqgrid-vertical-offset buttons">
                            <c:if test="${module==2}">
                            <c:if test="${isFinished==0}">
                            <button data-url="${ctx}/user/cet/cetTrain_quit"
                                    data-title="退出"
                                    data-msg="确定退出培训班？"
                                    data-grid-id="#jqGrid"
                                    data-id-name="traineeId"
                                    class="jqItemBtn btn btn-danger btn-sm">
                                <i class="fa fa-minus-circle"></i> 退出培训班
                            </button>
                            </c:if>
                            <button class="jqOpenViewBtn btn btn-info btn-sm"
                                    data-grid-id="#jqGrid"
                                    data-url="${ctx}/sysApprovalLog"
                                    data-width="850"
                                    data-querystr="&displayType=1&hideStatus=1&type=${SYS_APPROVAL_LOG_TYPE_CET_TRAINEE}">
                                <i class="fa fa-history"></i> 操作记录
                            </button>
                            </c:if>
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

    function _reload() {
        $("#modal").modal('hide');
        $("#jqGrid").trigger("reloadGrid");
    }

    // 查看详情和报名、 年度、 编号、 培训班名称、 培训主题、 参训人员类型、 开课日期、 结课日期、 选课截止时间
    $("#jqGrid").jqGrid({
        rownumbers: true,
        multiselect: ${module==2},
        url: '${ctx}/user/cet/cetTrain_data?callback=?&isFinished=${isFinished}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [

            <c:if test="${module==1}">
            {label: '查看详情和报名', name: '_applyDetail',  formatter: function (cellvalue, options, rowObject) {

                return ('<button class="openView btn {1} btn-xs" ' +
                        'data-url="${ctx}/user/cet/cetTrain_apply?trainId={0}"><i class="fa fa-sign-in"></i> 进入</button>')
                        .format(rowObject.id, rowObject.courseCount>0?'btn-primary':'btn-success')
            }, width: 125, frozen: true},
            {label: '报名状态', name: 'courseCount', formatter: function (cellvalue, options, rowObject) {
                return cellvalue>0?('已报名('+cellvalue+')'):'-'
            }, width: 90, frozen: true},
            </c:if>
            <c:if test="${module==2}">
            {name:'traineeId', hidden:true, key:true},
            {label: '选课详情', name: '_applyDetail',  formatter: function (cellvalue, options, rowObject) {

                return ('<button class="openView btn btn-success btn-xs" ' +
                        'data-url="${ctx}/user/cet/cetTrain_detail?trainId={0}"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id)
            }, width: 90, frozen: true},
            </c:if>
            {label: '年度', name: 'year', width:'60', frozen: true},
            {label: '培训班名称', name: 'name', width:200, align:'left'},
            {label: '内容简介', name: '_summary', formatter: function (cellvalue, options, rowObject) {
                if(rowObject.hasSummary==false) return'-'
                return ('<button class="popupBtn btn btn-primary btn-xs" data-width="750" ' +
                'data-url="${ctx}/cet/cetTrain_summary?id={0}&view=1"><i class="fa fa-search"></i> 查看</button>')
                        .format(rowObject.id);
            }},
            {label: '参训人员类型', name: 'traineeTypes', width:200},
            {label: '开课日期', name: 'startDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            {label: '结课日期', name: 'endDate', formatter: 'date', formatoptions: {newformat: 'Y-m-d'}},
            <c:if test="${module==2}">
            {label: '选课学时总数', name: 'totalPeriod', width:120},
            {label: '实际完成学时数', name: 'finishPeriod', width:130},
            </c:if>
            {label: '选课截止时间', name: 'endTime', width: 150, formatter: 'date',
                formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}}
        ]
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid", "jqGridPager");
    $(window).triggerHandler('resize.jqGrid');
</script>