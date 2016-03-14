<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <jsp:include page="menu.jsp"/>
                </ul>

                <div class="tab-content">
                    <div id="home4" class="tab-pane in active">

            <div class="space-4"></div>
                <div class="row col-xs-6">
                    <div class="col-xs-6">
                        <div class="profile-user-info profile-user-info-striped">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">  证件总数 </div>

                                    <div class="profile-info-value">
                                        <span class="editable">${totalCount}</span>
                                    </div>
                                </div>
                        </div>
                        <br/>
                        <div class="profile-user-info profile-user-info-striped">
                            <c:forEach items="${classBeans}" var="bean">
                            <div class="profile-info-row">
                                <div class="profile-info-name">  ${passportTypeMap.get(bean.classId).name} </div>

                                <div class="profile-info-value">
                                    <span class="editable">${bean.num}</span>
                                </div>
                            </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-xs-6">
                        <div class="profile-user-info profile-user-info-striped">
                            <c:forEach items="${postBeans}" var="bean">
                                <div class="profile-info-row">
                                    <div class="profile-info-name">  ${postMap.get(bean.postId).name} </div>

                                    <div class="profile-info-value">
                                        <span class="editable">${bean.num}</span>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
    </div>
                </div></div></div>
    <div id="item-content">
    </div>
    </div>
</div>
