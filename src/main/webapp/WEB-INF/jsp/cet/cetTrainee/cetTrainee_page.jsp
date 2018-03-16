<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <div class="type-select">
        <c:forEach items="${cetTraineeTypes}" var="cetTraineeType">
        <span class="typeCheckbox ${traineeTypeId==cetTraineeType.id?"checked":""}">
        <input ${traineeTypeId==cetTraineeType.id?"checked":""} type="checkbox" value="${cetTraineeType.id}"> ${cetTraineeType.name}
        </span>
        </c:forEach>
    </div>
    <c:if test="${param.cls==1}">
            <a class="popupBtn btn btn-success btn-sm" data-width="700"
               data-url="${ctx}/cet/cetTrainee_add?trainId=${param.trainId}&traineeTypeId=${traineeTypeId}"><i class="fa fa-plus"></i> 选择可选课人员</a>
        <button data-url="${ctx}/cet/cetTrainee_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}个人员？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </c:if>
    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid2"
            data-url="${ctx}/sysApprovalLog"
            data-width="850"
            data-querystr="&displayType=1&hideStatus=1&type=${SYS_APPROVAL_LOG_TYPE_CET_TRAINEE}">
        <i class="fa fa-history"></i> 操作记录
    </button>
</div>
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
            <form class="form-inline search-form" id="searchForm2">
                <input type="hidden" name="traineeTypeId" value="${traineeTypeId}">
                <div class="form-group">
                    <label>可选课人员</label>
                    <input class="form-control search-query" name="userId" type="text" value="${param.userId}"
                           placeholder="请输入可选课人员">
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#detail-item-content"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&cls=${param.cls}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-target="#detail-item-content"
                                data-url="${ctx}/cet/cetTrainee?trainId=${trainId.postId}&cls=${param.cls}">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<div class="rownumbers">
    <table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="40"></table>
    <div id="jqGridPager2"></div>
</div>
<style>
    .type-select {
        float:left;
        padding: 5px 20px 0 5px;
    }

    .type-select a {
        padding-left: 20px;
    }

    .type-select .typeCheckbox {
        padding: 10px;
        cursor: pointer;
    }

    .type-select .typeCheckbox.checked {
        color: darkred;
        font-weight: bolder;
    }
</style>
<jsp:include page="/WEB-INF/jsp/common/daterangerpicker.jsp"/>
<script>
    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=traineeTypeId]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })

    function _reload() {
        $("#jqGrid2").trigger("reloadGrid");
    }
    register_user_select($("#searchForm2 select[name=userId]"));

    $("#jqGrid2").jqGrid({
        pager: "#jqGridPager2",
        rownumbers: true,
        url: '${ctx}/cet/cetTrainee_data?callback=?&traineeTypeId=${traineeTypeId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: [
            { label: '选课情况',name: 'trainId'},
            {label: '工作证号', name: 'code', width: 100, frozen: true},
            {label: '姓名', name: 'realname', width: 120, frozen: true},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350},
            {
                label: '行政级别', name: 'typeId', formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.adminLevelMap[cellvalue].name;
            }},
            {
                label: '职务属性', name: 'postId', width: 150, formatter: function (cellvalue, options, rowObject) {
                if (cellvalue == undefined) return '-';
                return _cMap.postMap[cellvalue].name;
            }},
            {
                label: '党派', name: 'cadreDpType', width: 80, formatter: function (cellvalue, options, rowObject) {

                if (cellvalue == 0) return "中共党员"
                else if (cellvalue > 0) return _cMap.metaTypeMap[rowObject.dpTypeId].name
                return "-";
            }},
            {label: '专业技术职务', name: 'proPost', width: 120},
            {
                label: '任现职时间',
                name: 'lpWorkTime',
                formatter: 'date',
                formatoptions: {newformat: 'Y-m-d'}
            },
            {label: '联系方式', name: 'mobile', width: 120},
            {label: '电子邮箱', name: 'email', width: 150},
            { label: '本年度参加培训情况',name: 'courseCount', width: 180},
            { label: '备注',name: 'remark', width: 250}
        ]
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>