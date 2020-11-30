<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${page==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/sys/sysMsg?cls=2&page=1"><i class="fa fa-list"></i> 已接收提醒（${acceptMsg}）</a>
    </li>
    <li class="<c:if test="${page==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/sys/sysMsg?cls=2&page=2"><i class="fa fa-send"></i> 已发送提醒（${sendMsg}）</a>
    </li>
</ul>
