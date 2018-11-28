<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp" %>
<ul class="nav nav-tabs padding-12 tab-color-blue background-blue">
  <shiro:hasPermission name="scDispatch:list">
  <li class="${cls==3?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dispatch?cls=3"><i class="fa fa-files-o ${cls==3?'fa-1g':''}"></i> 文件起草签发</a>
  </li>
  </shiro:hasPermission>
  <li class="${cls==1?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dispatch?cls=1"><i class="fa fa-file-pdf-o ${cls==1?'fa-1g':''}"></i> 文件管理</a>
  </li>
  <li class="${cls==2?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dispatch?cls=2"}><i class="fa fa-share-alt ${cls==2?'fa-1g':''}"></i> 干部任免信息</a>
  </li>
  <li class="${cls==4?'active':''}">
  <a href="javascript:;" class="loadPage" data-url="${ctx}/dispatch?cls=4"}><i class="fa fa-share-alt ${cls==4?'fa-1g':''}"></i> 机构调整信息</a>
  </li>
</ul>
