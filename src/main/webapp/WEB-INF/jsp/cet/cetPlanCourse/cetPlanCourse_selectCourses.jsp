<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="modal-header">
    <button type="button" data-dismiss="modal" aria-hidden="true" class="close">&times;</button>
    <h3 style="margin: 0">添加课程（${CET_PROJECT_PLAN_TYPE_MAP.get(cetProjectPlan.type)}）</h3>
</div>
<div class="modal-body popup-jqgrid" style="padding-top: 0">
    <form class="form-inline search-form" id="searchForm_popup" style="padding-bottom: 0">
        <input type="hidden" name="planId" value="${cetProjectPlan.id}">
        <div class="form-group">
            <label>名称</label>
            <input class="form-control search-query" name="name" type="text" value="${param.name}"
                   placeholder="请输入课程名称">
        </div>

        <c:set var="_query" value="${not empty param.name}"/>
        <div class="form-group">
            <button type="button" data-url="${ctx}/cet/cetPlanCourse_selectCourses"
                    data-target="#modal .modal-content" data-form="#searchForm_popup"
                    class="jqSearchBtn btn btn-default btn-sm"><i class="fa fa-search"></i> 查找
            </button>
            <c:if test="${_query}">
                <button type="button"
                        data-url="${ctx}/cet/cetPlanCourse_selectCourses"
                        data-querystr="planId=${cetProjectPlan.id}"
                        data-target="#modal .modal-content"
                        class="resetBtn btn btn-warning btn-sm">
                    <i class="fa fa-reply"></i> 重置
                </button>
            </c:if>
        </div>
    </form>
    <table id="jqGrid_popup" class="table-striped"></table>
    <div id="jqGridPager_popup"></div>
</div>
<div class="modal-footer">
    <a href="javascript:;" data-dismiss="modal" class="btn btn-default">取消</a>
    <input id="selectBtn" type="button" class="btn btn-primary" value="添加"/>
</div>

<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SELF}">
    <c:set var="courseType" value="${CET_COURSE_TYPE_SELF}"/>
</c:if>
<c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_SPECIAL}">
    <c:set var="courseType" value="${CET_COURSE_TYPE_SPECIAL}"/>
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
        url: "${ctx}/cet/cetPlanCourse_selectCourses_data?callback=?&planType=${cetProjectPlan.type}&${cm:encodeQueryString(pageContext.request.queryString)}",
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
        $.post("${ctx}/cet/cetPlanCourse_selectCourses", {planId:${cetProjectPlan.id} ,courseIds:courseIds},function(ret){
            if(ret.success){
                $("#modal").modal('hide');
                $("#jqGrid2").trigger("reloadGrid");
                SysMsg.success("课程添加成功。");
            }
        });
    });

</script>