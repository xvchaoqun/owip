<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<shiro:hasPermission name="metaClass:*">
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/metaClass?cls=1"><i class="fa fa-tags"></i> 元数据分类</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/metaClass?cls=2"><i class="fa fa-bars"></i> 元数据属性</a>
  </li>
</ul>
  </shiro:hasPermission>