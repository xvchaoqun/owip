<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetCourse?type=${param.type}&cls=1"><i class="fa fa-share-alt ${cls==1?'fa-1g':''}"></i> 课程列表</a>
  </li>
  <li class="${cls==4?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetCourse?type=${param.type}&cls=4"><i class="fa fa-trash ${cls==4?'fa-1g':''}"></i> 已删除</a>
  </li>
  <c:if test="${param.type==CET_COURSE_TYPE_OFFLINE||param.type==CET_COURSE_TYPE_ONLINE}">
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetCourse?type=${param.type}&cls=2"><i class="fa fa-comments ${cls==2?'fa-1g':''}"></i> 重点专题管理</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/cet/cetCourse?type=${param.type}&cls=3"><i class="fa fa-random ${cls==3?'fa-1g':''}"></i> 特色栏目管理</a>
  </li>
  </c:if>
</ul>
