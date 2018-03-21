<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<ul class="nav nav-list">
  <li class="${_path=='/m/abroad/index'?'active':''}">
    <a href="${ctx}/m/abroad/index">
      <i class="menu-icon fa fa-tachometer"></i>
      <span class="menu-text"> 首页 </span>
    </a>

    <b class="arrow"></b>
  </li>
  <shiro:lacksRole name="${ROLE_CADREADMIN}">
  <li class="${_path=='/m/abroad/applySelfList'?'active':''}">
    <a href="${ctx}/m/abroad/applySelfList">
      <i class="menu-icon fa fa-pencil-square-o"></i>
      <span class="menu-text"> 因私出国境审批 </span>
      <%--<b class="arrow fa fa-angle-down"></b>--%>
    </a>
    <b class="arrow"></b>
  </li>
  </shiro:lacksRole>
  <shiro:hasRole name="${ROLE_CADREADMIN}">
    <li class="${_path=='/m/abroad/applySelf'?'active':''}">
      <a href="${ctx}/m/abroad/applySelf">
        <i class="menu-icon fa fa-pencil-square-o"></i>
        <span class="menu-text"> 因私出国境审批 </span>
          <%--<b class="arrow fa fa-angle-down"></b>--%>
      </a>
      <b class="arrow"></b>
    </li>
  </shiro:hasRole>
  <li class="">
    <a href="javascript:;" onclick="_logout()" ontouchstart="_logout()">
      <i class="menu-icon fa fa-power-off"></i>
      <span class="menu-text"> 安全退出 </span>
    </a>

    <b class="arrow"></b>
  </li>
</ul>