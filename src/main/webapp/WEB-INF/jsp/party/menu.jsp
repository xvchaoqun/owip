<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/party/constants.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${type==0&&cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/party?cls=1"><i class="fa fa-circle-o-notch"></i> ${_p_partyName}列表</a>
    </li>
    <c:if test="${_p_use_inside_pgb}">
        <li class="<c:if test="${type==1&&cls==1}">active</c:if>">
            <a href="javascript:;" class="loadPage" data-url="${ctx}/party?type=1"><i class="fa fa-circle-o-notch"></i> 内设党总支</a>
        </li>
    </c:if>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/party?cls=2"><i class="fa fa-history"></i> 已撤销</a>
    </li>
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-url="${ctx}/orgAdmin?type=<%=OwConstants.OW_ORG_ADMIN_PARTY%>&cls=3"><i class="fa fa-user-circle-o"></i> 管理员列表</a>
    </li>
    <c:if test="${cls==1&&type==0}">
    <shiro:hasAnyRoles name="${ROLE_ADMIN}, ${ROLE_ODADMIN}">
        <div class="buttons pull-left hidden-sm hidden-xs" style="left:50px;">
            <a class="downloadBtn btn btn-success btn-sm"
               href="javascript:;" data-url="${ctx}/party?export=2">
                <i class="fa fa-download"></i> 汇总导出基本情况表</a>
        </div>
    </shiro:hasAnyRoles>
    </c:if>
</ul>
