<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
    <li class="<c:if test="${cls==1}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreCompanyList?module=${module}&cls=1"><i
                class="fa fa-circle-o-notch"></i> 正在兼职</a>
    </li>
    <li class="<c:if test="${cls==2}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreCompanyList?module=${module}&cls=2"><i
                class="fa fa-history"></i> 历史兼职</a>
    </li>
    <c:if test="${module==2}">
    <li class="<c:if test="${cls==10}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreCompanyList?module=${module}&cls=10"><i
                class="fa fa-list"></i> 全部兼职</a>
    </li>
    </c:if>
    <li class="<c:if test="${cls==3}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreCompanyList?module=${module}&cls=3"><i
                class="fa fa-line-chart"></i> 兼职统计</a>
    </li>
    <c:if test="${module==2}">
    <li class="<c:if test="${cls==4}">active</c:if>">
        <a href="javascript:;" class="loadPage" data-url="${ctx}/cadreCompanyList?module=${module}&cls=4"><i
                class="fa fa-line-chart"></i> 按类别统计</a>
    </li>
    </c:if>
</ul>