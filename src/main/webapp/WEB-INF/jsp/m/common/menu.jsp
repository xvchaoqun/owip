<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<ul class="nav nav-list">
  <li class="${_path=='/m/index'?'active':''}">
    <a href="${ctx}/m/index">
      <i class="menu-icon fa fa-tachometer"></i>
      <span class="menu-text"> 首页 </span>
    </a>

    <b class="arrow"></b>
  </li>

  <li class="${_path=='/m/applySelfList'?'active':''}">
    <a href="${ctx}/m/applySelfList">
      <i class="menu-icon fa fa-pencil-square-o"></i>
      <span class="menu-text"> 因私出国境审批 </span>
      <%--<b class="arrow fa fa-angle-down"></b>--%>
    </a>
    <b class="arrow"></b>
  </li>
  <li class="">
    <a href="javascript:;" onclick="_logout()">
      <i class="menu-icon fa fa-power-off"></i>
      <span class="menu-text"> 安全退出 </span>
    </a>

    <b class="arrow"></b>
  </li>
</ul>