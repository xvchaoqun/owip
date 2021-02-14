<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpParty?cls=1"><i class="fa fa-circle-o-notch"></i> 民主党派</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/dp/dpParty?cls=2"><i class="fa fa-history"></i> 已撤销</a>
    </li>
     <shiro:hasPermission name="dpOrgAdmin:list">
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-url="${ctx}/dp/dpOrgAdmin?type=<%=OwConstants.OW_ORG_ADMIN_DPPARTY%>&cls=3"><i class="fa fa-users"></i> 管理员列表</a>
    </li>
     </shiro:hasPermission>
</ul>
