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
    <%--<c:if test="${param.cls==1}">
        <button class="popupBtn btn btn-success btn-sm" data-width="700"
           data-url="${ctx}/cet/cetTrainee_add?trainId=${param.trainId}&traineeTypeId=${traineeTypeId}">
            <i class="fa fa-plus"></i> 选择可选课人员</button>

        <button data-url="${ctx}/cet/cetTrainee_batchDel"
                data-title="删除"
                data-msg="确定删除这{0}个人员？"
                data-grid-id="#jqGrid4"
                data-callback="_detailReload"
                class="jqBatchBtn btn btn-danger btn-sm">
            <i class="fa fa-trash"></i> 删除
        </button>
    </c:if>--%>

    <button class="jqOpenViewBtn btn btn-info btn-sm"
            data-grid-id="#jqGrid4"
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
                    <label>姓名</label>
                    <select required data-rel="select2-ajax" data-ajax-url="${ctx}/sysUser_selects?type=${USER_TYPE_JZG}"
                            data-width="280"
                            name="userId" data-placeholder="请输入账号或姓名或教工号">
                        <option value="${sysUser.id}">${sysUser.realname}-${sysUser.code}</option>
                    </select>
                </div>
                <div class="clearfix form-actions center">
                    <a class="jqSearchBtn btn btn-default btn-sm"
                       data-target="#detail-content-view"
                       data-form="#searchForm2"
                       data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&cls=${param.cls}"><i class="fa fa-search"></i> 查找</a>
                    <c:if test="${_query}">&nbsp;
                        <button type="button" class="resetBtn btn btn-warning btn-sm"
                                data-target="#detail-content-view"
                                data-url="${ctx}/cet/cetTrainee?trainId=${param.trainId}&traineeTypeId=${traineeTypeId}&cls=${param.cls}">
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
    <table id="jqGrid4" class="jqGrid2 table-striped" data-height-reduce="40"></table>
    <div id="jqGridPager4"></div>
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
<jsp:include page="cetTrainee_colModel.jsp?type=admin"/>
<script>
    $(".typeCheckbox").click(function () {
        var $input = $("input", $(this));
        $("#searchForm2 input[name=traineeTypeId]").val($input.val());
        $("#searchForm2 .jqSearchBtn").click();
    })

    $.register.user_select($("#searchForm2 select[name=userId]"));

    $("#jqGrid4").jqGrid({
        pager: "#jqGridPager4",
        rownumbers: true,
        url: '${ctx}/cet/cetTrainee_data?callback=?&traineeTypeId=${traineeTypeId}&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns")
    $(window).triggerHandler('resize.jqGrid2');
    $.initNavGrid("jqGrid4", "jqGridPager4");
    $('#searchForm [data-rel="select2"]').select2();
    $('[data-rel="tooltip"]').tooltip();
</script>