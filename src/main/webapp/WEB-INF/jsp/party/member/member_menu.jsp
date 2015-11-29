<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="<c:if test="${type==1}">active</c:if>">
  <a <c:if test="${type!=1}">href="?type=1"</c:if>><i class="fa fa-th<c:if test="${type==1}">-large</c:if>"></i> 学生党员</a>
  </li>
  <li class="<c:if test="${type==2}">active</c:if>">
  <a <c:if test="${type!=2}">href="?type=2"</c:if>><i class="fa fa-th<c:if test="${type==2}">-large</c:if>"></i> 教职工党员</a>
  </li>
  <li class="<c:if test="${type==3}">active</c:if>">
  <a <c:if test="${type!=3}">href="?type=3"</c:if>><i class="fa fa-th<c:if test="${type==3}">-large</c:if>"></i> 离退休党员</a>
  </li>
</ul>
