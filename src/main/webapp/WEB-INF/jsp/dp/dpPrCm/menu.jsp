<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpPrCm?cls=1"><i class="fa fa-circle-o-notch"></i>人大代表、政协委员</a>
    </li>
    <%--<li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpPrCm?cls=2"><i class="fa fa-history"></i> 已移除</a>
    </li>--%>
</ul>
