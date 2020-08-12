<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>

<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/metaClass?cls=1"><i class="fa fa-tags"></i> 元数据分类</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/metaType"><i class="fa fa-bars"></i> 元数据属性</a>
  </li>
  <shiro:hasRole name="${ROLE_SUPER}">
  <shiro:hasPermission name="metaClass:del">
    <li class="<c:if test="${cls==3}">active</c:if>">
      <a href="javascript:;" class="loadPage" data-url="${ctx}/metaClass?cls=3&isDeleted=1"><i class="fa fa-bars"></i> 已删除</a>
    </li>
  </shiro:hasPermission>
    </shiro:hasRole>
</ul>
