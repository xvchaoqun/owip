<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set value="${cm:getParentIdSet(_path)}" var="parentIdSet"/>
<ul class="nav nav-list">
<c:forEach items="${menus}" var="menu" varStatus="st">
    <c:if test="${menu.parentId>0 && menu.type!='function' && not empty menu.permission}">
    <shiro:hasPermission name="${menu.permission}">
    <c:if test="${st.index>1 && menu.parentId!=lastMenu.parentId && menu.parentId!=lastMenu.id}">
        <!--上一个节点不是父节点，且不是兄弟节点-->
        </ul></li>

        <c:if test="${fn:length(fn:split(menu.parentIds,'/'))==2 &&
        fn:length(fn:split(lastMenu.parentIds,'/'))==4}">
            <!--上一个节点是一个三级节点，需要二次闭合-->
            </ul></li>
        </c:if>
    </c:if>
    <c:if test="${menu.type=='url'}">
        <!--如果是url本身，或者 function打开，则选择function所属的url-->
        <li class="<c:if test="${menu.url==_path || parentIdSet.contains(menu.id)}">active</c:if>">
            <a href="${menu.url}">
                <i class='menu-icon ${menu.menuCss}<c:if test="${empty menu.menuCss}">fa fa-caret-right</c:if>'></i>
                <span class="menu-text"> ${menu.name} </span>
            </a>
            <b class="arrow"></b>
        </li>
    </c:if>
    <c:if test="${menu.type=='menu'}">
        <li class="<c:if test="${parentIdSet.contains(menu.id)}">active open</c:if> ">
            <a href="#" class="dropdown-toggle">
                <i class="menu-icon ${menu.menuCss}<c:if test="${empty menu.menuCss}">fa fa-caret-right</c:if>"></i>
                <span class="menu-text"> ${menu.name} </span>
                <b class="arrow fa fa-angle-down"></b>
            </a>
            <b class="arrow"></b>
            <ul class="submenu">
    </c:if>
    <c:set var="lastMenu" value="${menu}"/>
    </shiro:hasPermission>
    </c:if>
</c:forEach>
</ul>
