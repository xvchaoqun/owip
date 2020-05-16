<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs nav-justified" data-target="#unitfunctionDiv">
	<c:forEach items="${unitFunctions}" var="fun">
	<li class="<c:if test='${currentId == fun.id}'>active</c:if>" style="width: 120px">
		<a href="javascript:void(0)" class="${fun.isCurrent?'green bolder':''}"
        data-url="${ctx}/unitFunction?unitId=${unitId}&funId=${fun.id}">
			<c:if test="${fun.isCurrent}"><i class="fa fa-circle-o"></i> </c:if>
			<c:if test="${!fun.isCurrent}"><i class="fa fa-history"></i> </c:if>
			${cm:formatDate(fun.confirmTime, "yyyy.MM.dd")}
		</a>
	</li>
	</c:forEach>
	<li class="<c:if test='${currentId==-1}'>active</c:if>" style="width: 100px">
		<a href="javascript:void(0)" data-url="${ctx}/unitFunction?unitId=${unitId}&funId=-1"><i class="fa fa-plus"></i></a>
	</li>
</ul>
