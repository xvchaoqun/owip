<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <li class="${cls==1?'active':''}">
  <a ${cls!=1?'href="?cls=1"':''}><i class="fa fa-file-pdf-o ${cls==1?'fa-1g':''}"></i> 任免文件</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a ${cls!=2?'href="?cls=2"':''}><i class="fa fa-share-alt ${cls==2?'fa-1g':''}"></i> 任免信息</a>
  </li>
</ul>
