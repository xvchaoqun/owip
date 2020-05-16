<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<div class="row">
    <div class="col-xs-12">
        <div id="body-content">
            <div class="tabbable">
                <ul class="nav nav-tabs padding-12 tab-color-blue background-blue" data-target="#layout">
                    <shiro:hasPermission name="unit:leaderView">
                    <li class="<c:if test="${type==0}">active</c:if>">
                        <a href="javascript:;" data-url="${ctx}/unit_layout_byType?type=0"><i class="fa ${type==0?'fa-th-large':'fa-th'}"></i> 校领导班子</a>
                    </li>
                    </shiro:hasPermission>
                    <c:forEach var="unitType" items="${cm:getMetaTypes('mc_unit_type')}">
                        <c:if test="${!unitType.value.boolAttr}">
                    <li class="${type==unitType.key?'active':''}">
                        <a href="javascript:;" data-url="${ctx}/unit_layout_byType?type=${unitType.key}"><i class="fa ${type==unitType.key?'fa-th-large':'fa-th'}"></i> ${unitType.value.name}</a>
                    </li>
                        </c:if>
                    </c:forEach>
                </ul>
                <div class="tab-content" id="layout">
                </div>
            </div>
        </div>
        <div id="body-content-view">
        </div>
    </div>
</div>
<script>
    $("ul[data-target='#layout'] li:first a").click();
</script>