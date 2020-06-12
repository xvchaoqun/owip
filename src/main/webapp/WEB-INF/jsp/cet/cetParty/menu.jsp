<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==0}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetParty?cls=0"><i class="fa fa-circle-o-notch"></i> 正在运转</a>
    </li>
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetParty?cls=1"><i class="fa fa-history"></i> 已删除</a>
    </li>
</ul>
