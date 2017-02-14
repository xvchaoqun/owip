<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<ul class="nav nav-list">
  <li class="${_path=='/train/index'?'active':''}">
    <a href="${ctx}/train/index">
      <i class="menu-icon fa fa-tachometer"></i>
      <span class="menu-text"> 全部课程 </span>
    </a>

    <b class="arrow"></b>
  </li>
  <li class="">
    <a href="${ctx}/train/logout">
      <i class="menu-icon fa fa-power-off"></i>
      <span class="menu-text"> 安全退出 </span>
    </a>

    <b class="arrow"></b>
  </li>
</ul>