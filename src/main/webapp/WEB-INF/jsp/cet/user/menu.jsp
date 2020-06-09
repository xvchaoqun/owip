<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li  class="<c:if test="${cls==5}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/cet/cetUnitTrain?cls=5"><i class="fa fa-list"></i> 培训信息</a>
    </li>
    <li  class="<c:if test="${cls==6}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/user/cet/cetUnitTrain?cls=6"><i class="fa fa-list"></i> 补录信息</a>
    </li>
</ul>
