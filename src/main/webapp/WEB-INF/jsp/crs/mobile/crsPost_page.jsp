<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/crs/constants.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
        <div class="tabbable">
            <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" id="myTab4">
                <li class="<c:if test="${status==1}">active</c:if>">
                    <a href="?status=1"><i
                            class="green ace-icon fa fa-clock-o bigger-120"></i>正在招聘</a>
                </li>

                <li class="<c:if test="${status==2}">active</c:if>">
                    <a href="?status=2"><i
                            class="orange ace-icon fa fa-check-square-o bigger-120"></i>完成招聘</a>
                </li>
            </ul>
            <div class="tab-content" style="padding: 0px;">
                <div class="tab-pane in active">
                    <div class="message-list-container">
                        <div class="message-list">
                            <c:if test="${fn:length(crsPosts)==0}">
                                <div class="none">目前没有${status==1?'正在招聘':'完成招聘'}记录</div>
                            </c:if>
                            <c:forEach items="${crsPosts}" var="crsPost">
                                <%--<c:set var="cadre" value="${cm:getCadreById(crsPost.cadreId)}"/>
                                <c:set var="sysUser" value="${cm:getUserById(cadre.userId)}"/>--%>
                                <div class="openView message-item" data-open-by="page"
                                     data-url="${ctx}/m/crs/crsApplicant_page?postId=${crsPost.id}">
                                    <i class="message-star ace-icon fa ${status==1?'fa-star orange2':'fa-star-o light-green'}"></i>
                                    <span class="sender sigle-span">${crsPost.name}</span>
                                <span class="summary">
                                    <span class="text sigle-span">
                                        <c:set var="applicantCount" value="${fn:length(crsPost.applicants)}"/>
                                         <c:choose>
                                            <c:when test="${crsPost.autoSwitch}">
                                                <c:choose>
                                                    <c:when test="${cm:compareDate(crsPost.startTime,now)}">
                                                        未开启报名
                                                    </c:when>
                                                    <c:when test="${cm:compareDate(crsPost.endTime, now)}">
                                                        正在报名(${applicantCount})
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-danger">报名结束(${applicantCount})</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:when>
                                            <c:when test="${crsPost.enrollStatus==CRS_POST_ENROLL_STATUS_OPEN}">
                                                正在报名(${applicantCount})
                                            </c:when>
                                            <c:otherwise>
                                               ${CRS_POST_ENROLL_STATUS_MAP.get(crsPost.enrollStatus)}(${applicantCount})
                                            </c:otherwise>
                                         </c:choose>
                                        (${cm:formatDate(crsPost.endTime, "yyyy-MM-dd HH:mm")} 截止报名)
                                    </span>

                                </span>
                                    <span class="summary">
                                    <span class="text">
                                        分管工作：${empty crsPost.job?'--':crsPost.job}
                                    </span>
                                    <a class="btn btn-success btn-xs pull-right">详情</a>
                                </span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="message-footer clearfix">
                        <wo:page commonList="${commonList}" uri="${ctx}/m/crs/crsPost_page" target="#page-content" model="4"/>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
