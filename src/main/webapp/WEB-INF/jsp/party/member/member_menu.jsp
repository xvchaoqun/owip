<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a ${cls!=1?'href="?cls=1"':''}><i class="fa fa-th${cls==1?'-large':''}"></i> 学生党员</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a ${cls!=2?'href="?cls=2"':''}><i class="fa fa-th${cls==2?'-large':''}"></i> 教职工党员</a>
  </li>
  <li class="${cls==3?'active':''}">
  <a ${cls!=3?'href="?cls=3"':''}><i class="fa fa-th${cls==3?'-large':''}"></i> 离退休党员</a>
  </li>
  <li class="${cls==4?'active':''}">
    <a ${cls!=4?'href="?cls=4"':''}><i class="fa fa-th${cls==4?'-large':''}"></i> 应退休党员</a>
  </li>
  <li class="${cls==5?'active':''}">
    <a ${cls!=5?'href="?cls=5"':''}><i class="fa fa-th${cls==5?'-large':''}"></i> 已退休党员</a>
  </li>
</ul>
