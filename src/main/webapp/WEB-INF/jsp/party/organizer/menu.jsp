<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<c:set var="OW_ORGANIZER_TYPE_MAP" value="<%=OwConstants.OW_ORGANIZER_TYPE_MAP%>"/>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <c:if test="${type==1}">
    <li class="<c:if test="${cls==10}">active</c:if>">
      <a href="javascript:;" class="loadPage" data-url="${ctx}/organizer?type=${type}&cls=10"><i class="fa fa-group"></i> 校级组织员分组</a>
    </li>
  </c:if>
  <li class="<c:if test="${cls==1}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/organizer?type=${type}&cls=1"><i class="fa fa-circle-o-notch"></i> 现任${OW_ORGANIZER_TYPE_MAP.get(type)}</a>
  </li>
  <li class="<c:if test="${cls==2}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/organizer?type=${type}&cls=2"><i class="fa fa-sign-out"></i> 离任${OW_ORGANIZER_TYPE_MAP.get(type)}</a>
  </li>
  <li class="<c:if test="${cls==3}">active</c:if>">
    <a href="javascript:;" class="loadPage" data-url="${ctx}/organizer?type=${type}&cls=3"><i class="fa fa-history"></i> 历史任职情况</a>
  </li>
</ul>