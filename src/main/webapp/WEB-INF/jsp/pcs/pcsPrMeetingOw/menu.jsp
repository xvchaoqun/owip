<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrMeetingOw?cls=1">
      <i class="fa fa-envelope-open"></i> 设置大会材料清单</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrMeetingOw?cls=2">
      <i class="fa fa-area-chart"></i> 各分党委党员大会</a>
  </li>
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrMeetingOw?cls=3">
      <i class="fa fa-area-chart"></i> 全校党代表汇总</a>
  </li>
</ul>