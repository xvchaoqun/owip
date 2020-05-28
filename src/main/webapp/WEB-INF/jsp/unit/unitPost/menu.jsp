<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-url="${ctx}/unitPost?cls=1"><i
                class="fa fa-circle-o-notch"></i> 现有岗位</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-url="${ctx}/unitPost?cls=2"><i class="fa fa-history"></i> 撤销岗位</a>
    </li>
    <shiro:hasPermission name="unitPostGroup:list">
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage"
           data-url="${ctx}/unitPost?cls=3"><i class="fa fa-bars"></i> 岗位分组</a>
    </li>
    </shiro:hasPermission>
</ul>
