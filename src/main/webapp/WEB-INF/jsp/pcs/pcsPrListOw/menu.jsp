<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrListOw?cls=1">
      <i class="fa fa-envelope-open"></i> 全校党代表名单</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrListOw?cls=2">
      <i class="fa fa-line-chart"></i> 全校党代表数据统计</a>
  </li>
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrListOw?cls=3">
      <i class="fa fa-hand-paper-o"></i> 全校党员参与情况</a>
  </li>
</ul>