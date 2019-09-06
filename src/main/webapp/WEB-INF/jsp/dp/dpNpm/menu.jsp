<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpNpm?cls=1"><i class="fa fa-circle-o-notch"></i> 无党派人士</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpNpm?cls=2"><i class="fa fa-history"></i> 已退出</a>
    </li>
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpNpm?cls=3"><i class="fa fa-arrow-circle-right"></i> 已转出</a>
    </li>
</ul>
