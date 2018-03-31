<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="space-4"></div>
<c:set var="_query" value="${not empty param.userId || not empty param.code || not empty param.sort}"/>
<div class="jqgrid-vertical-offset buttons">
    <div class="type-select">
        <c:forEach items="${CET_SPECIAL_OBJ_TYPE_MAP}" var="_type">
        <span class="typeCheckbox ${type==_type.key?"checked":""}">
        <input ${type==_type.key?"checked":""} type="checkbox" value="${_type.key}"> ${_type.value}
        </span>
        </c:forEach>
    </div>
    <c:if test="${cls==1}">
    <shiro:hasPermission name="cetSpecialObj:edit">
        <button class="popupBtn btn btn-info btn-sm"
                data-url="${ctx}/cet/cetSpecialObj_add?specialId=${param.specialId}&type=${type}">
            <i class="fa fa-plus"></i> 添加
        </button>
        <button data-url="${ctx}/cet/cetSpecialObj_quit?isQuit=1"
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
    <shiro:hasPermission name="cetSpecialObj:edit">
        <button data-url="${ctx}/cet/cetSpecialObj_quit?isQuit=0"
                data-title="重新学习"
                data-msg="确定将这{0}个人员转移到“培训对象”？"
                data-grid-id="#jqGrid2"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-success btn-sm">
            <i class="fa fa-reply"></i> 重新学习
        </button>
    </shiro:hasPermission>
    </c:if>
    <shiro:hasPermission name="cetSpecialObj:del">
        <button data-url="${ctx}/cet/cetSpecialObj_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}条数据？（相关数据将全部清除，请谨慎操作）"
                data-grid-id="#jqGrid"
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
                <input type="hidden" name="type" value="${type}">
                <input type="hidden" name="cls" value="${cls}">
                <div class="form-group">
                    <label>姓名</label>
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}"
                            data-width="280"
                            name="userId" data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#detail-item-content"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetSpecialObj?specialId=${param.specialId}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-target="#detail-item-content"
                                data-url="${ctx}/cet/cetSpecialObj?specialId=${trainId.specialId}&type=${type}&cls=${cls}">
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
<script>
    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=type]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })
    $.register.user_select($("#searchForm2 select[name=userId]"));
    $("#jqGrid2").jqGrid({
        pager: "jqGridPager2",
        rownumbers:true,
        url: '${ctx}/cet/cetSpecialObj_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel:[

            { label: '学习情况',name: '_status', width: 80, formatter: function (cellvalue, options, rowObject) {
                return ''
            }},
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
            {label: '完成学时数', name: '_finishPeriod'},
            {label: '完成百分比', name: '_finishPercent', width: 110},
            {label: '是否达到结业要求', name: '_enough', width: 150}
        ]
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid2", "jqGridPager2");
    $('#searchForm2 [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>