<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="back-btn">
    <a href="javascript:;" class="hideView"><i class="fa fa-reply"></i> 返回</a>
</div>
<div class="alert alert-block alert-success" style="margin-bottom: 5px;font-size: 14px;font-weight: bolder;">
    <i class="ace-icon fa fa-calendar"></i>
    ${cetTrain.name}
</div>
<div class="tabbable">
    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
        <li class="${param.selected!=1?'active':''}">
            <a data-toggle="tab" href="#unSelectedList"><i
                    class="green ace-icon fa fa-list bigger-120"></i>可选课列表</a>
        </li>
        <li class="${param.selected==1?'active':''}">
            <a data-toggle="tab" href="#selectedList"><i
                    class="orange ace-icon fa fa-check-square-o bigger-120"></i>已选课列表</a>
        </li>
    </ul>
    <div class="tab-content" style="padding: 0px;">
        <div id="unSelectedList" class="tab-pane in ${param.selected!=1?'active':''}">
            <div class="message-list-container">
                <div class="message-list" style="margin: 5px;">
                    <c:if test="${fn:length(unSelectedCetTrainCourses)==0}">
                        <div class="none">${fn:length(selectedCetTrainCourses)==0?"目前没有可选课课程":"已选全部课程"}</div>
                    </c:if>
                    <c:forEach items="${unSelectedCetTrainCourses}" var="tc">
                        <table class="course">
                            <tr>
                                <td colspan="2"class="name">
                                  <i class="message-star ace-icon fa fa-star-o light-green"></i>${tc.cetCourse.name}
                                </td>
                            </tr>
                            <tr>
                                <td class="info">
                                        ${tc.cetCourse.cetExpert.realname}
                                    <c:if test="${not empty tc.startTime}">
                                        (${cm:formatDate(tc.startTime, "MM-dd HH:mm")} ~ ${cm:formatDate(tc.endTime, "MM-dd HH:mm")} )
                                    </c:if>
                                </td>
                                <td rowspan="2" class="op">
                                    <c:choose>
                                        <c:when test="${tc.applyLimit>0 && tc.selectedCount>tc.applyLimit}">报名已满</c:when>
                                        <c:when test="${!cm:compareDate(tc.startTime, now)}">已开课</c:when>
                                        <c:when test="${cetTrain.switchStatus!=CET_TRAIN_ENROLL_STATUS_OPEN}">已关闭选课</c:when>
                                        <c:when test="${tc.applyStatus==CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY
                                        || tc.applyStatus==CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL}">已关闭选课</c:when>
                                        <c:otherwise>
                                            <button type="button"
                                                    data-msg="${tc.cetCourse.name}"
                                                    data-title="确认选课"
                                                    data-url="${ctx}/m/cet/cetTrain_apply_item?isApply=1&trainCourseId=${tc.id}"
                                                    data-callback="_reload"
                                                    data-selected="0"
                                                    class="confirm btn btn-success btn-xs pull-right"><i class="fa fa-plus-circle"></i> 选课</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="info">学时：${cm:stripTrailingZeros(tc.cetCourse.period)} <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_OFFLINE && fn:length(tc.address)>0 }">（${tc.address}）</c:if></td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div id="selectedList" class="tab-pane ${param.selected==1?'active':''}">
            <div class="message-list-container">
                <div class="message-list" style="margin: 5px;">
                    <c:if test="${fn:length(selectedCetTrainCourses)==0}">
                        <div class="none">还没有选课</div>
                    </c:if>
                    <c:forEach items="${selectedCetTrainCourses}" var="tc">
                        <table class="course">
                            <tr>
                                <td colspan="2"class="name">
                                    <i class="message-star ace-icon fa fa-star orange"></i>${tc.cetCourse.name}
                                </td>
                            </tr>
                            <tr>
                                <td class="info">
                                        ${tc.cetCourse.cetExpert.realname}
                                    <c:if test="${not empty tc.startTime}">
                                        (${cm:formatDate(tc.startTime, "MM-dd HH:mm")}~${cm:formatDate(tc.endTime, "MM-dd HH:mm")} )
                                    </c:if>
                                            <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_ONLINE}">（请登录PC端观看视频）</c:if>
                                </td>
                                <td rowspan="2" class="op">
                                    <c:choose>
                                        <c:when test="${tc.isFinished}">已完成</c:when>
                                        <c:when test="${!tc.canQuit}">必修</c:when>
                                        <c:when test="${!cm:compareDate(tc.startTime, now)}">已开课</c:when>
                                        <c:when test="${cetTrain.switchStatus!=CET_TRAIN_ENROLL_STATUS_OPEN}">已关闭选课</c:when>
                                        <c:when test="${tc.applyStatus==CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_APPLY
                                        || tc.applyStatus==CET_TRAIN_COURSE_APPLY_STATUS_CLOSE_ALL}">已关闭选课</c:when>
                                        <c:otherwise>
                                            <button type="button"
                                                    data-msg="${tc.cetCourse.name}"
                                                    data-title="确认退课"
                                                    data-url="${ctx}/m/cet/cetTrain_apply_item?isApply=0&trainCourseId=${tc.id}"
                                                    data-callback="_reload"
                                                    data-selected="1"
                                                    class="confirm btn btn-danger btn-xs pull-right"><i class="fa fa-minus-circle"></i> 退课</button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td class="info">学时：${cm:stripTrailingZeros(tc.cetCourse.period)} <c:if test="${cetProjectPlan.type==CET_PROJECT_PLAN_TYPE_OFFLINE && fn:length(tc.address)>0 }">（${tc.address}）</c:if></td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    table.course{
        border: 1px dotted;
        border-color: #d8d8d8!important;
        margin-bottom: 5px;
        width: 100%;
    }
    table.course td.name{
        color: #6A9CBA;
        font-weight: bold;
        text-indent: -1em;
        padding: 0 2px 0 2em;
    }
    table.course td.info{
        padding-left: 2em;
    }
    table.course .btn{
        /*margin-right: 6px;*/
    }
    table.course td.op{
        text-align: right;
        padding-right: 6px;
        color: darkgreen;
    }
    .message-star{
        vertical-align: 1px;
        margin: 2px 4px 0 8px;
    }
</style>
<script>
function _reload(btn){
    var selected = $(btn).data("selected");
    $.openView({url:"${ctx}/m/cet/courseList_page?trainId=${cetTrain.id}&selected="+selected});
}
</script>