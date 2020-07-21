<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnlineInspectorType?cls=2"><i class="fa fa-drivers-license"></i> 参评人身份类型</a>
    </li>
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dr/drOnlineNotice?cls=1"><i class="fa fa-book"></i> 线上民主推荐情况说明模板</a>
    </li>
</ul>
