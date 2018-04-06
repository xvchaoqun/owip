<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <c:if test="${param.detail!=1}">
                <li>
                <a href="javascript:" class="hideView btn btn-xs btn-success" style="background-position: inherit">
                    <i class="ace-icon fa fa-backward"></i>
                    返回</a>
                </li>
                </c:if>
                <li class="<c:if test="${empty param.trainCourseId}">active</c:if>">
                    <a href="javascript:void(0)" class="${cetTrain.isOnCampus?"loadPage":"openView"}"
                            <c:if test="${cetTrain.isOnCampus}"> data-load-el="#detail-body-content-view"  data-callback="$.menu.liSelected" </c:if>
                       data-url="${ctx}/cet/cetTrainStat?trainId=${param.trainId}&detail=${param.detail}"><i class="fa fa-signal"></i> 汇总</a>
                </li>
                <c:forEach items="${trainCourses}" var="tc">
                    <li class="${param.trainCourseId==tc.id?'active':''}">
                        <a href="javascript:void(0)" class="${empty tc.evaTableId?"red bolder":(cetTrain.isOnCampus?"loadPage":"openView")}"
                           <c:if test="${cetTrain.isOnCampus}"> data-load-el="#detail-body-content-view"  data-callback="$.menu.liSelected" </c:if>
                           data-url="${ctx}/cet/cetTrainStat?trainId=${param.trainId}&trainCourseId=${tc.id}&detail=${param.detail}">
                            <i class="fa fa-signal"></i>
                        ${tc.isGlobal?(cetTrain.isOnCampus?tc.cetCourse.name:tc.name)
                        :(cetTrain.isOnCampus?tc.cetCourse.cetExpert.realname:tc.teacher)}
                        </a>
                    </li>
                </c:forEach>

                <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                    <a class="btn btn-success btn-sm"
                       href="${ctx}/cet/cetTrainStat?trainId=${param.trainId}&export=1"><i class="fa fa-download"></i> 导出</a>
                </div>
            </ul>
            <div class="tab-content">
                <c:if test="${empty param.trainCourseId}">
                    <table class="table table-center tabel-unhover table-striped table-bordered">
                        <thead>
                        <tr>
                            <th width="100">课程名称</th>
                            <th width="100">授课教师</th>
                            <th width="100">平均得分</th>
                            <th style="text-align: left">意见建议和评价</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${trainCourses}" var="tc">
                            <tr>
                                <td nowrap style="text-align: left">${cetTrain.isOnCampus?tc.cetCourse.name:tc.name}</td>
                                <td>${tc.isGlobal?'-':(cetTrain.isOnCampus?tc.cetCourse.cetExpert.realname:tc.teacher)}</td>
                                <fmt:formatNumber var="_score" type="number" value="${courseScoreMap.get(tc.id)}" maxFractionDigits="1"/>
                                <td>${_score}</td>
                                <td style="text-align: left">
                                    <c:forEach items="${courseFeedbackMap.get(tc.id)}" var="feedback" varStatus="vs">
                                       ${vs.index+1}、${feedback}
                                        ${vs.last?'':'<br/>'}
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
                <c:if test="${not empty param.trainCourseId}">
                    <jsp:include page="stat_course.jsp"/>
                </c:if>
            </div>
        </div>
    </div>
</div>

