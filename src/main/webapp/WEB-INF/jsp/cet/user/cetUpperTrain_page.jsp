<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="CET_UPPER_TRAIN_TYPE_ABROAD" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_ABROAD%>"/>
<c:set var="CET_UPPER_TRAIN_TYPE_SCHOOL" value="<%=CetConstants.CET_UPPER_TRAIN_TYPE_SCHOOL%>"/>
<c:set var="CET_UPPER_TRAIN_ADD_TYPE_SELF" value="<%=CetConstants.CET_UPPER_TRAIN_ADD_TYPE_SELF%>"/>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content" class="rownumbers multi-row-head-table"
             data-querystr="${cm:encodeQueryString(pageContext.request.queryString)}">

            <c:set var="_query"
                   value="${not empty param.unitId ||not empty param.userId || not empty param.code || not empty param.sort}"/>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${cls==1}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/user/cet/cetUpperTrain?type=${param.type}&cls=1"><i
                            class="fa fa-list"></i> 信息汇总</a>
                </li>
                <li class="<c:if test="${cls==2}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/user/cet/cetUpperTrain?type=${param.type}&cls=2"><i
                            class="fa fa-circle-o"></i> 待审核</a>
                </li>
                <li class="<c:if test="${cls==3}">active</c:if>">
                    <a href="javascript:;" class="loadPage" data-url="${ctx}/user/cet/cetUpperTrain?type=${param.type}&cls=3"><i
                            class="fa fa-times"></i> 未通过审核</a>
                </li>
            </ul>
            <div class="space-4"></div>
            <div class="jqgrid-vertical-offset buttons">

                <c:if test="${cls==1}">
                    <button class="popupBtn btn btn-success btn-sm" data-width="1000"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=${param.type}&addType=${CET_UPPER_TRAIN_ADD_TYPE_SELF}">
                        <i class="fa fa-plus"></i> 添加本人参加${param.type==CET_UPPER_TRAIN_TYPE_ABROAD?'出国研修'
                        :(param.type==CET_UPPER_TRAIN_TYPE_SCHOOL?'培训':'上级单位调训')}信息
                    </button>
                </c:if>
                <c:if test="${cls!=1}">
                    <button class="jqOpenViewBtn btn ${cls==3?'btn-success':'btn-primary'} btn-sm"
                            data-url="${ctx}/cet/cetUpperTrain_au?type=${param.type}&addType=${CET_UPPER_TRAIN_ADD_TYPE_SELF}"
                            data-grid-id="#jqGrid" data-width="1000"><i class="fa fa-edit"></i>
                        ${cls==3?'重新提交':'修改'}
                    </button>
                </c:if>
                <c:if test="${cls==2}">
                    <button data-url="${ctx}/user/cet/cetUpperTrain_batchDel"
                            data-title="删除"
                            data-msg="确定删除这{0}条数据？<br/>（删除后无法恢复，请谨慎操作！！）"
                            data-grid-id="#jqGrid"
                            class="jqBatchBtn btn btn-danger btn-sm">
                        <i class="fa fa-trash"></i> 删除
                    </button>
                </c:if>
            </div>
            <div class="space-4"></div>
            <table id="jqGrid" class="jqGrid table-striped"></table>
            <div id="jqGridPager"></div>
        </div>
        <div id="body-content-view"></div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/cet/cetUpperTrain/cetUpperTrain_colModel.jsp?addType=${CET_UPPER_TRAIN_ADD_TYPE_SELF}"/>
<script>
    $("#jqGrid").jqGrid({
        rownumbers: true,
        <c:if test="${cls==1}">multiselect:false,</c:if>
        url: '${ctx}/user/cet/cetUpperTrain_data?callback=?&${cm:encodeQueryString(pageContext.request.queryString)}',
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $(window).triggerHandler('resize.jqGrid');
    $.initNavGrid("jqGrid", "jqGridPager");
    //$.register.user_select($('[data-rel="select2-ajax"]'));
    //$('#searchForm [data-rel="select2"]').select2();
    //$('[data-rel="tooltip"]').tooltip();
    //$.register.date($('.date-picker'));
</script>