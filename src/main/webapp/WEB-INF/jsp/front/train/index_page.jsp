<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div class="row">
            <div class="col-xs-12">
                <div class="alert alert-block alert-success">
                    <i class="ace-icon fa fa-hourglass-1 green"></i>
                     ${train.name}
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8 infobox-container">
                    <c:forEach items="${trainCourseMap}" var="entity">
                        <c:set var="tc" value="${entity.value}"></c:set>
                        <c:set var="tic" value="${ticMap.get(tc.id)}"></c:set>
                        <c:set var="evaIsClosed" value="${cm:evaIsClosed(tc.id)}"></c:set>
                        <div class="infobox infobox-blue2">
                            <div class="infobox-icon">
                                <c:if test="${tic.status!=TRAIN_INSPECTOR_COURSE_STATUS_FINISH}">
                                <i class="ace-icon fa fa-history"></i>
                                </c:if>
                                <c:if test="${tic.status==TRAIN_INSPECTOR_COURSE_STATUS_FINISH}">
                                    <i class="ace-icon fa fa-check-square-o"></i>
                                </c:if>
                            </div>
                            <div class="infobox-data">
                                <div class="infobox-content">${tc.name}
                                    <div class="pull-right">
                                    <c:if test="${tic.status==TRAIN_INSPECTOR_COURSE_STATUS_FINISH}">
                                        完成测评
                                    </c:if>
                                    <c:if test="${tic.status!=TRAIN_INSPECTOR_COURSE_STATUS_FINISH}">
                                        <c:if test="${evaIsClosed==0}">
                                            <button class="ahref btn btn-success btn-xs"  data-url="${ctx}/train/eva?courseId=${tc.id}"><i class="fa fa-hand-o-right"></i> 测评</button>
                                        </c:if>
                                        <c:if test="${evaIsClosed==1}">
                                            已关闭评课
                                        </c:if>
                                        <c:if test="${evaIsClosed==2}">
                                            未上课
                                        </c:if>
                                        <c:if test="${evaIsClosed==3}">
                                            评课已结束
                                        </c:if>
                                    </c:if>
                                    </div>
                                </div>
                                <span class="infobox-data-number"> <span style="font-size: 10pt;">
                                    ${tc.teacher}&nbsp;&nbsp;${cm:formatDate(tc.startTime, "yyyy-MM-dd HH:mm")} - ${cm:formatDate(tc.endTime, "HH:mm")}
                                </span></span>


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