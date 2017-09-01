<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsParty?cls=1&stage=${param.stage}">
      <i class="fa fa-envelope-open"></i> 两委委员推荐提名情况</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsParty?cls=2&stage=${param.stage}">
      <i class="fa fa-line-chart"></i> 党员参与推荐提名情况</a>
  </li>
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/pcsParty?cls=3&stage=${param.stage}">
      <i class="fa fa-hand-paper-o"></i> 报送（“${param.stage==PCS_STAGE_FIRST?"一上":""}
      ${param.stage==PCS_STAGE_SECOND?"二上":""}${param.stage==PCS_STAGE_THIRD?"三上":""}”）</a>
  </li>
</ul>