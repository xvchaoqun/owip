<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div class="tabbable">
            <a href="javascript:" class="closeView btn btn-xs btn-success">
                <i class="ace-icon fa fa-backward"></i>
                返回</a>
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                <li class="<c:if test="${empty param.courseId}">active</c:if>">
                    <a href="javascript:void(0)" class="openView" data-url="${ctx}/stat_train_page?trainId=${param.trainId}"><i class="fa fa-signal"></i> 汇总</a>
                </li>
                <c:forEach items="${trainCourses}" var="tc">
                    <c:if test="${tc.status==AVAILABLE}">
                    <li class="<c:if test="${param.courseId==tc.id}">active</c:if>">
                        <a href="javascript:void(0)" class="openView" data-url="${ctx}/stat_train_page?trainId=${param.trainId}&courseId=${tc.id}"><i class="fa fa-signal"></i>
                        ${tc.isGlobal?tc.name:tc.teacher}
                        </a>
                    </li>
                    </c:if>
                </c:forEach>

                <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px; position: relative">
                    <a class="btn btn-success btn-sm"
                       href="${ctx}/stat_train_page?trainId=${param.trainId}&export=1"><i class="fa fa-download"></i> 导出</a>
                </div>
            </ul>
            <div class="tab-content">
                <c:if test="${empty param.courseId}">
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
                                <td>${tc.name}</td>
                                <td>${tc.isGlobal?'-':tc.teacher}</td>
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
                <c:if test="${not empty param.courseId}">
                    <jsp:include page="stat_course.jsp"/>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div class="footer-margin"/>
