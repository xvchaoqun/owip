<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="_query" value="${not empty param.userId ||not empty param.dpTypes||not empty param.adminLevels
                ||not empty param.postIds || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <div class="type-select">
        <c:forEach items="${cetTraineeTypes}" var="cetTraineeType">
        <span class="typeCheckbox ${traineeTypeId==cetTraineeType.id?"checked":""}">
        <input ${traineeTypeId==cetTraineeType.id?"checked":""} type="checkbox" value="${cetTraineeType.id}"> ${cetTraineeType.name}
        </span>
        </c:forEach>
    </div>
    <c:if test="${cls==1}">
    <shiro:hasPermission name="cetProjectObj:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/cet/cetProjectObj_add?projectId=${param.projectId}&traineeTypeId=${traineeTypeId}">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button data-url="${ctx}/cet/cetProjectObj_quit?isQuit=1"
                data-title="退出"
                data-msg="确定将这{0}个人员转移到“退出培训人员”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-warning btn-sm">
            <i class="fa fa-sign-out"></i> 退出
        </button>
    </shiro:hasPermission>
    </c:if>
    <c:if test="${cls==2}">
    <shiro:hasPermission name="cetProjectObj:edit">
        <button data-url="${ctx}/cet/cetProjectObj_quit?isQuit=0"
                data-title="重新学习"
                data-msg="确定将这{0}个人员转移到“培训对象”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-reply"></i> 重新学习
        </button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="cetProjectObj:del">
        <button data-url="${ctx}/cet/cetProjectObj_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？（相关数据将全部清除，请谨慎操作）"
                data-grid-id="#jqGrid2"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </shiro:hasPermission>
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
                <input type="hidden" name="cls" value="${cls}">
                <div class="form-group">
                    <label>姓名</label>
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}"
                            data-width="280"
                            name="userId" data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>行政级别</label>
                        <select class="multiselect" multiple="" name="adminLevels">
                            <c:forEach items="${adminLevelMap}" var="entry">
                                <option value="${entry.key}">${entry.value.name}</option>
                            </c:forEach>
                        </select>
                </div>
                <div class="form-group">
                    <label>职务属性</label>
                        <select class="multiselect" multiple="" name="postIds">
                            <c:forEach items="${postMap}" var="entry">
                                <option value="${entry.key}">${entry.value.name}</option>
                            </c:forEach>
                        </select>
                </div>
                <div class="form-group">
                    <label>党派</label>
                    <select class="multiselect" multiple="" name="dpTypes"
                            style="width: 250px;">
                        <option value="-1">非党干部</option>
                        <option value="0">中共党员</option>
                        <c:forEach var="entry" items="${democraticPartyMap}">
                            <option value="${entry.key}">${entry.value.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#detail-content-view"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetProjectObj?projectId=${param.projectId}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-target="#detail-content-view"
                                data-url="${ctx}/cet/cetProjectObj?projectId=${param.projectId}&traineeTypeId=${traineeTypeId}&cls=${cls}">
                            <i class="fa fa-reply"></i> 重置
                        </button>
                    </c:if>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="space-4"></div>
<table id="jqGrid2" class="jqGrid2 table-striped" data-height-reduce="20"></table>
<div id="jqGridPager2"></div>
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
<script src="${ctx}/assets/js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-multiselect.css"/>
<script>
    $.register.multiselect($('#searchForm2 select[name=dpTypes]'), ${cm:toJSONArray(selectDpTypes)});
    $.register.multiselect($('#searchForm2 select[name=adminLevels]'), ${cm:toJSONArray(selectAdminLevels)});
    $.register.multiselect($('#searchForm2 select[name=postIds]'), ${cm:toJSONArray(selectPostIds)});

    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=traineeTypeId]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })

    var period = parseFloat('${cetProject.period}');
    var requirePeriod = parseFloat('${cetProject.requirePeriod}');
    console.log("period=" + period)
    console.log("requirePeriod=" + requirePeriod)

    $.register.user_select($("#searchForm2 select[name=userId]"));
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers:true,
        url: '${ctx}/cet/cetProjectObj_data?callback=?&traineeTypeId=${traineeTypeId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:[

            { label: '学习情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ''
            }, frozen: true},
            {label: '工作证号', name: 'code', width: 100, frozen: true},
            {label: '姓名', name: 'realname', width: 120, formatter: function (cellvalue, options, rowObject) {
                return $.cadre(rowObject.cadreId, cellvalue, "_blank");

            }, frozen: true},
            {label: '所在单位及职务', name: 'title', align: 'left', width: 350, frozen: true},
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
                else if (cellvalue > 0){
                    var dpType = _cMap.metaTypeMap[rowObject.dpTypeId];
                    return (dpType==undefined)?rowObject.dpTypeId:dpType.name;
                }
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
            {label: '完成学时数', name: 'finishPeriod'},
            {label: '完成百分比', name: '_finishPercent', width: 110, formatter: function (cellvalue, options, rowObject) {

                if(isNaN(period) || period<=0) return '-';
                return Math.formatFloat(rowObject.finishPeriod*100/period, 2) + "%";
            }},
            {label: '是否达到结业要求', name: '_enough', width: 150, formatter: function (cellvalue, options, rowObject) {

                if(isNaN(requirePeriod) || requirePeriod<=0) return '-';
                return rowObject.finishPeriod/requirePeriod >= 0.9?"<span class='text-success'>达到</span>"
                        :"<span class='text-danger'>未达到</span>";
            }}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>