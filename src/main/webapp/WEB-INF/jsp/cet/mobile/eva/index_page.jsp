<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/cet/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">

        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-block alert-success" style="font-size: 18px;font-weight: bolder">
                    <i class="ace-icon fa fa-hourglass-1 green"></i>
                    &nbsp;&nbsp;${cetTrain.name}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8 infobox-container">
                    <c:forEach items="${trainCourseMap}" var="entity">
                        <c:set var="tc" value="${entity.value}"></c:set>
                        <c:set var="tic" value="${ticMap.get(tc.id)}"></c:set>
                        <c:set var="evaIsClosed" value="${cm:evaIsClosed(tc.id)}"></c:set>
                        <div class="infobox ${tic.status==CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH?'infobox-grey':'infobox-blue2'}">
                            <%--<div class="infobox-icon">
                                <i class="ace-icon fa ${tic.status==CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH?'fa-check-square-o':'fa-history'}"></i>
                            </div>--%>
                            <div class="infobox-data" style="width: 100%">
                                    <table class="course-list" style="width: 100%">
                                    <tr>
                                        <td class="name ${tic.status==CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH?'finish':''}">
                                            ${cetTrain.isOnCampus?tc.cetCourse.name:tc.name}
                                        </td>
                                        <td class="status">
                                            <c:if test="${tic.status==CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH}">
                                                已完成测评
                                            </c:if>
                                            <c:if test="${tic.status!=CET_TRAIN_INSPECTOR_COURSE_STATUS_FINISH}">
                                                <c:if test="${evaIsClosed==0}">
                                                    <button class="ahref btn btn-success btn-xs"  data-url="${ctx}/m/cet_eva/eva?trainCourseId=${tc.id}"><i class="fa fa-hand-o-right"></i> 测评</button>
                                                </c:if>
                                                <c:if test="${evaIsClosed==1}">
                                                    已关闭评课
                                                </c:if>
                                                <c:if test="${evaIsClosed==2}">
                                                    ${tc.isGlobal?'未开始测评':'未上课'}
                                                </c:if>
                                                <c:if test="${evaIsClosed==3}">
                                                    评课已结束
                                                </c:if>
                                            </c:if>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <span style="font-size: 10pt;">
                                                <c:if test="${tc.isGlobal}">
                                                    ${cm:formatDate(tc.startTime, "yyyy-MM-dd HH:mm")}
                                                        </c:if>
                                                <c:if test="${!tc.isGlobal}">
                                                    ${cm:substr(cetTrain.isOnCampus?tc.cetCourse.cetExpert.realname:tc.teacher, 0, 20, '')}&nbsp;&nbsp;<c:if test="${not empty tc.startTime}"> ${cm:formatDate(tc.startTime, "HH:mm")}~${cm:formatDate(tc.endTime, "HH:mm")}</c:if>
                                                &nbsp;&nbsp;${cm:formatDate(tc.startTime, "yyyy-MM-dd")}
                                                </c:if>
                                        </span>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->
            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
</div>
<style>
    .infobox{
        height: auto;
    }
    .course-list .name{
        font-weight: bold;
        color: black;
    }
    .course-list .name.finish{
        color: grey;
    }
    .course-list .status{
        text-align: right;
        padding-left:10px;
        white-space: nowrap;
    }
</style>