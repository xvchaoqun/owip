<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <shiro:hasPermission name="owReport:menu">
      <li class="<c:if test="${cls==1}">active</c:if>">
          <a href="javascript:;" class="hashchange" data-load-el="#tab-content"
             data-url="${ctx}/member/memberReport?cls=1"><i
                  class="fa fa-user"></i> 党组织书记考核</a>
      </li>
      <li class="<c:if test="${cls==2}">active</c:if>">
          <a href="javascript:;" class="hashchange" data-load-el="#tab-content"
             data-url="${ctx}/member/memberReport?cls=2"><i class="fa fa-sitemap"></i> 党支部考核</a>
      </li>
  </shiro:hasPermission>
</ul>