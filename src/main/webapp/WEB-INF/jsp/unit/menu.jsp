<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?cls=1"><i class="fa fa-circle-o-notch fa-spin"></i> 正在运转单位</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?cls=2"><i class="fa fa-history"></i> 历史单位</a>
  </li>
  <shiro:hasPermission name="unit:del">
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?cls=3"><i class="fa fa-trash"></i> 已删除</a>
  </li>
  </shiro:hasPermission>
  <shiro:hasPermission name="unit:edit">
  <li class="<c:if test="${cls==4}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/unit?cls=4"><i class="fa fa-list"></i> 学校单位列表</a>
  </li>
  </shiro:hasPermission>
</ul>