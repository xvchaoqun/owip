<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${clss==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/party/partyRePu_page?type=${param.type}&clss=1"><i class="fa fa-circle-o-notch"></i> 党内奖励</a>
    </li>
    <li class="<c:if test="${clss==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/party/partyRePu_page?type=${param.type}&clss=2"><i class="fa fa-history"></i> 党内惩罚</a>
    </li>
</ul>
