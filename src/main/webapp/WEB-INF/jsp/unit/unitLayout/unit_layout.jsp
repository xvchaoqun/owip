<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <!-- PAGE CONTENT BEGINS -->
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
                    <li class="<c:if test="${type==0}">active</c:if>">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/unit_layout?type=0"><i class="fa ${type==0?'fa-th-large':'fa-th'}"></i> 校领导班子</a>
                    </li>
                    <c:forEach var="unitType" items="${unitTypeMap}">
                    <li class="${type==unitType.key?'active':''}">
                        <a href="javascript:;" class="loadPage" data-url="${ctx}/unit_layout?type=${unitType.key}"><i class="fa ${type==unitType.key?'fa-th-large':'fa-th'}"></i> ${unitType.value.name}</a>
                    </li>
                    </c:forEach>
                </ul>

                <div class="tab-content">
                    <c:import url="/unit_layout_byType?type=${type}"/>
                </div>

            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>