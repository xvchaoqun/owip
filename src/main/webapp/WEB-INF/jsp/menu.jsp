<%@ page import="domain.sys.SysResource" %>
<%@ page import="java.util.Stack" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${cm:getParentIdSet(_path)}" var="parentIdSet"/>
<%
    Stack<SysResource> menuStack = new Stack();
%>
<ul class="nav nav-list">
<c:forEach items="${menus}" var="menu" varStatus="vs">
    <c:set var="cacheCount" value="${cm:getMenuCacheCount(menu.countCacheKeys)}"></c:set>
    <%
        SysResource menu = (SysResource) pageContext.getAttribute("menu");
        while (!menuStack.isEmpty() && menu.getParentId().intValue() != menuStack.peek().getId().intValue()) {
    %>
    </ul></li>
    <%
            menuStack.pop();
        }
    %>
    <c:if test="${menu.type eq 'menu'}">
        <%
            menuStack.push(menu);
        %>
        <li class="<c:if test="${parentIdSet.contains(menu.id)}">active open</c:if> ">
        <a href="javascript:;" class="dropdown-toggle">
            <i class="menu-icon ${menu.menuCss}<c:if test="${empty menu.menuCss}">fa fa-caret-right</c:if>"></i>
            <span class="menu-text"> ${menu.name} </span>
            <c:if test="${cacheCount>0 && (empty menu.countCacheRoles || cm:hasAnyRoles(menu.countCacheRoles))}">
            <span class="badge badge-danger">${cacheCount}</span>
            </c:if>
            <b class="arrow fa fa-angle-down"></b>
        </a>

        <b class="arrow"></b>
        <ul class="submenu">
    </c:if>
    <c:if test="${menu.type eq 'url'}">
        <li class="<c:if test="${menu.url=='/' || menu.url==_path || parentIdSet.contains(menu.id)}">active</c:if>">
            <c:if test="${menu.url=='/'}">
                <a href="#">
            </c:if>
            <c:if test="${menu.url!='/'}">
                <a href="javascript:;" class="hashchange" data-url="${menu.url}">
            </c:if>

                <i class='menu-icon ${menu.menuCss}<c:if test="${empty menu.menuCss}">fa fa-caret-right</c:if>'></i>
                <span class="menu-text"> ${menu.name} </span>
                <c:if test="${cacheCount>0 && (empty menu.countCacheRoles || cm:hasAnyRoles(menu.countCacheRoles))}">
                    <span class="badge badge-warning">${cacheCount}</span>
                </c:if>
            </a>
            <b class="arrow"></b>
        </li>
    </c:if>
    <c:if test="${vs.last}">
        <%
            while (!menuStack.isEmpty()) {
        %>
        </ul></li>
        <%
                menuStack.pop();
            }
        %>
    </c:if>
</c:forEach>
</ul>
