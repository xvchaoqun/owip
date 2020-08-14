<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=1"><i class="fa fa-list"></i> 党代会投票</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=2&isSecond=0"><i class="fa fa-drivers-license"></i> 一下阶段投票结果</a>
    </li>
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/pcs/pcsPoll?cls=3&isSecond=1"><i class="fa fa-drivers-license"></i> 二下阶段投票结果</a>
    </li>
</ul>
