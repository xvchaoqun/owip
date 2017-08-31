<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrOw?cls=1&stage=${param.stage}">
      <i class="fa fa-envelope-open"></i> 各分党委推荐情况</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsPrOw?cls=2&stage=${param.stage}">
      <i class="fa fa-area-chart"></i> 全校党代表推荐汇总</a>
  </li>
</ul>