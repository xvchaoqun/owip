<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">添加课程（${cetTrain.name}）</h3>
</div>
<div class="modal-body popup-jqgrid" style="padding-top: 0">
    <form class="form-inline search-form" id="searchForm_popup" style="padding-bottom: 0">
        <input type="hidden" name="trainId" value="${cetTrain.id}">
<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_OFFLINE || cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_ONLINE}">
        <div class="form-group">
            <label>主讲人</label>
            <select data-rel="select2-ajax"
                    data-ajax-url="${ctx}/cet/cetExpert_selects"
                    name="expertId" data-placeholder="请输入姓名">
                <option value="${cetExpert.id}">${cetExpert.realname}-${cetExpert.unit}</option>
            </select>
        </div>
    </c:if>
        <div class="form-group">
            <label>课程名称</label>
            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                   placeholder="请输入课程名称">
        </div>

        <c:set var="_query" value="${not empty param.expertId ||not empty param.name}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/cet/cetTrainCourse_selectCourses"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/cet/cetTrainCourse_selectCourses"
                        data-querystr="trainId=${param.trainId}"
                        data-target="#modal .modal-content"
                        class="reloadBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
    </form>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>
<div class="modal-footer">
    <div class="note">注：如果课程不存在或信息有误，请在[课程中心-线下课程/线上课程/实践教学]中维护</div>
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="selectBtn" type="button" class="btn btn-primary" value="添加"/>
</div>

<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_OFFLINE}">
    <c:set var="courseType" value="${CET_COURSE_TYPE_OFFLINE}"/>
</c:if>
<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_ONLINE}">
    <c:set var="courseType" value="${CET_COURSE_TYPE_ONLINE}"/>
</c:if>
<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_PRACTICE}">
    <c:set var="courseType" value="${CET_COURSE_TYPE_PRACTICE}"/>
</c:if>
<jsp:include page="../cetCourse/cetCourse_colModel.jsp?type=${courseType}"/>
<script>
    $.register.user_select($('#searchForm_popup select[name=expertId]'));
    $("#jqGrid_popup").jqGrid({
        height: 390,
        width: 1160,
        rowNum: 10,
        ondblClickRow: function () {
        },
        pager: "jqGridPager_popup",
        url: "${ctx}/cet/cetTrainCourse_selectCourses_data?callback=?&planType=${cetProjectPlan.type}&${cm:encodeQueryString(pageContext.request.queryString)}",
        colModel: colModel
    }).jqGrid("setFrozenColumns");
    $.initNavGrid("jqGrid_popup", "jqGridPager_popup");

    $("#selectBtn").click(function () {

        var courseIds = $("#jqGrid_popup").getGridParam("selarrrow");
        //console.log(courseIds)
        if(courseIds.length==0){
            SysMsg.info("请选择课程。");
            return;
        }
        $.post("${ctx}/cet/cetTrainCourse_selectCourses", {trainId:${cetTrain.id} ,courseIds:courseIds},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid2").trigger("reloadGrid");
                SysMsg.success("课程添加成功。");
            }
        });
    });

    $.register.fancybox();
</script>